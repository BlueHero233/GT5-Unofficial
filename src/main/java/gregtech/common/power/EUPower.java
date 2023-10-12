package gregtech.common.power;

import static gregtech.api.util.GT_Utility.trans;

import gregtech.api.recipe.RecipeMapFrontend;
import gregtech.api.util.GT_Utility;
import gregtech.nei.NEIRecipeInfo;

public class EUPower extends Power {

    protected final int amperage;
    protected int originalVoltage;

    public EUPower(byte tier, int amperage) {
        super(tier);
        this.amperage = amperage;
    }

    public EUPower(byte tier, int amperage, int specialValue) {
        super(tier, specialValue);
        this.amperage = amperage;
    }

    @Override
    // This generic EU Power class has no overclock defined and does no special calculations.
    public void computePowerUsageAndDuration(int euPerTick, int duration) {
        originalVoltage = computeVoltageForEuRate(euPerTick);
        recipeEuPerTick = euPerTick;
        recipeDuration = duration;
    }

    @Override
    public String getTierString() {
        return GT_Utility.getColoredTierNameFromTier(tier);
    }

    @Override
    protected String getTotalPowerString() {
        return GT_Utility.formatNumbers((long) recipeDuration * recipeEuPerTick) + " EU";
    }

    @Override
    protected void drawNEIDescImpl(NEIRecipeInfo recipeInfo, RecipeMapFrontend frontend) {
        if (shouldShowAmperage()) {
            frontend.drawNEIText(recipeInfo, trans("153", "Usage: ") + getPowerUsageString());
            frontend.drawNEIText(recipeInfo, trans("154", "Voltage: ") + getVoltageString());
            frontend.drawNEIText(recipeInfo, trans("155", "Amperage: ") + getAmperageString());
        } else {
            frontend.drawNEIText(recipeInfo, trans("154", "Voltage: ") + getVoltageString());
        }
    }

    protected boolean shouldShowAmperage() {
        return amperage != 1;
    }

    /**
     * @return EU/t usage, without tier display.
     */
    protected String getPowerUsageString() {
        return GT_Utility.formatNumbers(recipeEuPerTick) + " EU/t";
    }

    /**
     * @return EU/t usage, with tier display.
     */
    protected String getVoltageString() {
        String voltageDescription = GT_Utility.formatNumbers(originalVoltage) + " EU/t";
        voltageDescription += GT_Utility.getTierNameWithParentheses(originalVoltage);
        return voltageDescription;
    }

    protected String getAmperageString() {
        return GT_Utility.formatNumbers(amperage);
    }

    protected int computeVoltageForEuRate(int euPerTick) {
        return amperage != 0 ? euPerTick / amperage : euPerTick;
    }
}
