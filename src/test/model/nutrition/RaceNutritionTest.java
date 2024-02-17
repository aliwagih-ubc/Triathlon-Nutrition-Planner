package model.nutrition;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RaceNutritionTest {
    private RaceNutrition numbers;
    private RaceNutrition items;

    @BeforeEach
    void setUp() {
        this.numbers = new RaceNutrition(0, 0, 0);
        NutritionItem supplement = new NutritionItem("gel", 10, 20, 10, 1);
        NutritionItem liquid = new NutritionItem("water", 0, 0, 0, 10);
        NutritionItem solid = new NutritionItem("banana", 5, 5, 25, 5);
        this.items = new RaceNutrition(supplement, liquid, solid);
    }

    @Test
    void incrementNumSupplements() {
        assertEquals(0, this.numbers.getNumSupplements());
        this.numbers.incrementNumSupplements();
        assertEquals(1, this.numbers.getNumSupplements());
    }

    @Test
    void incrementNumLiquids() {
        assertEquals(0, this.numbers.getNumLiquids());
        this.numbers.incrementNumLiquids();
        assertEquals(1, this.numbers.getNumLiquids());
    }

    @Test
    void incrementNumSolids() {
        assertEquals(0, this.numbers.getNumSolids());
        this.numbers.incrementNumSolids();
        assertEquals(1, this.numbers.getNumSolids());
    }

    @Test
    void getSupplementsConsumedNutrition() {
        this.items.incrementNumSupplements();
        NutritionSummary totals = this.items.getSupplementsConsumedNutrition();
        assertEquals(10, totals.getCalories());
        assertEquals(20, totals.getCarbs());
        assertEquals(10, totals.getPotassium());
        assertEquals(1, totals.getSodium());
    }

    @Test
    void getLiquidsConsumedNutrition() {
        this.items.incrementNumLiquids();
        NutritionSummary totals = this.items.getLiquidsConsumedNutrition();
        assertEquals(0, totals.getCalories());
        assertEquals(0, totals.getCarbs());
        assertEquals(0, totals.getPotassium());
        assertEquals(10, totals.getSodium());
    }

    @Test
    void getSolidsConsumedNutrition() {
        this.items.incrementNumSolids();
        NutritionSummary totals = this.items.getSolidsConsumedNutrition();
        assertEquals(5, totals.getCalories());
        assertEquals(5, totals.getCarbs());
        assertEquals(25, totals.getPotassium());
        assertEquals(5, totals.getSodium());
    }

    @Test
    void totalConsumed() {
        this.items.incrementNumSupplements();
        this.items.incrementNumLiquids();
        this.items.incrementNumSolids();
        NutritionSummary totals = this.items.totalConsumed();
        assertEquals(15, totals.getCalories());
        assertEquals(25, totals.getCarbs());
        assertEquals(35, totals.getPotassium());
        assertEquals(16, totals.getSodium());
    }

    @Test
    void areAllNutritionRequirementsMet() {
        this.items.incrementNumSupplements();
        this.items.incrementNumLiquids();
        this.items.incrementNumSolids();
        NutritionSummary reqs1 = new NutritionSummary(1, 1, 1, 1);
        assertTrue(this.items.areAllNutritionRequirementsMet(reqs1));

        NutritionSummary reqs2 = new NutritionSummary(100, 100, 100, 100);
        assertFalse(this.items.areAllNutritionRequirementsMet(reqs2));
    }
}