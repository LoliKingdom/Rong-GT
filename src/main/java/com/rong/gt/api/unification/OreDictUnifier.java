package com.rong.gt.api.unification;

import static com.rong.gt.Values.M;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import com.rong.gt.ConfigHolder;
import com.rong.gt.api.unification.materials.types.DustMaterial;
import com.rong.gt.api.unification.materials.types.IngotMaterial;
import com.rong.gt.api.unification.materials.types.MarkerMaterial;
import com.rong.gt.api.unification.materials.types.Material;
import com.rong.gt.api.unification.stack.ItemAndMetadata;
import com.rong.gt.api.unification.stack.ItemMaterialInfo;
import com.rong.gt.api.unification.stack.MaterialStack;
import com.rong.gt.api.unification.stack.UnificationEntry;
import com.rong.gt.api.unification.stack.WildcardAwareHashMap;
import com.rong.gt.api.utils.CustomModPriorityComparator;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

public class OreDictUnifier {

    private OreDictUnifier() {
    }

    //simple version of material registry for marker materials
    private static final Map<String, MarkerMaterial> markerMaterialRegistry = new HashMap<>();
    private static final Map<ItemAndMetadata, ItemMaterialInfo> materialUnificationInfo = new WildcardAwareHashMap<>();
    private static final Map<ItemAndMetadata, UnificationEntry> stackUnificationInfo = new WildcardAwareHashMap<>();
    private static final Map<UnificationEntry, ArrayList<ItemAndMetadata>> stackUnificationItems = new HashMap<>();
    private static final Map<ItemAndMetadata, Set<String>> stackOreDictName = new WildcardAwareHashMap<>();

    @Nullable
    private static Comparator<ItemAndMetadata> stackComparator;

    public static Comparator<ItemAndMetadata> getSimpleItemStackComparator() {
        if (stackComparator == null) {
            if (ConfigHolder.useCustomModPriorities) {
                List<String> modPriorities = Arrays.asList(ConfigHolder.modPriorities);
                stackComparator = Collections.reverseOrder(new CustomModPriorityComparator(modPriorities));
            } else {
                //noinspection ConstantConditions
                Function<ItemAndMetadata, String> modIdExtractor = stack -> stack.item.getRegistryName().getResourceDomain();
                stackComparator = Comparator.comparing(modIdExtractor);
            }
        }
        return stackComparator;
    }

    public static Comparator<ItemStack> getItemStackComparator() {
        Comparator<ItemAndMetadata> comparator = getSimpleItemStackComparator();
        return (first, second) -> comparator.compare(new ItemAndMetadata(first), new ItemAndMetadata(second));
    }

    public static void registerMarkerMaterial(MarkerMaterial markerMaterial) {
        if (markerMaterialRegistry.containsKey(markerMaterial.toString())) {
            throw new IllegalArgumentException(("Marker material with id " + markerMaterial.toString() + " is already registered!"));
        }
        markerMaterialRegistry.put(markerMaterial.toString(), markerMaterial);
    }

    public static void registerOre(ItemStack itemStack, ItemMaterialInfo materialInfo) {
        if (itemStack.isEmpty()) return;
        materialUnificationInfo.put(new ItemAndMetadata(itemStack), materialInfo);
    }

    public static void registerOre(ItemStack itemStack, EnumOrePrefix orePrefix, @Nullable Material material) {
        if (itemStack.isEmpty()) return;
        OreDictionary.registerOre(orePrefix.name() + (material == null ? "" : material.toCamelCaseString()), itemStack);
    }

    public static void registerOre(ItemStack itemStack, String oreDict) {
        if (itemStack.isEmpty()) return;
        OreDictionary.registerOre(oreDict, itemStack);
    }

    public static void init() {
        for (String registeredOreName : OreDictionary.getOreNames()) {
            NonNullList<ItemStack> theseOres = OreDictionary.getOres(registeredOreName);
            for (ItemStack itemStack : theseOres) {
                onItemRegistration(new OreRegisterEvent(registeredOreName, itemStack));
            }
        }
        MinecraftForge.EVENT_BUS.register(OreDictUnifier.class);
    }

