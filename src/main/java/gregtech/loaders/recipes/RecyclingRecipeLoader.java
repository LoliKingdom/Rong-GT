package gregtech.loaders.recipes;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.GemMaterial;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.Material.MatFlags;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static gregtech.api.GTValues.L;
import static gregtech.api.GTValues.M;

public class RecyclingRecipeLoader {

    public static void init() {
    	initializeRecyclingRecipes();
    }

    private static void initializeRecyclingRecipes() {
        for(Entry<ItemStack, ItemMaterialInfo> entry : OreDictUnifier.getAllItemInfos()) {
            ItemStack itemStack = entry.getKey();
            ItemMaterialInfo materialInfo = entry.getValue();
            ArrayList<MaterialStack> materialStacks = new ArrayList<>();
            materialStacks.add(materialInfo.material);
            materialStacks.addAll(materialInfo.additionalComponents);
            registerRecyclingRecipe(b -> b.inputs(itemStack), materialStacks, false);
        }
    }

    public static void registerRecyclingRecipe(Consumer<RecipeBuilder<?>> inputSupplier, List<MaterialStack> components, boolean ignoreArcSmelting) {
        List<MaterialStack> dustMaterials = components.stream()
            .filter(stack -> stack.material instanceof DustMaterial)
            .filter(stack -> stack.amount >= M / 9) //do only materials which have at least one nugget
            .collect(Collectors.toList());
        if(dustMaterials.isEmpty()) return;
        MaterialStack firstStack = dustMaterials.get(0);
        DustMaterial dustMaterial = (DustMaterial) firstStack.material;
        int voltageMultiplier = 1;
        if(dustMaterial instanceof IngotMaterial) {
            int blastFurnaceTemperature = ((IngotMaterial) dustMaterial).blastFurnaceTemperature;
            voltageMultiplier = blastFurnaceTemperature == 0 ? 16 : blastFurnaceTemperature > 2000 ? 480 : 96;
        } 
        else {
            //do not apply arc smelting for gems, solid materials and dust materials
            //only generate recipes for ingot materials
            ignoreArcSmelting = true;
        }
        if(dustMaterial.shouldGenerateFluid()) {
        	if(dustMaterial instanceof GemMaterial) {
        		RecipeBuilder<?> fluidHeaterRecipeBuilder = RecipeMaps.FLUID_HEATER_RECIPES.recipeBuilder()
        				.fluidInputs(Materials.Lava.getFluid(20))
                        .fluidOutputs(dustMaterial.getFluid((int) (firstStack.amount * L / M)))
                        .duration((int) Math.max(1L, firstStack.amount * 80 / M))
                        .EUt(4 * voltageMultiplier);
                    inputSupplier.accept(fluidHeaterRecipeBuilder);
                    fluidHeaterRecipeBuilder.buildAndRegister();
        	}
        	else {
        		RecipeBuilder<?> fluidExtractorRecipeBuilder = RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
                        .fluidOutputs(dustMaterial.getFluid((int) (firstStack.amount * L / M)))
                        .duration((int) Math.max(1L, firstStack.amount * 80 / M))
                        .EUt(4 * voltageMultiplier);
                    inputSupplier.accept(fluidExtractorRecipeBuilder);
                    fluidExtractorRecipeBuilder.buildAndRegister();
        	}
        }
        else {
        	RecipeBuilder<?> fluidExtractorRecipeBuilder = RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
                    .outputs(dustMaterials.stream().map(OreDictUnifier::getDust).findFirst().get())
                    .duration((int) Math.max(1L, firstStack.amount * 80 / M))
                    .EUt(4 * voltageMultiplier);
                inputSupplier.accept(fluidExtractorRecipeBuilder);
                fluidExtractorRecipeBuilder.buildAndRegister();
        }
        if(!ignoreArcSmelting) {
            List<ItemStack> resultList = dustMaterials.stream().map(RecyclingRecipeLoader::getArcSmeltingResult).collect(Collectors.toList());
            resultList.removeIf(ItemStack::isEmpty);
            if(resultList.isEmpty()) return;
            RecipeBuilder<?> recycleBuilder = RecipeMaps.RECYCLING_RECIPES.recipeBuilder()
                .outputs(resultList)
                .duration((int) Math.max(1L, firstStack.amount * 60 / M))
                .EUt(30 * voltageMultiplier);
            inputSupplier.accept(recycleBuilder);
            recycleBuilder.buildAndRegister();
        }
    }

    private static ItemStack getArcSmeltingResult(MaterialStack materialStack) {
        DustMaterial material = (DustMaterial) materialStack.material;
        long materialAmount = materialStack.amount;
        if(material.hasFlag(MatFlags.FLAMMABLE)) {
            return OreDictUnifier.getDust(Materials.Ash, materialAmount);
        } 
        else if(material instanceof GemMaterial) {
            if(materialStack.material.materialComponents.stream()
                .anyMatch(stack -> stack.material == Materials.Oxygen)) {
                return OreDictUnifier.getDust(Materials.Ash, materialAmount);
            }
            if(materialStack.material.materialComponents.stream()
                .anyMatch(stack -> stack.material == Materials.Carbon)) {
                return OreDictUnifier.getDust(Materials.Carbon, materialAmount);
            }
            return OreDictUnifier.getDust(Materials.DarkAsh, materialAmount);
        } 
        else if(material instanceof IngotMaterial) {
            IngotMaterial ingotMaterial = (IngotMaterial) material;
            if(ingotMaterial.recycleTo != null) {
                ingotMaterial = ingotMaterial.recycleTo;
            }
            return OreDictUnifier.getIngot(ingotMaterial, materialAmount);
        } 
        else {
            return OreDictUnifier.getDust(material, materialAmount);
        }
    }
}