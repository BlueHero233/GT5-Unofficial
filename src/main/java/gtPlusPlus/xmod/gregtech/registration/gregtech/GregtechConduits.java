package gtPlusPlus.xmod.gregtech.registration.gregtech;

import static gregtech.api.enums.Mods.EnderIO;
import static gregtech.api.enums.Mods.Thaumcraft;
import static gregtech.api.recipe.RecipeMaps.*;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeBuilder.TICKS;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.metatileentity.implementations.MTEFluid;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;
import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.core.material.Material;
import gtPlusPlus.core.material.MaterialsAlloy;
import gtPlusPlus.core.material.MaterialsElements;
import gtPlusPlus.core.recipe.common.CI;
import gtPlusPlus.core.util.Utils;
import gtPlusPlus.core.util.minecraft.FluidUtils;
import gtPlusPlus.core.util.minecraft.ItemUtils;
import gtPlusPlus.core.util.minecraft.MaterialUtils;
import gtPlusPlus.core.util.minecraft.RecipeUtils;
import gtPlusPlus.xmod.gregtech.api.enums.GregtechOrePrefixes.GT_Materials;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.GTPPMTECable;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.GTPPMTEFluid;

public class GregtechConduits {

    // 30000-30999
    private static final int BaseWireID = 30600;
    private static final int BasePipeID = 30700;
    private static int BasePipeHexadecupleID = 30100;

    public static void run() {
        run1();
        run2();
        run3();
    }

    private static void run3() {
        generateFluidMultiPipes(
            Materials.Copper,
            MaterialUtils.getMaterialName(Materials.Copper),
            "Copper",
            BasePipeHexadecupleID++,
            60,
            1000);
        generateFluidMultiPipes(
            Materials.Bronze,
            MaterialUtils.getMaterialName(Materials.Bronze),
            "Bronze",
            BasePipeHexadecupleID++,
            120,
            2000);
        generateFluidMultiPipes(
            Materials.Steel,
            MaterialUtils.getMaterialName(Materials.Steel),
            "Steel",
            BasePipeHexadecupleID++,
            240,
            2500);
        generateFluidMultiPipes(
            Materials.StainlessSteel,
            MaterialUtils.getMaterialName(Materials.StainlessSteel),
            "Stainless Steel",
            BasePipeHexadecupleID++,
            360,
            3000);
        generateFluidMultiPipes(
            Materials.Titanium,
            MaterialUtils.getMaterialName(Materials.Titanium),
            "Titanium",
            BasePipeHexadecupleID++,
            480,
            5000);
        generateFluidMultiPipes(
            Materials.TungstenSteel,
            MaterialUtils.getMaterialName(Materials.TungstenSteel),
            "Tungsten Steel",
            BasePipeHexadecupleID++,
            600,
            7500);
        generateFluidMultiPipes(
            Materials.Plastic,
            MaterialUtils.getMaterialName(Materials.Plastic),
            "Plastic",
            BasePipeHexadecupleID++,
            360,
            350);

        Materials aPTFE = Materials.get("Polytetrafluoroethylene");
        generateFluidMultiPipes(aPTFE, MaterialUtils.getMaterialName(aPTFE), "PTFE", BasePipeHexadecupleID++, 480, 600);
    }

    private static void generateFluidMultiPipes(Materials aMaterial, String name, String displayName, int startID,
        int transferRatePerSec, int heatCapacity) {
        final int transferRatePerTick = transferRatePerSec / 20;
        MTEFluid aPipe = new MTEFluid(
            startID,
            "GT_Pipe_" + name + "_Hexadecuple",
            "Hexadecuple " + displayName + " Fluid Pipe",
            1.0F,
            aMaterial,
            transferRatePerTick,
            heatCapacity,
            true,
            16);
        GTOreDictUnificator.registerOre("pipeHexadecuple" + aMaterial, aPipe.getStackForm(1L));
    }

    private static void run1() {
        wireFactory("RedstoneAlloy", 32, BaseWireID + 45, 0, 2, 1, new short[] { 178, 34, 34, 0 });
        // need to go back id because fluid pipes already occupy
        makeCustomWires(MaterialsElements.STANDALONE.HYPOGEN, BaseWireID - 15, 0, 0, 8, GTValues.V[11], false, true);
    }

    private static void run2() {
        generateNonGTFluidPipes(GT_Materials.Staballoy, MaterialsAlloy.STABALLOY, BasePipeID, 12500, 7500, true);
        generateNonGTFluidPipes(
            GT_Materials.Tantalloy60,
            MaterialsAlloy.TANTALLOY_60,
            BasePipeID + 5,
            10000,
            4250,
            true);
        generateNonGTFluidPipes(
            GT_Materials.Tantalloy61,
            MaterialsAlloy.TANTALLOY_61,
            BasePipeID + 10,
            12000,
            5800,
            true);
        if (Thaumcraft.isModLoaded()) {
            generateNonGTFluidPipes(GT_Materials.Void, null, BasePipeID + 15, 1600, 25000, true);
        }
        generateGTFluidPipes(Materials.Europium, BasePipeID + 20, 12000, 7500, true);
        generateNonGTFluidPipes(GT_Materials.Potin, MaterialsAlloy.POTIN, BasePipeID + 25, 500, 2000, true);
        generateNonGTFluidPipes(
            GT_Materials.MaragingSteel300,
            MaterialsAlloy.MARAGING300,
            BasePipeID + 30,
            14000,
            2500,
            true);
        generateNonGTFluidPipes(
            GT_Materials.MaragingSteel350,
            MaterialsAlloy.MARAGING350,
            BasePipeID + 35,
            16000,
            2500,
            true);
        generateNonGTFluidPipes(
            GT_Materials.Inconel690,
            MaterialsAlloy.INCONEL_690,
            BasePipeID + 40,
            15000,
            4800,
            true);
        generateNonGTFluidPipes(
            GT_Materials.Inconel792,
            MaterialsAlloy.INCONEL_792,
            BasePipeID + 45,
            16000,
            5500,
            true);
        generateNonGTFluidPipes(
            GT_Materials.HastelloyX,
            MaterialsAlloy.HASTELLOY_X,
            BasePipeID + 50,
            20000,
            4200,
            true);

        generateGTFluidPipes(Materials.Tungsten, BasePipeID + 55, 4320, 7200, true);
        if (EnderIO.isModLoaded()) {
            generateGTFluidPipes(Materials.DarkSteel, BasePipeID + 60, 2320, 2750, true);
        }
        generateGTFluidPipes(Materials.Clay, BasePipeID + 65, 100, 500, false);
        generateGTFluidPipes(Materials.Lead, BasePipeID + 70, 350, 1200, true);

        generateNonGTFluidPipes(
            GT_Materials.TriniumNaquadahCarbonite,
            MaterialsAlloy.TRINIUM_NAQUADAH_CARBON,
            30500,
            20,
            250000,
            true);
    }

