package model.nutrition;

import model.nutrition.NutritionSummary;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class NutritionSummaryTest {
    private NutritionSummary ns;

    @BeforeEach
    void setUp() {
        ns = new NutritionSummary(100, 10, 5, 5);
    }

    @Test
    void testNutritionSummary() {
        assertEquals(100, ns.getCalories());
        assertEquals(10, ns.getCarbs());
        assertEquals(5, ns.getPotassium());
        assertEquals(5, ns.getSodium());
    }

    @Test
    void testToString() {
        String str = "(Calories: 100 - Carbs: 10 - Potassium: 5 - Sodium: 5)";
    assertEquals(str, ns.toString());
    }
}