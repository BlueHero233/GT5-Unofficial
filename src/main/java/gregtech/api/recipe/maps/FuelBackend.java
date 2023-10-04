package gregtech.api.recipe.maps;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraftforge.fluids.FluidStack;

import gregtech.api.recipe.RecipeMapBackend;
import gregtech.api.recipe.RecipeMapBackendPropertiesBuilder;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.MethodsReturnNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FuelBackend extends RecipeMapBackend {

    private final Map<String, GT_Recipe> recipesByFluidInput = new HashMap<>();

    public FuelBackend(RecipeMapBackendPropertiesBuilder propertiesBuilder) {
        super(propertiesBuilder.disableOptimize());
    }

    @Override
    public GT_Recipe doAdd(GT_Recipe recipe) {
        super.doAdd(recipe);
        if (recipe.mInputs != null && GT_Utility.getNonnullElementCount(recipe.mInputs) == 1
            && (recipe.mFluidInputs == null || GT_Utility.getNonnullElementCount(recipe.mFluidInputs) == 0)) {
            FluidStack tFluid = GT_Utility.getFluidForFilledItem(recipe.mInputs[0], true);
            if (tFluid != null) {
                tFluid.amount = 0;
                recipesByFluidInput.put(tFluid.getUnlocalizedName(), recipe);
            }
        } else if ((recipe.mInputs == null || GT_Utility.getNonnullElementCount(recipe.mInputs) == 0)
            && recipe.mFluidInputs != null
            && GT_Utility.getNonnullElementCount(recipe.mFluidInputs) == 1
            && recipe.mFluidInputs[0] != null) {
                recipesByFluidInput.put(recipe.mFluidInputs[0].getUnlocalizedName(), recipe);
            }
        return recipe;
    }

    @Nullable
    public GT_Recipe findFuel(FluidStack aFluidInput) {
        return recipesByFluidInput.get(aFluidInput.getUnlocalizedName());
    }
}
