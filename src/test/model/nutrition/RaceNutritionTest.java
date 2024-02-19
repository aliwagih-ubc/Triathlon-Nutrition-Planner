package model.nutrition;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class RaceNutritionTest {
    private RaceNutrition numbers;
    private RaceNutrition items;

    @BeforeEach
    void setUp() {
        numbers = new RaceNutrition(1, 0, 0);
        NutritionItem supplement = new NutritionItem("gel", 10, 20, 10, 1);
        NutritionItem liquid = new NutritionItem("water", 0, 0, 0, 10);
        NutritionItem solid = new NutritionItem("banana", 5, 5, 25, 5);
        items = new RaceNutrition(supplement, liquid, solid);
    }

    @Test
    void testRaceNutritionNumbers() {
        assertEquals(1, numbers.getNumSupplements());
        assertEquals(0, numbers.getNumLiquids());
        assertEquals(0, numbers.getNumSolids());
    }

    @Test
    void testIncrementNumSupplements() {
        numbers.incrementNumSupplements();
        assertEquals(2, numbers.getNumSupplements());
    }

    @Test
    void testIncrementNumLiquids() {
        numbers.incrementNumLiquids();
        assertEquals(1, numbers.getNumLiquids());
    }

    @Test
    void testIncrementNumSolids() {
        numbers.incrementNumSolids();
        assertEquals(1, numbers.getNumSolids());
    }

    @Test
    void testGetSupplementsConsumedNutrition() {
        items.incrementNumSupplements();
        NutritionSummary totals = items.getSupplementsConsumedNutrition();
        assertEquals(10, totals.getCalories());
        assertEquals(20, totals.getCarbs());
        assertEquals(10, totals.getPotassium());
        assertEquals(1, totals.getSodium());
    }

    @Test
    void testGetLiquidsConsumedNutrition() {
        items.incrementNumLiquids();
        NutritionSummary totals = items.getLiquidsConsumedNutrition();
        assertEquals(0, totals.getCalories());
        assertEquals(0, totals.getCarbs());
        assertEquals(0, totals.getPotassium());
        assertEquals(10, totals.getSodium());
    }

    @Test
    void testGetSolidsConsumedNutrition() {
        items.incrementNumSolids();
        NutritionSummary totals = items.getSolidsConsumedNutrition();
        assertEquals(5, totals.getCalories());
        assertEquals(5, totals.getCarbs());
        assertEquals(25, totals.getPotassium());
        assertEquals(5, totals.getSodium());
    }

    @Test
    void testTotalConsumed() {
        items.incrementNumSupplements();
        items.incrementNumLiquids();
        items.incrementNumSolids();
        NutritionSummary totals = items.totalConsumed();
        assertEquals(15, totals.getCalories());
        assertEquals(25, totals.getCarbs());
        assertEquals(35, totals.getPotassium());
        assertEquals(16, totals.getSodium());
    }

    @Test
    void testAreAllNutritionRequirementsMet() {
        items.incrementNumSupplements();
        items.incrementNumLiquids();
        items.incrementNumSolids();

        NutritionSummary requirementsMet= new NutritionSummary(1, 1, 1, 1);
        assertTrue(items.areAllNutritionRequirementsMet(requirementsMet));

        NutritionSummary requirementsNotMet = new NutritionSummary(100, 100, 100, 100);
        assertFalse(items.areAllNutritionRequirementsMet(requirementsNotMet));
    }

    @Test
    void testToString() {
        items.incrementNumSupplements();
        items.incrementNumLiquids();
        items.incrementNumSolids();

        String str = "1 gel (Calories: 10 - Carbs: 20 - Potassium: 10 - Sodium: 1)\n"
                + "1 water (Calories: 0 - Carbs: 0 - Potassium: 0 - Sodium: 10)\n"
                + "1 banana (Calories: 5 - Carbs: 5 - Potassium: 25 - Sodium: 5)";
        assertEquals(str, items.toString());
    }
}