    @SubscribeEvent
    public static void onItemRegistration(OreRegisterEvent event) {
        ItemAndMetadata simpleItemStack = new ItemAndMetadata(event.getOre());
        String oreName = event.getName();
        //cache this registration by name
        stackOreDictName.computeIfAbsent(simpleItemStack, k -> new HashSet<>()).add(oreName);
        //and try to transform registration name into OrePrefix + Material pair
        EnumOrePrefix orePrefix = EnumOrePrefix.getPrefix(oreName);
        Material material = null;
        if (orePrefix == null) {
            //split ore dict name to parts
            //oreBasalticMineralSand -> ore, Basaltic, Mineral, Sand
            ArrayList<String> splits = new ArrayList<>();
            StringBuilder builder = new StringBuilder();
            for (char character : oreName.toCharArray()) {
                if (Character.isUpperCase(character)) {
                    if (builder.length() > 0) {
                        splits.add(builder.toString());
                        builder = new StringBuilder().append(character);
                    } else splits.add(Character.toString(character));
                } else builder.append(character);
            }
            if (builder.length() > 0) {
                splits.add(builder.toString());
            }
            //try to combine in different manners
            //oreBasaltic MineralSand , ore BasalticMineralSand
            StringBuilder buffer = new StringBuilder();
            for (int i = 0; i < splits.size(); i++) {
                buffer.append(splits.get(i));
                EnumOrePrefix maybePrefix = EnumOrePrefix.getPrefix(buffer.toString()); //ore -> OrePrefix.ore
                String possibleMaterialName = Joiner.on("").join(splits.subList(i + 1, splits.size())); //BasalticMineralSand
                String underscoreName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, possibleMaterialName); //basaltic_mineral_sand
                Material possibleMaterial = Material.MATERIAL_REGISTRY.getObject(underscoreName); //Materials.BasalticSand
                if (possibleMaterial == null) {
                    //if we didn't found real material, try using marker material registry
                    possibleMaterial = markerMaterialRegistry.get(underscoreName);
                }
                if (maybePrefix != null && possibleMaterial != null) {
                    orePrefix = maybePrefix;
                    material = possibleMaterial;
                    break;
                }
            }
        }

