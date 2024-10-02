package gregtech.loaders.postload;

import static gregtech.api.enums.Mods.BetterLoadingScreen;
import static gregtech.api.enums.Mods.Forestry;
import static gregtech.api.enums.Mods.GalacticraftCore;
import static gregtech.api.enums.Mods.GalacticraftMars;
import static gregtech.api.enums.Mods.GalaxySpace;
import static gregtech.api.enums.Mods.Thaumcraft;
import static gregtech.api.recipe.RecipeMaps.fluidCannerRecipes;
import static gregtech.api.recipe.RecipeMaps.massFabFakeRecipes;
import static gregtech.api.recipe.RecipeMaps.rockBreakerFakeRecipes;
import static gregtech.api.recipe.RecipeMaps.scannerFakeRecipes;
import static gregtech.api.util.GTRecipeBuilder.MINUTES;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeBuilder.TICKS;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.google.common.base.Stopwatch;

import cpw.mods.fml.common.ProgressManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.GTMod;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.SubTag;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.GTCLSCompat;
import gregtech.api.util.GTForestryCompat;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTRecipeBuilder;
import gregtech.api.util.GTRecipeConstants;
import gregtech.api.util.GTRecipeRegistrator;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaGeneratedTool01;
import gregtech.common.items.behaviors.BehaviourDataOrb;
import gregtech.common.tileentities.machines.basic.MTEMassfabricator;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;

@SuppressWarnings("deprecation")
public class GTPostLoad {

    public static void activateOreDictHandler() {
        @SuppressWarnings("UnstableApiUsage") // Stable enough for this project
        Stopwatch stopwatch = Stopwatch.createStarted();
        GTMod.gregtechproxy.activateOreDictHandler();

        // noinspection UnstableApiUsage// Stable enough for this project
        GTMod.GT_FML_LOGGER
            .info("Congratulations, you have been waiting long enough (" + stopwatch.stop() + "). Have a Cake.");
        GTLog.out.println(
            "GTMod: List of Lists of Tool Recipes: " + GTModHandler.sSingleNonBlockDamagableRecipeList_list.toString());
        GTLog.out.println(
            "GTMod: Vanilla Recipe List -> Outputs null or stackSize <=0: "
                + GTModHandler.sVanillaRecipeList_warntOutput.toString());
        GTLog.out.println(
            "GTMod: Single Non Block Damageable Recipe List -> Outputs null or stackSize <=0: "
                + GTModHandler.sSingleNonBlockDamagableRecipeList_warntOutput.toString());
    }

    public static void removeIc2Recipes(Map<IRecipeInput, RecipeOutput> aMaceratorRecipeList,
        Map<IRecipeInput, RecipeOutput> aCompressorRecipeList, Map<IRecipeInput, RecipeOutput> aExtractorRecipeList,
        Map<IRecipeInput, RecipeOutput> aOreWashingRecipeList,
        Map<IRecipeInput, RecipeOutput> aThermalCentrifugeRecipeList) {
        @SuppressWarnings("UnstableApiUsage") // Stable enough for this project
        Stopwatch stopwatch = Stopwatch.createStarted();
        // remove gemIridium exploit
        ItemStack iridiumOre = GTModHandler.getIC2Item("iridiumOre", 1);
        aCompressorRecipeList.entrySet()
            .parallelStream()
            .filter(
                e -> e.getKey()
                    .getInputs()
                    .size() == 1 && e.getKey()
                        .getInputs()
                        .get(0)
                        .isItemEqual(iridiumOre))
            .findAny()
            .ifPresent(e -> aCompressorRecipeList.remove(e.getKey()));
        // Remove all IC2
        GTModHandler.removeAllIC2Recipes();
        // noinspection UnstableApiUsage// Stable enough for this project
        GTMod.GT_FML_LOGGER.info("IC2 Removal (" + stopwatch.stop() + "). Have a Cake.");
    }

