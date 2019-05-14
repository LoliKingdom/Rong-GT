package gregtech.loaders.recipes.processing;

import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.loaders.recipes.MachineRecipeLoader;
import gregtech.loaders.recipes.RecyclingRecipeLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class RecyclingRecipeHandler {

    private static final List<Object> CRUSHING_PREFIXES = Arrays.asList(
        OrePrefix.ingot, OrePrefix.gem, OrePrefix.stick, OrePrefix.plate, OrePrefix.plank,
        OrePrefix.ring, OrePrefix.foil,
        OrePrefix.screw, OrePrefix.nugget, OrePrefix.gearSmall, OrePrefix.gear,
        OrePrefix.frameGt, OrePrefix.spring,
        OrePrefix.block, OrePrefix.wireFine,
        OrePrefix.rotor, OrePrefix.lens, OrePrefix.turbineBlade, OrePrefix.crystal,
        (Predicate<OrePrefix>) orePrefix -> orePrefix.name().startsWith("toolHead"),
        (Predicate<OrePrefix>) orePrefix -> orePrefix.name().startsWith("gem"),
        (Predicate<OrePrefix>) orePrefix -> orePrefix.name().startsWith("cableGt"),
        (Predicate<OrePrefix>) orePrefix -> orePrefix.name().startsWith("wireGt"),
        (Predicate<OrePrefix>) orePrefix -> orePrefix.name().startsWith("pipe")
    );

    private static final List<OrePrefix> IGNORE_RECYCLING = Arrays.asList(
        OrePrefix.ingot, OrePrefix.gem, OrePrefix.nugget, OrePrefix.block);

    public static void register() {
        //registers universal maceration recipes for specified ore prefixes
        for(OrePrefix orePrefix : OrePrefix.values()) {
            if(CRUSHING_PREFIXES.stream().anyMatch(object -> {
                if(object instanceof OrePrefix)
                    return object == orePrefix;
                else if(object instanceof Predicate)
                    return ((Predicate<OrePrefix>) object).test(orePrefix);
                else return false;
            })) orePrefix.addProcessingHandler(DustMaterial.class, RecyclingRecipeHandler::process);
        }
    }

    public static void process(OrePrefix prefix, DustMaterial material) {
        ArrayList<MaterialStack> materialStacks = new ArrayList<>();
        materialStacks.add(new MaterialStack(material, prefix.getMaterialAmount(material)));
        materialStacks.addAll(prefix.secondaryMaterials);
        //only ignore arc smelting for blacklisted prefixes if yielded material is the same as input material
        //if arc smelting gives different material, allow it
        boolean ignoreArcSmelting = IGNORE_RECYCLING.contains(prefix) && !(
            material instanceof IngotMaterial && ((IngotMaterial)material).recycleTo != material);
        RecyclingRecipeLoader.registerRecyclingRecipe(builder -> builder.input(prefix, material), materialStacks, ignoreArcSmelting);
    }
}