        //finally register item
        if (orePrefix != null && (material != null || orePrefix.isSelfReferencing)) {
            UnificationEntry unificationEntry = new UnificationEntry(orePrefix, material);
            stackUnificationItems.computeIfAbsent(unificationEntry, p -> new ArrayList<>()).add(simpleItemStack);
            if (!unificationEntry.orePrefix.isMarkerPrefix()) {
                stackUnificationInfo.put(simpleItemStack, unificationEntry);
            }
            orePrefix.processOreRegistration(material);
        }
    }

    public static Set<String> getOreDictionaryNames(ItemStack itemStack) {
        if (itemStack.isEmpty()) return Collections.emptySet();
        ItemAndMetadata simpleItemStack = new ItemAndMetadata(itemStack);
        if (stackOreDictName.containsKey(simpleItemStack))
            return Collections.unmodifiableSet(stackOreDictName.get(simpleItemStack));
        return Collections.emptySet();
    }

    @Nullable
    public static MaterialStack getMaterial(ItemStack itemStack) {
        if (itemStack.isEmpty()) return null;
        ItemAndMetadata simpleItemStack = new ItemAndMetadata(itemStack);
        UnificationEntry entry = stackUnificationInfo.get(simpleItemStack);
        if (entry != null) {
            Material entryMaterial = entry.material;
            if (entryMaterial == null) {
                entryMaterial = entry.orePrefix.materialType;
            }
            if (entryMaterial != null) {
                return new MaterialStack(entryMaterial, entry.orePrefix.materialAmount);
            }
        }
        ItemMaterialInfo info = materialUnificationInfo.get(simpleItemStack);
        return info == null ? null : info.material.copy();
    }

    @Nullable
    public static EnumOrePrefix getPrefix(ItemStack itemStack) {
        if (itemStack.isEmpty()) return null;
        ItemAndMetadata simpleItemStack = new ItemAndMetadata(itemStack);
        UnificationEntry entry = stackUnificationInfo.get(simpleItemStack);
        if (entry != null) return entry.orePrefix;
        return null;
    }

    @Nullable
    public static UnificationEntry getUnificationEntry(ItemStack itemStack) {
        if (itemStack.isEmpty()) return null;
        return stackUnificationInfo.get(new ItemAndMetadata(itemStack));
    }

    public static ItemStack getUnificated(ItemStack itemStack) {
        if (itemStack.isEmpty()) return ItemStack.EMPTY;
        UnificationEntry unificationEntry = getUnificationEntry(itemStack);
        if (unificationEntry == null || !stackUnificationItems.containsKey(unificationEntry) || !unificationEntry.orePrefix.isUnificationEnabled)
            return itemStack;
        ArrayList<ItemAndMetadata> keys = stackUnificationItems.get(unificationEntry);
        keys.sort(getSimpleItemStackComparator());
        return keys.size() > 0 ? keys.get(0).toItemStack(itemStack.getCount()) : itemStack;
    }

    public static ItemStack get(UnificationEntry unificationEntry) {
        return get(unificationEntry.orePrefix, unificationEntry.material);
    }

    public static ItemStack get(EnumOrePrefix orePrefix, Material material) {
        return get(orePrefix, material, 1);
    }

    public static ItemStack get(EnumOrePrefix orePrefix, Material material, int stackSize) {
        UnificationEntry unificationEntry = new UnificationEntry(orePrefix, material);
        if (!stackUnificationItems.containsKey(unificationEntry))
            return ItemStack.EMPTY;
        ArrayList<ItemAndMetadata> keys = stackUnificationItems.get(unificationEntry);
        keys.sort(getSimpleItemStackComparator());
        return keys.size() > 0 ? keys.get(0).toItemStack(stackSize) : ItemStack.EMPTY;
    }

    public static List<Entry<ItemStack, ItemMaterialInfo>> getAllItemInfos() {
        return materialUnificationInfo.entrySet().stream()
            .map(entry -> new SimpleEntry<>(entry.getKey().toItemStack(), entry.getValue()))
            .collect(Collectors.toList());
    }

    public static List<ItemStack> getAll(UnificationEntry unificationEntry) {
        if (!stackUnificationItems.containsKey(unificationEntry))
            return Collections.emptyList();
        ArrayList<ItemAndMetadata> keys = stackUnificationItems.get(unificationEntry);
        keys.sort(getSimpleItemStackComparator());
        return keys.stream().map(ItemAndMetadata::toItemStack).collect(Collectors.toList());
    }

    public static ItemStack getDust(DustMaterial material, long materialAmount) {
        if (materialAmount <= 0)
            return ItemStack.EMPTY;
        if (materialAmount % M == 0 || materialAmount >= M * 16)
            return get(EnumOrePrefix.dust, material, (int) (materialAmount / M));
        else if ((materialAmount * 9) >= M)
            return get(EnumOrePrefix.dustTiny, material, (int) ((materialAmount * 9) / M));
        return ItemStack.EMPTY;
    }

    public static ItemStack getDust(MaterialStack materialStack) {
        return getDust((DustMaterial) materialStack.material, materialStack.amount);
    }

    public static ItemStack getIngot(IngotMaterial material, long materialAmount) {
        if (materialAmount <= 0)
            return ItemStack.EMPTY;
        if (materialAmount % M == 0 || materialAmount >= M * 16)
            return get(EnumOrePrefix.ingot, material, (int) (materialAmount / M));
        else if ((materialAmount * 9) >= M)
            return get(EnumOrePrefix.nugget, material, (int) ((materialAmount * 9) / M));
        return ItemStack.EMPTY;
    }

}