    public static void registerFluidCannerRecipes() {
        for (FluidContainerRegistry.FluidContainerData tData : FluidContainerRegistry
            .getRegisteredFluidContainerData()) {
            if (tData.fluid.amount <= 0) {
                continue;
            }
            // lava clay bucket is registered with empty container with 0 stack size
            ItemStack emptyContainer = tData.emptyContainer.copy();
            emptyContainer.stackSize = 1;
            GTValues.RA.stdBuilder()
                .itemInputs(emptyContainer)
                .itemOutputs(tData.filledContainer)
                .fluidInputs(tData.fluid)
                .duration((tData.fluid.amount / 62) * TICKS)
                .eut(1)
                .addTo(fluidCannerRecipes);
            GTRecipeBuilder builder = GTValues.RA.stdBuilder()
                .itemInputs(tData.filledContainer);
            if (tData.emptyContainer.stackSize > 0) {
                builder.itemOutputs(tData.emptyContainer);
            }
            builder.fluidOutputs(tData.fluid)
                .duration((tData.fluid.amount / 62) * TICKS)
                .eut(1)
                .addTo(fluidCannerRecipes);
        }
    }

    public static void addFakeRecipes() {
        GTLog.out.println("GTMod: Adding Fake Recipes for NEI");

        if (Forestry.isModLoaded()) {
            GTForestryCompat.populateFakeNeiRecipes();
        }

        if (ItemList.IC2_Crop_Seeds.get(1L) != null) {
            GTValues.RA.stdBuilder()
                .itemInputs(ItemList.IC2_Crop_Seeds.getWildcard(1L))
                .itemOutputs(ItemList.IC2_Crop_Seeds.getWithName(1L, "Scanned Seeds"))
                .duration(8 * SECONDS)
                .eut(8)
                .noOptimize()
                .ignoreCollision()
                .fake()
                .addTo(scannerFakeRecipes);
        }
        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(Items.written_book, 1, 32767))
            .itemOutputs(ItemList.Tool_DataStick.getWithName(1L, "Scanned Book Data"))
            .special(ItemList.Tool_DataStick.getWithName(1L, "Stick to save it to"))
            .duration(6 * SECONDS + 8 * TICKS)
            .eut(TierEU.RECIPE_LV)
            .noOptimize()
            .ignoreCollision()
            .fake()
            .addTo(scannerFakeRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(Items.filled_map, 1, 32767))
            .itemOutputs(ItemList.Tool_DataStick.getWithName(1L, "Scanned Map Data"))
            .special(ItemList.Tool_DataStick.getWithName(1L, "Stick to save it to"))
            .duration(6 * SECONDS + 8 * TICKS)
            .eut(TierEU.RECIPE_LV)
            .noOptimize()
            .ignoreCollision()
            .fake()
            .addTo(scannerFakeRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(ItemList.Tool_DataOrb.getWithName(1L, "Orb to overwrite"))
            .itemOutputs(ItemList.Tool_DataOrb.getWithName(1L, "Copy of the Orb"))
            .duration(25 * SECONDS + 12 * TICKS)
            .eut(TierEU.RECIPE_LV)
            .noOptimize()
            .ignoreCollision()
            .fake()
            .addTo(scannerFakeRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(ItemList.Tool_DataStick.getWithName(1L, "Stick to overwrite"))
            .itemOutputs(ItemList.Tool_DataStick.getWithName(1L, "Copy of the Stick"))
            .special(ItemList.Tool_DataStick.getWithName(0L, "Stick to copy"))
            .duration(6 * SECONDS + 8 * TICKS)
            .eut(TierEU.RECIPE_LV)
            .noOptimize()
            .ignoreCollision()
            .fake()
            .addTo(scannerFakeRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(ItemList.Tool_DataStick.getWithName(1L, "Raw Prospection Data"))
            .itemOutputs(ItemList.Tool_DataStick.getWithName(1L, "Analyzed Prospection Data"))
            .duration(50 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .noOptimize()
            .ignoreCollision()
            .fake()
            .addTo(scannerFakeRecipes);

        if (GalacticraftCore.isModLoaded()) {
            GTValues.RA.stdBuilder()
                .itemInputs(
                    Objects
                        .requireNonNull(
                            GTModHandler.getModItem(GalacticraftCore.ID, "item.schematic", 1, Short.MAX_VALUE))
                        .setStackDisplayName("Any Schematic"))
                .itemOutputs(ItemList.Tool_DataStick.getWithName(1L, "Scanned Schematic"))
                .special(ItemList.Tool_DataStick.getWithName(1L, "Stick to save it to"))
                .duration(30 * MINUTES)
                .eut(TierEU.RECIPE_HV)
                .noOptimize()
                .ignoreCollision()
                .fake()
                .addTo(scannerFakeRecipes);

            if (GalacticraftMars.isModLoaded()) {
                GTValues.RA.stdBuilder()
                    .itemInputs(
                        Objects
                            .requireNonNull(
                                GTModHandler.getModItem(GalacticraftMars.ID, "item.schematic", 1, Short.MAX_VALUE))
                            .setStackDisplayName("Any Schematic"))
                    .itemOutputs(ItemList.Tool_DataStick.getWithName(1L, "Scanned Schematic"))
                    .special(ItemList.Tool_DataStick.getWithName(1L, "Stick to save it to"))
                    .duration(30 * MINUTES)
                    .eut(TierEU.RECIPE_MV)
                    .noOptimize()
                    .ignoreCollision()
                    .fake()
                    .addTo(scannerFakeRecipes);
            }
            if (GalaxySpace.isModLoaded()) {
                for (int i = 4; i < 9; i++) {
                    GTValues.RA.stdBuilder()
                        .itemInputs(
                            GTModHandler.getModItem(GalaxySpace.ID, "item.SchematicTier" + i, 1)
                                .setStackDisplayName("Any Schematic"))
                        .itemOutputs(ItemList.Tool_DataStick.getWithName(1L, "Scanned Schematic"))
                        .special(ItemList.Tool_DataStick.getWithName(1L, "Stick to save it to"))
                        .duration(30 * MINUTES)
                        .eut(TierEU.RECIPE_HV)
                        .noOptimize()
                        .ignoreCollision()
                        .fake()
                        .addTo(scannerFakeRecipes);
                }
            }
        }
        Materials.getMaterialsMap()
            .values()
            .forEach(tMaterial -> {
                if ((tMaterial.mElement != null) && (!tMaterial.mElement.mIsIsotope)
                    && (tMaterial != Materials.Magic)
                    && (tMaterial.getMass() > 0L)) {
                    ItemStack dataOrb = ItemList.Tool_DataOrb.get(1L);
                    BehaviourDataOrb.setDataTitle(dataOrb, "Elemental-Scan");
                    BehaviourDataOrb.setDataName(dataOrb, tMaterial.mElement.name());
                    ItemStack dustItem = GTOreDictUnificator.get(OrePrefixes.dust, tMaterial, 1L);
                    if (dustItem != null) {
                        GTValues.RA.stdBuilder()
                            .itemInputs(dustItem)
                            .itemOutputs(dataOrb)
                            .special(ItemList.Tool_DataOrb.get(1L))
                            .duration((int) (tMaterial.getMass() * 8192L))
                            .eut(TierEU.RECIPE_LV)
                            .fake()
                            .ignoreCollision()
                            .addTo(scannerFakeRecipes);
                        GTValues.RA.stdBuilder()
                            .itemOutputs(dustItem)
                            .special(dataOrb)
                            .metadata(GTRecipeConstants.MATERIAL, tMaterial)
                            .addTo(RecipeMaps.replicatorRecipes);
                        return;
                    }
                    ItemStack cellItem = GTOreDictUnificator.get(OrePrefixes.cell, tMaterial, 1L);
                    if (cellItem != null) {
                        GTValues.RA.stdBuilder()
                            .itemInputs(cellItem)
                            .itemOutputs(dataOrb)
                            .special(ItemList.Tool_DataOrb.get(1L))
                            .duration((int) (tMaterial.getMass() * 8192L))
                            .eut(TierEU.RECIPE_LV)
                            .fake()
                            .ignoreCollision()
                            .addTo(scannerFakeRecipes);
                        FluidStack fluidStack = GTUtility.getFluidForFilledItem(cellItem, false);
                        GTRecipeBuilder builder = GTValues.RA.stdBuilder();
                        if (fluidStack != null) {
                            builder.fluidOutputs(fluidStack);
                        } else {
                            builder.itemInputs(Materials.Empty.getCells(1))
                                .itemOutputs(cellItem);
                        }
                        builder.special(dataOrb)
                            .metadata(GTRecipeConstants.MATERIAL, tMaterial)
                            .addTo(RecipeMaps.replicatorRecipes);
                    }
                }
            });

        if (!MTEMassfabricator.sRequiresUUA) {

            MTEMassfabricator.nonUUARecipe = GTValues.RA.stdBuilder()
                .fluidOutputs(Materials.UUMatter.getFluid(1L))
                .duration(MTEMassfabricator.sDurationMultiplier)
                .eut(MTEMassfabricator.BASE_EUT)
                .ignoreCollision()
                .noOptimize()
                .fake()
                .build()
                .get();

            massFabFakeRecipes.add(MTEMassfabricator.nonUUARecipe);

        }

        MTEMassfabricator.uuaRecipe = GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(1))
            .fluidInputs(Materials.UUAmplifier.getFluid(MTEMassfabricator.sUUAperUUM))
            .fluidOutputs(Materials.UUMatter.getFluid(1L))
            .duration(MTEMassfabricator.sDurationMultiplier / MTEMassfabricator.sUUASpeedBonus)
            .eut(MTEMassfabricator.BASE_EUT)
            .ignoreCollision()
            .noOptimize()
            .fake()
            .build()
            .get();

        massFabFakeRecipes.add(MTEMassfabricator.uuaRecipe);

        GTValues.RA.stdBuilder()
            .itemInputs(ItemList.Display_ITS_FREE.getWithName(1L, "IT'S FREE! Place Lava on Side"))
            .itemOutputs(new ItemStack(Blocks.cobblestone, 1))
            .duration(16 * TICKS)
            .eut(TierEU.RECIPE_LV)
            .ignoreCollision()
            .noOptimize()
            .fake()
            .addTo(rockBreakerFakeRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(ItemList.Display_ITS_FREE.getWithName(1L, "IT'S FREE! Place Lava on Side"))
            .itemOutputs(new ItemStack(Blocks.stone, 1))
            .duration(16 * TICKS)
            .eut(TierEU.RECIPE_LV)
            .ignoreCollision()
            .noOptimize()
            .fake()
            .addTo(rockBreakerFakeRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 1L),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(new ItemStack(Blocks.obsidian, 1))
            .duration(6 * SECONDS + 8 * TICKS)
            .eut(TierEU.RECIPE_LV)
            .ignoreCollision()
            .noOptimize()
            .fake()
            .addTo(rockBreakerFakeRecipes);
    }

    public static void nerfVanillaTools() {
        if (!GTMod.gregtechproxy.mNerfedVanillaTools) {
            return;
        }

        GTLog.out.println("GTMod: Nerfing Vanilla Tool Durability");
        Items.wooden_sword.setMaxDamage(12);
        Items.wooden_pickaxe.setMaxDamage(12);
        Items.wooden_shovel.setMaxDamage(12);
        Items.wooden_axe.setMaxDamage(12);
        Items.wooden_hoe.setMaxDamage(12);

        Items.stone_sword.setMaxDamage(48);
        Items.stone_pickaxe.setMaxDamage(48);
        Items.stone_shovel.setMaxDamage(48);
        Items.stone_axe.setMaxDamage(48);
        Items.stone_hoe.setMaxDamage(48);

        Items.iron_sword.setMaxDamage(256);
        Items.iron_pickaxe.setMaxDamage(256);
        Items.iron_shovel.setMaxDamage(256);
        Items.iron_axe.setMaxDamage(256);
        Items.iron_hoe.setMaxDamage(256);

        Items.golden_sword.setMaxDamage(24);
        Items.golden_pickaxe.setMaxDamage(24);
        Items.golden_shovel.setMaxDamage(24);
        Items.golden_axe.setMaxDamage(24);
        Items.golden_hoe.setMaxDamage(24);

        Items.diamond_sword.setMaxDamage(768);
        Items.diamond_pickaxe.setMaxDamage(768);
        Items.diamond_shovel.setMaxDamage(768);
        Items.diamond_axe.setMaxDamage(768);
        Items.diamond_hoe.setMaxDamage(768);

    }

    public static void replaceVanillaMaterials() {
        @SuppressWarnings("UnstableApiUsage") // Stable enough for this project
        Stopwatch stopwatch = Stopwatch.createStarted();
        GTMod.GT_FML_LOGGER.info("Replacing Vanilla Materials in recipes, please wait.");
        Set<Materials> replaceVanillaItemsSet = Arrays.stream(Materials.values())
            .filter(GTRecipeRegistrator::hasVanillaRecipes)
            .collect(Collectors.toSet());

        ProgressManager.ProgressBar progressBar = ProgressManager
            .push("Register materials", replaceVanillaItemsSet.size());
        if (BetterLoadingScreen.isModLoaded()) {
            GTCLSCompat.doActualRegistrationCLS(progressBar, replaceVanillaItemsSet);
            GTCLSCompat.pushToDisplayProgress();
        } else {
            replaceVanillaItemsSet.forEach(m -> {
                progressBar.step(m.mDefaultLocalName);
                doActualRegistration(m);
            });
        }
        ProgressManager.pop(progressBar);
        // noinspection UnstableApiUsage// stable enough for project
        GTMod.GT_FML_LOGGER.info("Replaced Vanilla Materials (" + stopwatch.stop() + "). Have a Cake.");
    }

    public static void doActualRegistration(Materials m) {
        String plateName = OrePrefixes.plate.get(m)
            .toString();
        boolean noSmash = !m.contains(SubTag.NO_SMASHING);
        if ((m.mTypes & 2) != 0) GTRecipeRegistrator.registerUsagesForMaterials(plateName, noSmash, m.getIngots(1));
        if ((m.mTypes & 4) != 0) GTRecipeRegistrator.registerUsagesForMaterials(plateName, noSmash, m.getGems(1));
        if (m.getBlocks(1) != null) GTRecipeRegistrator.registerUsagesForMaterials(null, noSmash, m.getBlocks(1));
    }

    public static void createGTtoolsCreativeTab() {
        new CreativeTabs("GTtools") {

            @SideOnly(Side.CLIENT)
            @Override
            public ItemStack getIconItemStack() {
                return ItemList.Tool_Cheat.get(1, new ItemStack(Blocks.iron_block, 1));
            }

            @SideOnly(Side.CLIENT)
            @Override
            public Item getTabIconItem() {
                return ItemList.Circuit_Integrated.getItem();
            }

            @Override
            public void displayAllReleventItems(List<ItemStack> aList) {

                for (int i = 0; i < 32766; i += 2) {
                    if (MetaGeneratedTool01.INSTANCE.getToolStats(new ItemStack(MetaGeneratedTool01.INSTANCE, 1, i))
                        == null) {
                        continue;
                    }

                    ItemStack tStack = new ItemStack(MetaGeneratedTool01.INSTANCE, 1, i);
                    MetaGeneratedTool01.INSTANCE.isItemStackUsable(tStack);
                    aList
                        .add(MetaGeneratedTool01.INSTANCE.getToolWithStats(i, 1, Materials.Lead, Materials.Lead, null));
                    aList.add(
                        MetaGeneratedTool01.INSTANCE.getToolWithStats(i, 1, Materials.Nickel, Materials.Nickel, null));
                    aList.add(
                        MetaGeneratedTool01.INSTANCE.getToolWithStats(i, 1, Materials.Cobalt, Materials.Cobalt, null));
                    aList.add(
                        MetaGeneratedTool01.INSTANCE.getToolWithStats(i, 1, Materials.Osmium, Materials.Osmium, null));
                    aList.add(
                        MetaGeneratedTool01.INSTANCE
                            .getToolWithStats(i, 1, Materials.Adamantium, Materials.Adamantium, null));
                    aList.add(
                        MetaGeneratedTool01.INSTANCE
                            .getToolWithStats(i, 1, Materials.Neutronium, Materials.Neutronium, null));

                }
                super.displayAllReleventItems(aList);
            }
        };
    }

    public static void addSolidFakeLargeBoilerFuels() {
        RecipeMaps.largeBoilerFakeFuels.getBackend()
            .addSolidRecipes(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Charcoal, 1),
                GTOreDictUnificator.get(OrePrefixes.gem, Materials.Charcoal, 1),
                GTOreDictUnificator.get(OrePrefixes.block, Materials.Charcoal, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Coal, 1),
                GTOreDictUnificator.get(OrePrefixes.gem, Materials.Coal, 1),
                GTOreDictUnificator.get(OrePrefixes.block, Materials.Coal, 1),
                GTOreDictUnificator.get(OrePrefixes.crushed, Materials.Coal, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Lignite, 1),
                GTOreDictUnificator.get(OrePrefixes.gem, Materials.Lignite, 1),
                GTOreDictUnificator.get(OrePrefixes.block, Materials.Lignite, 1),
                GTOreDictUnificator.get(OrePrefixes.crushed, Materials.Lignite, 1),
                GTOreDictUnificator.get(OrePrefixes.log, Materials.Wood, 1),
                GTOreDictUnificator.get(OrePrefixes.plank, Materials.Wood, 1),
                GTOreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 1),
                GTOreDictUnificator.get(OrePrefixes.slab, Materials.Wood, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sodium, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Lithium, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Caesium, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sulfur, 1),
                GTOreDictUnificator.get(ItemList.Block_SSFUEL.get(1)),
                GTOreDictUnificator.get(ItemList.Block_MSSFUEL.get(1)),
                GTOreDictUnificator.get(OrePrefixes.rod, Materials.Blaze, 1));
        if (Thaumcraft.isModLoaded()) {
            RecipeMaps.largeBoilerFakeFuels.getBackend()
                .addSolidRecipe(GTModHandler.getModItem(Thaumcraft.ID, "ItemResource", 1));
        }
    }

    public static void identifyAnySteam() {
        final String[] steamCandidates = { "steam", "ic2steam" };
        final String[] superHeatedSteamCandidates = { "ic2superheatedsteam" };

        GTModHandler.sAnySteamFluidIDs = Arrays.stream(steamCandidates)
            .map(FluidRegistry::getFluid)
            .filter(Objects::nonNull)
            .map(FluidRegistry::getFluidID)
            .collect(Collectors.toList());
        GTModHandler.sSuperHeatedSteamFluidIDs = Arrays.stream(superHeatedSteamCandidates)
            .map(FluidRegistry::getFluid)
            .filter(Objects::nonNull)
            .map(FluidRegistry::getFluidID)
            .collect(Collectors.toList());
    }
}