    private static void wireFactory(final String Material, final int Voltage, final int ID, final long insulatedLoss,
        final long uninsulatedLoss, final long Amps, final short[] rgb) {
        final Materials T = Materials.get(Material);
        int V = GTUtility.getTier(Voltage);
        if (V == -1) {
            Logger.ERROR("Failed to set voltage on " + Material + ". Invalid voltage of " + Voltage + "V set.");
            Logger.ERROR(Material + " has defaulted to 8v.");
            V = 0;
        }
        makeWires(T, ID, insulatedLoss, uninsulatedLoss, Amps, GTValues.V[V], true, false, rgb);
    }

    private static void makeWires(final Materials aMaterial, final int aStartID, final long aLossInsulated,
        final long aLoss, final long aAmperage, final long aVoltage, final boolean aInsulatable,
        final boolean aAutoInsulated, final short[] aRGB) {
        Logger.WARNING("Gregtech5u Content | Registered " + aMaterial.mName + " as a new material for Wire & Cable.");
        GTOreDictUnificator.registerOre(
            OrePrefixes.wireGt01,
            aMaterial,
            new GTPPMTECable(
                aStartID + 0,
                "wire." + aMaterial.mName.toLowerCase() + ".01",
                "1x " + aMaterial.mDefaultLocalName + " Wire",
                0.125F,
                aMaterial,
                aLoss,
                1L * aAmperage,
                aVoltage,
                false,
                !aAutoInsulated,
                aRGB).getStackForm(1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.wireGt02,
            aMaterial,
            new GTPPMTECable(
                aStartID + 1,
                "wire." + aMaterial.mName.toLowerCase() + ".02",
                "2x " + aMaterial.mDefaultLocalName + " Wire",
                0.25F,
                aMaterial,
                aLoss,
                2L * aAmperage,
                aVoltage,
                false,
                !aAutoInsulated,
                aRGB).getStackForm(1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.wireGt04,
            aMaterial,
            new GTPPMTECable(
                aStartID + 2,
                "wire." + aMaterial.mName.toLowerCase() + ".04",
                "4x " + aMaterial.mDefaultLocalName + " Wire",
                0.375F,
                aMaterial,
                aLoss,
                4L * aAmperage,
                aVoltage,
                false,
                !aAutoInsulated,
                aRGB).getStackForm(1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.wireGt08,
            aMaterial,
            new GTPPMTECable(
                aStartID + 3,
                "wire." + aMaterial.mName.toLowerCase() + ".08",
                "8x " + aMaterial.mDefaultLocalName + " Wire",
                0.50F,
                aMaterial,
                aLoss,
                8L * aAmperage,
                aVoltage,
                false,
                !aAutoInsulated,
                aRGB).getStackForm(1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.wireGt12,
            aMaterial,
            new GTPPMTECable(
                aStartID + 4,
                "wire." + aMaterial.mName.toLowerCase() + ".12",
                "12x " + aMaterial.mDefaultLocalName + " Wire",
                0.625F,
                aMaterial,
                aLoss,
                12L * aAmperage,
                aVoltage,
                false,
                !aAutoInsulated,
                aRGB).getStackForm(1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.wireGt16,
            aMaterial,
            new GTPPMTECable(
                aStartID + 5,
                "wire." + aMaterial.mName.toLowerCase() + ".16",
                "16x " + aMaterial.mDefaultLocalName + " Wire",
                0.75F,
                aMaterial,
                aLoss,
                16L * aAmperage,
                aVoltage,
                false,
                !aAutoInsulated,
                aRGB).getStackForm(1L));
        if (aInsulatable) {
            GTOreDictUnificator.registerOre(
                OrePrefixes.cableGt01,
                aMaterial,
                new GTPPMTECable(
                    aStartID + 6,
                    "cable." + aMaterial.mName.toLowerCase() + ".01",
                    "1x " + aMaterial.mDefaultLocalName + " Cable",
                    0.25F,
                    aMaterial,
                    aLossInsulated,
                    1L * aAmperage,
                    aVoltage,
                    true,
                    false,
                    aRGB).getStackForm(1L));
            GTOreDictUnificator.registerOre(
                OrePrefixes.cableGt02,
                aMaterial,
                new GTPPMTECable(
                    aStartID + 7,
                    "cable." + aMaterial.mName.toLowerCase() + ".02",
                    "2x " + aMaterial.mDefaultLocalName + " Cable",
                    0.375F,
                    aMaterial,
                    aLossInsulated,
                    2L * aAmperage,
                    aVoltage,
                    true,
                    false,
                    aRGB).getStackForm(1L));
            GTOreDictUnificator.registerOre(
                OrePrefixes.cableGt04,
                aMaterial,
                new GTPPMTECable(
                    aStartID + 8,
                    "cable." + aMaterial.mName.toLowerCase() + ".04",
                    "4x " + aMaterial.mDefaultLocalName + " Cable",
                    0.5F,
                    aMaterial,
                    aLossInsulated,
                    4L * aAmperage,
                    aVoltage,
                    true,
                    false,
                    aRGB).getStackForm(1L));
            GTOreDictUnificator.registerOre(
                OrePrefixes.cableGt08,
                aMaterial,
                new GTPPMTECable(
                    aStartID + 9,
                    "cable." + aMaterial.mName.toLowerCase() + ".08",
                    "8x " + aMaterial.mDefaultLocalName + " Cable",
                    0.625F,
                    aMaterial,
                    aLossInsulated,
                    8L * aAmperage,
                    aVoltage,
                    true,
                    false,
                    aRGB).getStackForm(1L));
            GTOreDictUnificator.registerOre(
                OrePrefixes.cableGt12,
                aMaterial,
                new GTPPMTECable(
                    aStartID + 10,
                    "cable." + aMaterial.mName.toLowerCase() + ".12",
                    "12x " + aMaterial.mDefaultLocalName + " Cable",
                    0.75F,
                    aMaterial,
                    aLossInsulated,
                    12L * aAmperage,
                    aVoltage,
                    true,
                    false,
                    aRGB).getStackForm(1L));
            GTOreDictUnificator.registerOre(
                OrePrefixes.cableGt16,
                aMaterial,
                new GTPPMTECable(
                    aStartID + 11,
                    "cable." + aMaterial.mName.toLowerCase() + ".16",
                    "16x " + aMaterial.mDefaultLocalName + " Cable",
                    0.875f,
                    aMaterial,
                    aLossInsulated,
                    16L * aAmperage,
                    aVoltage,
                    true,
                    false,
                    aRGB).getStackForm(1L));
        }
    }

    private static void customWireFactory(final Material Material, final int Voltage, final int ID,
        final long insulatedLoss, final long uninsulatedLoss, final long Amps) {
        int V = GTUtility.getTier(Voltage);
        if (V == -1) {
            Logger.ERROR("Failed to set voltage on " + Material + ". Invalid voltage of " + Voltage + "V set.");
            Logger.ERROR(Material + " has defaulted to 8v.");
            V = 0;
        }
        makeCustomWires(Material, ID, insulatedLoss, uninsulatedLoss, Amps, GTValues.V[V], true, false);
    }

    private static void makeCustomWires(final Material aMaterial, final int aStartID, final long aLossInsulated,
        final long aLoss, final long aAmperage, final long aVoltage, final boolean aInsulatable,
        final boolean aAutoInsulated) {
        Logger.WARNING(
            "Gregtech5u Content | Registered " + aMaterial.getLocalizedName() + " as a new material for Wire & Cable.");
        registerOre(
            OrePrefixes.wireGt01,
            aMaterial,
            new GTPPMTECable(
                aStartID + 0,
                "wire." + aMaterial.getLocalizedName()
                    .toLowerCase() + ".01",
                "1x " + aMaterial.getLocalizedName() + " Wire",
                0.125F,
                aLoss,
                1L * aAmperage,
                aVoltage,
                false,
                !aAutoInsulated,
                aMaterial.getRGBA()).getStackForm(1L));
        registerOre(
            OrePrefixes.wireGt02,
            aMaterial,
            new GTPPMTECable(
                aStartID + 1,
                "wire." + aMaterial.getLocalizedName()
                    .toLowerCase() + ".02",
                "2x " + aMaterial.getLocalizedName() + " Wire",
                0.25F,
                aLoss,
                2L * aAmperage,
                aVoltage,
                false,
                !aAutoInsulated,
                aMaterial.getRGBA()).getStackForm(1L));
        registerOre(
            OrePrefixes.wireGt04,
            aMaterial,
            new GTPPMTECable(
                aStartID + 2,
                "wire." + aMaterial.getLocalizedName()
                    .toLowerCase() + ".04",
                "4x " + aMaterial.getLocalizedName() + " Wire",
                0.375F,
                aLoss,
                4L * aAmperage,
                aVoltage,
                false,
                !aAutoInsulated,
                aMaterial.getRGBA()).getStackForm(1L));
        registerOre(
            OrePrefixes.wireGt08,
            aMaterial,
            new GTPPMTECable(
                aStartID + 3,
                "wire." + aMaterial.getLocalizedName()
                    .toLowerCase() + ".08",
                "8x " + aMaterial.getLocalizedName() + " Wire",
                0.50F,
                aLoss,
                8L * aAmperage,
                aVoltage,
                false,
                !aAutoInsulated,
                aMaterial.getRGBA()).getStackForm(1L));
        registerOre(
            OrePrefixes.wireGt12,
            aMaterial,
            new GTPPMTECable(
                aStartID + 4,
                "wire." + aMaterial.getLocalizedName()
                    .toLowerCase() + ".12",
                "12x " + aMaterial.getLocalizedName() + " Wire",
                0.625F,
                aLoss,
                12L * aAmperage,
                aVoltage,
                false,
                !aAutoInsulated,
                aMaterial.getRGBA()).getStackForm(1L));
        registerOre(
            OrePrefixes.wireGt16,
            aMaterial,
            new GTPPMTECable(
                aStartID + 5,
                "wire." + aMaterial.getLocalizedName()
                    .toLowerCase() + ".16",
                "16x " + aMaterial.getLocalizedName() + " Wire",
                0.75F,
                aLoss,
                16L * aAmperage,
                aVoltage,
                false,
                !aAutoInsulated,
                aMaterial.getRGBA()).getStackForm(1L));
        if (aInsulatable) {
            registerOre(
                OrePrefixes.cableGt01,
                aMaterial,
                new GTPPMTECable(
                    aStartID + 6,
                    "cable." + aMaterial.getLocalizedName()
                        .toLowerCase() + ".01",
                    "1x " + aMaterial.getLocalizedName() + " Cable",
                    0.25F,
                    aLossInsulated,
                    1L * aAmperage,
                    aVoltage,
                    true,
                    false,
                    aMaterial.getRGBA()).getStackForm(1L));
            registerOre(
                OrePrefixes.cableGt02,
                aMaterial,
                new GTPPMTECable(
                    aStartID + 7,
                    "cable." + aMaterial.getLocalizedName()
                        .toLowerCase() + ".02",
                    "2x " + aMaterial.getLocalizedName() + " Cable",
                    0.375F,
                    aLossInsulated,
                    2L * aAmperage,
                    aVoltage,
                    true,
                    false,
                    aMaterial.getRGBA()).getStackForm(1L));
            registerOre(
                OrePrefixes.cableGt04,
                aMaterial,
                new GTPPMTECable(
                    aStartID + 8,
                    "cable." + aMaterial.getLocalizedName()
                        .toLowerCase() + ".04",
                    "4x " + aMaterial.getLocalizedName() + " Cable",
                    0.5F,
                    aLossInsulated,
                    4L * aAmperage,
                    aVoltage,
                    true,
                    false,
                    aMaterial.getRGBA()).getStackForm(1L));
            registerOre(
                OrePrefixes.cableGt08,
                aMaterial,
                new GTPPMTECable(
                    aStartID + 9,
                    "cable." + aMaterial.getLocalizedName()
                        .toLowerCase() + ".08",
                    "8x " + aMaterial.getLocalizedName() + " Cable",
                    0.625F,
                    aLossInsulated,
                    8L * aAmperage,
                    aVoltage,
                    true,
                    false,
                    aMaterial.getRGBA()).getStackForm(1L));
            registerOre(
                OrePrefixes.cableGt12,
                aMaterial,
                new GTPPMTECable(
                    aStartID + 10,
                    "cable." + aMaterial.getLocalizedName()
                        .toLowerCase() + ".12",
                    "12x " + aMaterial.getLocalizedName() + " Cable",
                    0.75F,
                    aLossInsulated,
                    12L * aAmperage,
                    aVoltage,
                    true,
                    false,
                    aMaterial.getRGBA()).getStackForm(1L));
            registerOre(
                OrePrefixes.cableGt16,
                aMaterial,
                new GTPPMTECable(
                    aStartID + 11,
                    "cable." + aMaterial.getLocalizedName()
                        .toLowerCase() + ".16",
                    "16x " + aMaterial.getLocalizedName() + " Cable",
                    0.875f,
                    aLossInsulated,
                    16L * aAmperage,
                    aVoltage,
                    true,
                    false,
                    aMaterial.getRGBA()).getStackForm(1L));
        }
    }

    private static void generateGTFluidPipes(final Materials material, final int startID, final int transferRatePerSec,
        final int heatResistance, final boolean isGasProof) {
        final int transferRatePerTick = transferRatePerSec / 20;
        final long mass = material.getMass();
        final long voltage = material.mMeltingPoint >= 2800 ? 64 : 16;
        GTOreDictUnificator.registerOre(
            OrePrefixes.pipeTiny.get(material),
            new MTEFluid(
                startID,
                "GT_Pipe_" + material.mDefaultLocalName + "_Tiny",
                "Tiny " + material.mDefaultLocalName + " Fluid Pipe",
                0.25F,
                material,
                transferRatePerTick * 2,
                heatResistance,
                isGasProof).getStackForm(1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.pipeSmall.get(material),
            new MTEFluid(
                startID + 1,
                "GT_Pipe_" + material.mDefaultLocalName + "_Small",
                "Small " + material.mDefaultLocalName + " Fluid Pipe",
                0.375F,
                material,
                transferRatePerTick * 4,
                heatResistance,
                isGasProof).getStackForm(1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.pipeMedium.get(material),
            new MTEFluid(
                startID + 2,
                "GT_Pipe_" + material.mDefaultLocalName,
                material.mDefaultLocalName + " Fluid Pipe",
                0.5F,
                material,
                transferRatePerTick * 12,
                heatResistance,
                isGasProof).getStackForm(1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.pipeLarge.get(material),
            new MTEFluid(
                startID + 3,
                "GT_Pipe_" + material.mDefaultLocalName + "_Large",
                "Large " + material.mDefaultLocalName + " Fluid Pipe",
                0.75F,
                material,
                transferRatePerTick * 24,
                heatResistance,
                isGasProof).getStackForm(1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.pipeHuge.get(material),
            new MTEFluid(
                startID + 4,
                "GT_Pipe_" + material.mDefaultLocalName + "_Huge",
                "Huge " + material.mDefaultLocalName + " Fluid Pipe",
                0.875F,
                material,
                transferRatePerTick * 48,
                heatResistance,
                isGasProof).getStackForm(1L));
    }

    private static void generateNonGTFluidPipes(final GT_Materials material, final Material GGMaterial,
        final int startID, final int transferRatePerSec, final int heatResistance, final boolean isGasProof) {
        final int transferRatePerTick = transferRatePerSec / 20;
        GTOreDictUnificator.registerOre(
            OrePrefixes.pipeTiny.get(material),
            new GTPPMTEFluid(
                startID,
                "GT_Pipe_" + material.mDefaultLocalName + "_Tiny",
                "Tiny " + material.mDefaultLocalName + " Fluid Pipe",
                0.25F,
                material,
                transferRatePerTick * 2,
                heatResistance,
                isGasProof).getStackForm(1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.pipeSmall.get(material),
            new GTPPMTEFluid(
                startID + 1,
                "GT_Pipe_" + material.mDefaultLocalName + "_Small",
                "Small " + material.mDefaultLocalName + " Fluid Pipe",
                0.375F,
                material,
                transferRatePerTick * 4,
                heatResistance,
                isGasProof).getStackForm(1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.pipeMedium.get(material),
            new GTPPMTEFluid(
                startID + 2,
                "GT_Pipe_" + material.mDefaultLocalName,
                material.mDefaultLocalName + " Fluid Pipe",
                0.5F,
                material,
                transferRatePerTick * 12,
                heatResistance,
                isGasProof).getStackForm(1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.pipeLarge.get(material),
            new GTPPMTEFluid(
                startID + 3,
                "GT_Pipe_" + material.mDefaultLocalName + "_Large",
                "Large " + material.mDefaultLocalName + " Fluid Pipe",
                0.75F,
                material,
                transferRatePerTick * 24,
                heatResistance,
                isGasProof).getStackForm(1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.pipeHuge.get(material),
            new GTPPMTEFluid(
                startID + 4,
                "GT_Pipe_" + material.mDefaultLocalName + "_Huge",
                "Huge " + material.mDefaultLocalName + " Fluid Pipe",
                0.875F,
                material,
                transferRatePerTick * 48,
                heatResistance,
                isGasProof).getStackForm(1L));

    }

    public static void generatePipeRecipes(final String materialName, final long Mass, final long vMulti) {

        String output = materialName.substring(0, 1)
            .toUpperCase() + materialName.substring(1);
        output = Utils.sanitizeString(output);

        if (output.equals("VoidMetal")) {
            output = "Void";
        }

        Logger.INFO("Generating " + output + " pipes & respective recipes.");

        ItemStack pipeIngot = ItemUtils.getItemStackOfAmountFromOreDict("ingot" + output, 1);
        ItemStack pipePlate = ItemUtils.getItemStackOfAmountFromOreDict("plate" + output, 1);

        if (pipeIngot == null) {
            if (pipePlate != null) {
                pipeIngot = pipePlate;
            }
        }

        // Check all pipes are not null
        Logger.WARNING(
            "Generated pipeTiny from " + materialName
                + "? "
                + (ItemUtils.getItemStackOfAmountFromOreDict("pipe" + "Tiny" + output, 1) != null));
        Logger.WARNING(
            "Generated pipeSmall from " + materialName
                + "? "
                + (ItemUtils.getItemStackOfAmountFromOreDict("pipe" + "Small" + output, 1) != null));
        Logger.WARNING(
            "Generated pipeNormal from " + materialName
                + "? "
                + (ItemUtils.getItemStackOfAmountFromOreDict("pipe" + "Medium" + output, 1) != null));
        Logger.WARNING(
            "Generated pipeLarge from " + materialName
                + "? "
                + (ItemUtils.getItemStackOfAmountFromOreDict("pipe" + "Large" + output, 1) != null));
        Logger.WARNING(
            "Generated pipeHuge from " + materialName
                + "? "
                + (ItemUtils.getItemStackOfAmountFromOreDict("pipe" + "Huge" + output, 1) != null));

        int eut = (int) (8 * vMulti);

        // Add the Three Shaped Recipes First
        RecipeUtils.addShapedRecipe(
            pipePlate,
            "craftingToolWrench",
            pipePlate,
            pipePlate,
            null,
            pipePlate,
            pipePlate,
            "craftingToolHardHammer",
            pipePlate,
            ItemUtils.getItemStackOfAmountFromOreDict("pipe" + "Small" + output, 6));

        RecipeUtils.addShapedRecipe(
            pipePlate,
            pipePlate,
            pipePlate,
            "craftingToolWrench",
            null,
            "craftingToolHardHammer",
            pipePlate,
            pipePlate,
            pipePlate,
            ItemUtils.getItemStackOfAmountFromOreDict("pipe" + "Medium" + output, 2));

        RecipeUtils.addShapedRecipe(
            pipePlate,
            "craftingToolHardHammer",
            pipePlate,
            pipePlate,
            null,
            pipePlate,
            pipePlate,
            "craftingToolWrench",
            pipePlate,
            ItemUtils.getItemStackOfAmountFromOreDict("pipe" + "Large" + output, 1));

        if (pipeIngot != null && ItemUtils.checkForInvalidItems(pipeIngot)) {
            // 1 Clay Plate = 1 Clay Dust = 2 Clay Ball
            int inputMultiplier = materialName.equals("Clay") ? 2 : 1;
            GTValues.RA.stdBuilder()
                .itemInputs(
                    ItemUtils.getSimpleStack(pipeIngot, 1 * inputMultiplier),
                    ItemList.Shape_Extruder_Pipe_Tiny.get(0))
                .itemOutputs(ItemUtils.getItemStackOfAmountFromOreDictNoBroken("pipe" + "Tiny" + output, 2))
                .duration(5 * TICKS)
                .eut(eut)
                .addTo(extruderRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(
                    ItemUtils.getSimpleStack(pipeIngot, 1 * inputMultiplier),
                    ItemList.Shape_Extruder_Pipe_Small.get(0))
                .itemOutputs(ItemUtils.getItemStackOfAmountFromOreDict("pipe" + "Small" + output, 1))
                .duration(10 * TICKS)
                .eut(eut)
                .addTo(extruderRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(
                    ItemUtils.getSimpleStack(pipeIngot, 3 * inputMultiplier),
                    ItemList.Shape_Extruder_Pipe_Medium.get(0))
                .itemOutputs(ItemUtils.getItemStackOfAmountFromOreDict("pipe" + "Medium" + output, 1))
                .duration(20 * TICKS)
                .eut(eut)
                .addTo(extruderRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(
                    ItemUtils.getSimpleStack(pipeIngot, 6 * inputMultiplier),
                    ItemList.Shape_Extruder_Pipe_Large.get(0))
                .itemOutputs(ItemUtils.getItemStackOfAmountFromOreDict("pipe" + "Large" + output, 1))
                .duration(2 * SECONDS)
                .eut(eut)
                .addTo(extruderRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(
                    ItemUtils.getSimpleStack(pipeIngot, 12 * inputMultiplier),
                    ItemList.Shape_Extruder_Pipe_Huge.get(0))
                .itemOutputs(ItemUtils.getItemStackOfAmountFromOreDict("pipe" + "Huge" + output, 1))
                .duration(4 * SECONDS)
                .eut(eut)
                .addTo(extruderRecipes);

        }

        if ((eut < 512) && !output.equals("Void")) {
            ItemStack pipePlateDouble = ItemUtils.getItemStackOfAmountFromOreDict("plateDouble" + output, 1);
            if (pipePlateDouble != null) {
                pipePlateDouble = pipePlateDouble.copy();
                RecipeUtils.addShapedRecipe(
                    pipePlateDouble,
                    "craftingToolHardHammer",
                    pipePlateDouble,
                    pipePlateDouble,
                    null,
                    pipePlateDouble,
                    pipePlateDouble,
                    "craftingToolWrench",
                    pipePlateDouble,
                    ItemUtils.getItemStackOfAmountFromOreDict("pipe" + "Huge" + output, 1));
            } else {
                Logger.INFO(
                    "Failed to add a recipe for " + materialName + " Huge pipes. Double plates probably do not exist.");
            }
        }
    }

    public static boolean registerOre(OrePrefixes aPrefix, Material aMaterial, ItemStack aStack) {
        return registerOre(aPrefix.get(Utils.sanitizeString(aMaterial.getLocalizedName())), aStack);
    }

    public static boolean registerOre(Object aName, ItemStack aStack) {
        if ((aName == null) || (GTUtility.isStackInvalid(aStack))) return false;
        String tName = aName.toString();
        if (GTUtility.isStringInvalid(tName)) return false;
        ArrayList<ItemStack> tList = GTOreDictUnificator.getOres(tName);
        for (ItemStack itemStack : tList) if (GTUtility.areStacksEqual(itemStack, aStack, true)) return false;
        OreDictionary.registerOre(tName, GTUtility.copyAmount(1L, new Object[] { aStack }));
        return true;
    }

    public static boolean generateWireRecipes(Material aMaterial) {

        ItemStack aPlate = aMaterial.getPlate(1);
        ItemStack aIngot = aMaterial.getIngot(1);
        ItemStack aRod = aMaterial.getRod(1);
        ItemStack aWire01 = aMaterial.getWire01(1);
        ItemStack aWire02 = aMaterial.getWire02(1);
        ItemStack aWire04 = aMaterial.getWire04(1);
        ItemStack aWire08 = aMaterial.getWire08(1);
        ItemStack aWire12 = aMaterial.getWire12(1);
        ItemStack aWire16 = aMaterial.getWire16(1);
        ItemStack aCable01 = aMaterial.getCable01(1);
        ItemStack aCable02 = aMaterial.getCable02(1);
        ItemStack aCable04 = aMaterial.getCable04(1);
        ItemStack aCable08 = aMaterial.getCable08(1);
        ItemStack aCable12 = aMaterial.getCable12(1);
        ItemStack aCable16 = aMaterial.getCable16(1);
        ItemStack aFineWire = aMaterial.getFineWire(1);

        // Adds manual crafting recipe
        if (ItemUtils.checkForInvalidItems(new ItemStack[] { aPlate, aWire01 })) {
            RecipeUtils
                .addShapedRecipe(aPlate, CI.craftingToolWireCutter, null, null, null, null, null, null, null, aWire01);
        }

        // Wire mill
        if (ItemUtils
            .checkForInvalidItems(new ItemStack[] { aIngot, aWire01, aWire02, aWire04, aWire08, aWire12, aWire16 })) {
            GTValues.RA.stdBuilder()
                .itemInputs(aMaterial.getIngot(1), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(aMaterial.getWire01(2))
                .duration(5 * SECONDS)
                .eut(4)
                .addTo(wiremillRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(aMaterial.getIngot(1), GTUtility.getIntegratedCircuit(2))
                .itemOutputs(aMaterial.getWire02(1))
                .duration(7 * SECONDS + 10 * TICKS)
                .eut(4)
                .addTo(wiremillRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(aMaterial.getIngot(2), GTUtility.getIntegratedCircuit(4))
                .itemOutputs(aMaterial.getWire04(1))
                .duration(10 * SECONDS)
                .eut(4)
                .addTo(wiremillRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(aMaterial.getIngot(4), GTUtility.getIntegratedCircuit(8))
                .itemOutputs(aMaterial.getWire08(1))
                .duration(12 * SECONDS + 10 * TICKS)
                .eut(4)
                .addTo(wiremillRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(aMaterial.getIngot(6), GTUtility.getIntegratedCircuit(12))
                .itemOutputs(aMaterial.getWire12(1))
                .duration(15 * SECONDS)
                .eut(4)
                .addTo(wiremillRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(aMaterial.getIngot(8), GTUtility.getIntegratedCircuit(16))
                .itemOutputs(aMaterial.getWire16(1))
                .duration(17 * SECONDS + 10 * TICKS)
                .eut(4)
                .addTo(wiremillRecipes);

        }

        if (ItemUtils
            .checkForInvalidItems(new ItemStack[] { aRod, aWire01, aWire02, aWire04, aWire08, aWire12, aWire16 })) {
            GTValues.RA.stdBuilder()
                .itemInputs(aMaterial.getRod(1), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(aMaterial.getWire01(1))
                .duration(2 * SECONDS + 10 * TICKS)
                .eut(4)
                .addTo(wiremillRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(aMaterial.getRod(2), GTUtility.getIntegratedCircuit(2))
                .itemOutputs(aMaterial.getWire02(1))
                .duration(5 * SECONDS)
                .eut(4)
                .addTo(wiremillRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(aMaterial.getRod(4), GTUtility.getIntegratedCircuit(4))
                .itemOutputs(aMaterial.getWire04(1))
                .duration(7 * SECONDS + 10 * TICKS)
                .eut(4)
                .addTo(wiremillRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(aMaterial.getRod(8), GTUtility.getIntegratedCircuit(8))
                .itemOutputs(aMaterial.getWire08(1))
                .duration(10 * SECONDS)
                .eut(4)
                .addTo(wiremillRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(aMaterial.getRod(12), GTUtility.getIntegratedCircuit(12))
                .itemOutputs(aMaterial.getWire12(1))
                .duration(12 * SECONDS + 10 * TICKS)
                .eut(4)
                .addTo(wiremillRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(aMaterial.getRod(16), GTUtility.getIntegratedCircuit(16))
                .itemOutputs(aMaterial.getWire16(1))
                .duration(15 * SECONDS)
                .eut(4)
                .addTo(wiremillRecipes);

        }

        if (ItemUtils.checkForInvalidItems(new ItemStack[] { aIngot, aFineWire })) {
            GTValues.RA.stdBuilder()
                .itemInputs(aMaterial.getIngot(1), GTUtility.getIntegratedCircuit(3))
                .itemOutputs(aMaterial.getFineWire(8))
                .duration(5 * SECONDS)
                .eut(4)
                .addTo(wiremillRecipes);

        }

        if (ItemUtils.checkForInvalidItems(new ItemStack[] { aRod, aFineWire })) {
            GTValues.RA.stdBuilder()
                .itemInputs(aMaterial.getRod(1), GTUtility.getIntegratedCircuit(3))
                .itemOutputs(aMaterial.getFineWire(4))
                .duration(2 * SECONDS + 10 * TICKS)
                .eut(4)
                .addTo(wiremillRecipes);

        }

        if (ItemUtils.checkForInvalidItems(new ItemStack[] { aWire01, aFineWire })) {
            GTValues.RA.stdBuilder()
                .itemInputs(aMaterial.getWire01(1), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(aMaterial.getFineWire(4))
                .duration(10 * SECONDS)
                .eut(8)
                .addTo(wiremillRecipes);

        }

        // Extruder
        if (ItemUtils.checkForInvalidItems(new ItemStack[] { aIngot, aWire01 })) {
            GTValues.RA.stdBuilder()
                .itemInputs(aIngot, ItemList.Shape_Extruder_Wire.get(0))
                .itemOutputs(aMaterial.getWire01(2))
                .duration(9 * SECONDS + 16 * TICKS)
                .eut(96)
                .addTo(extruderRecipes);
        }

        if (ItemUtils.checkForInvalidItems(new ItemStack[] { aCable01, aWire01 })) {
            GTValues.RA.stdBuilder()
                .itemInputs(aCable01)
                .itemOutputs(aWire01)
                .duration(5 * SECONDS)
                .eut(8)
                .addTo(unpackagerRecipes);
        }

        // Shapeless Down-Crafting
        // 2x
        if (ItemUtils.checkForInvalidItems(new ItemStack[] { aWire01, aWire02 })) {
            RecipeUtils.addShapelessGregtechRecipe(new ItemStack[] { aWire02 }, aMaterial.getWire01(2));
        }

        // 4x
        if (ItemUtils.checkForInvalidItems(new ItemStack[] { aWire01, aWire04 })) {
            RecipeUtils.addShapelessGregtechRecipe(new ItemStack[] { aWire04 }, aMaterial.getWire01(4));
        }

        // 8x
        if (ItemUtils.checkForInvalidItems(new ItemStack[] { aWire01, aWire08 })) {
            RecipeUtils.addShapelessGregtechRecipe(new ItemStack[] { aWire08 }, aMaterial.getWire01(8));
        }

        // 12x
        if (ItemUtils.checkForInvalidItems(new ItemStack[] { aWire01, aWire12 })) {
            RecipeUtils.addShapelessGregtechRecipe(new ItemStack[] { aWire12 }, aMaterial.getWire01(12));
        }

        // 16x
        if (ItemUtils.checkForInvalidItems(new ItemStack[] { aWire01, aWire16 })) {
            RecipeUtils.addShapelessGregtechRecipe(new ItemStack[] { aWire16 }, aMaterial.getWire01(16));
        }

        // 1x -> 2x
        if (ItemUtils.checkForInvalidItems(new ItemStack[] { aWire01, aWire02 })) {
            RecipeUtils.addShapelessGregtechRecipe(new ItemStack[] { aWire01, aWire01 }, aWire02);
        }

        // 2x -> 4x
        if (ItemUtils.checkForInvalidItems(new ItemStack[] { aWire02, aWire04 })) {
            RecipeUtils.addShapelessGregtechRecipe(new ItemStack[] { aWire02, aWire02 }, aWire04);
        }

        // 4x -> 8x
        if (ItemUtils.checkForInvalidItems(new ItemStack[] { aWire04, aWire08 })) {
            RecipeUtils.addShapelessGregtechRecipe(new ItemStack[] { aWire04, aWire04 }, aWire08);
        }

        // 8x -> 12x
        if (ItemUtils.checkForInvalidItems(new ItemStack[] { aWire04, aWire08, aWire12 })) {
            RecipeUtils.addShapelessGregtechRecipe(new ItemStack[] { aWire04, aWire08 }, aWire12);
        }

        // 12x -> 16x
        if (ItemUtils.checkForInvalidItems(new ItemStack[] { aWire04, aWire12, aWire16 })) {
            RecipeUtils.addShapelessGregtechRecipe(new ItemStack[] { aWire04, aWire12 }, aWire16);
        }

        // 8x -> 16x
        if (ItemUtils.checkForInvalidItems(new ItemStack[] { aWire08, aWire16 })) {
            RecipeUtils.addShapelessGregtechRecipe(new ItemStack[] { aWire08, aWire08 }, aWire16);
        }

        // 1x -> 4x
        if (ItemUtils.checkForInvalidItems(new ItemStack[] { aWire01, aWire04 })) {
            RecipeUtils.addShapelessGregtechRecipe(new ItemStack[] { aWire01, aWire01, aWire01, aWire01 }, aWire04);
        }

        // 1x -> 8x
        if (ItemUtils.checkForInvalidItems(new ItemStack[] { aWire01, aWire08 })) {
            RecipeUtils.addShapelessGregtechRecipe(
                new ItemStack[] { aWire01, aWire01, aWire01, aWire01, aWire01, aWire01, aWire01, aWire01 },
                aWire08);
        }

        // Wire to Cable
        // 1x
        if (ItemUtils.checkForInvalidItems(new ItemStack[] { aWire01, aCable01 })) {
            GTValues.RA.stdBuilder()
                .itemInputs(aWire01, GTUtility.getIntegratedCircuit(24))
                .itemOutputs(aCable01)
                .fluidInputs(FluidUtils.getFluidStack("molten.rubber", 144))
                .duration(5 * SECONDS)
                .eut(8)
                .addTo(assemblerRecipes);

        }

        // 2x
        if (ItemUtils.checkForInvalidItems(new ItemStack[] { aWire02, aCable02 })) {
            GTValues.RA.stdBuilder()
                .itemInputs(aWire02, GTUtility.getIntegratedCircuit(24))
                .itemOutputs(aCable02)
                .fluidInputs(FluidUtils.getFluidStack("molten.rubber", 144))
                .duration(5 * SECONDS)
                .eut(8)
                .addTo(assemblerRecipes);

        }

        // 4x
        if (ItemUtils.checkForInvalidItems(new ItemStack[] { aWire04, aCable04 })) {
            GTValues.RA.stdBuilder()
                .itemInputs(aWire04, GTUtility.getIntegratedCircuit(24))
                .itemOutputs(aCable04)
                .fluidInputs(FluidUtils.getFluidStack("molten.rubber", 288))
                .duration(5 * SECONDS)
                .eut(8)
                .addTo(assemblerRecipes);

        }

        // 8x
        if (ItemUtils.checkForInvalidItems(new ItemStack[] { aWire08, aCable08 })) {
            GTValues.RA.stdBuilder()
                .itemInputs(aWire08, GTUtility.getIntegratedCircuit(24))
                .itemOutputs(aCable08)
                .fluidInputs(FluidUtils.getFluidStack("molten.rubber", 432))
                .duration(5 * SECONDS)
                .eut(8)
                .addTo(assemblerRecipes);

        }

        // 12x
        if (ItemUtils.checkForInvalidItems(new ItemStack[] { aWire12, aCable12 })) {
            GTValues.RA.stdBuilder()
                .itemInputs(aWire12, GTUtility.getIntegratedCircuit(24))
                .itemOutputs(aCable12)
                .fluidInputs(FluidUtils.getFluidStack("molten.rubber", 576))
                .duration(5 * SECONDS)
                .eut(8)
                .addTo(assemblerRecipes);

        }

        // 16x
        if (ItemUtils.checkForInvalidItems(new ItemStack[] { aWire16, aCable16 })) {
            GTValues.RA.stdBuilder()
                .itemInputs(aWire16, GTUtility.getIntegratedCircuit(24))
                .itemOutputs(aCable16)
                .fluidInputs(FluidUtils.getFluidStack("molten.rubber", 720))
                .duration(5 * SECONDS)
                .eut(8)
                .addTo(assemblerRecipes);

        }

        // Assemble small wires into bigger wires
        if (ItemUtils.checkForInvalidItems(new ItemStack[] { aWire01, aWire02 })) {
            GTValues.RA.stdBuilder()
                .itemInputs(aMaterial.getWire01(2), GTUtility.getIntegratedCircuit(2))
                .itemOutputs(aWire02)
                .duration(5 * SECONDS)
                .eut(8)
                .addTo(assemblerRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(aMaterial.getWire01(4), GTUtility.getIntegratedCircuit(4))
                .itemOutputs(aWire04)
                .duration(5 * SECONDS)
                .eut(8)
                .addTo(assemblerRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(aMaterial.getWire01(8), GTUtility.getIntegratedCircuit(8))
                .itemOutputs(aWire08)
                .duration(5 * SECONDS)
                .eut(8)
                .addTo(assemblerRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(aMaterial.getWire01(12), GTUtility.getIntegratedCircuit(12))
                .itemOutputs(aWire12)
                .duration(5 * SECONDS)
                .eut(8)
                .addTo(assemblerRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(aMaterial.getWire01(16), GTUtility.getIntegratedCircuit(16))
                .itemOutputs(aWire16)
                .duration(5 * SECONDS)
                .eut(8)
                .addTo(assemblerRecipes);
        }

        return true;
    }
}