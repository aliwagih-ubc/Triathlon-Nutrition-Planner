package persistence;

import model.nutrition.NutritionItem;
import model.nutrition.RaceNutrition;

import static org.junit.jupiter.api.Assertions.*;

// Modified from code in the JsonSerializationDemo project provided for reference.
public class JsonTest {
    protected void checkRaceNutritionPlan(NutritionItem supplement, NutritionItem liquid, NutritionItem solid,
                                          int numSupplements, int numLiquids, int numSolids, RaceNutrition rn) {
        assertTrue(checkNutritionItem(supplement.getItemName(), supplement.getCalories(), supplement.getCarbs(),
                supplement.getPotassium(), supplement.getSodium(), rn.getSupplement()));
        assertTrue(checkNutritionItem(liquid.getItemName(), liquid.getCalories(), liquid.getCarbs(),
                liquid.getPotassium(), liquid.getSodium(), rn.getLiquid()));
        assertTrue(checkNutritionItem(solid.getItemName(), solid.getCalories(), solid.getCarbs(),
                solid.getPotassium(), solid.getSodium(), rn.getSolid()));
        assertEquals(numSupplements, rn.getNumSupplements());
        assertEquals(numLiquids, rn.getNumLiquids());
        assertEquals(numSolids, rn.getNumSolids());
    }

    protected boolean checkNutritionItem(String name, int calories, int carbs, int potassium, int sodium,
                                      NutritionItem ni) {
        return name.equals(ni.getItemName()) && calories == ni.getCalories() && carbs == ni.getCarbs() &&
                potassium == ni.getPotassium() && sodium == ni.getSodium();
    }
}
