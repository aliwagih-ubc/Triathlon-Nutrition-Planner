package model.strategy;

import model.nutrition.NutritionItem;
import model.nutrition.NutritionSummary;
import model.nutrition.RaceNutrition;
import model.race.Race;
import model.triathlete.Triathlete;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class RaceStrategyTest {

    private RaceStrategy rs;

    @BeforeEach
    void setUp() {
        Triathlete athlete = new Triathlete("ali", 28, 90, "Male", 1);
        NutritionItem gel = new NutritionItem("gel", 100, 25, 0, 22);
        NutritionItem gatorade = new NutritionItem("gatorade", 90,22,140,300);
        NutritionItem banana = new NutritionItem("banana", 105, 27, 422, 1);
        RaceNutrition rn = new RaceNutrition(gel, gatorade, banana);
        RaceNutrition maxItems = new RaceNutrition(8, 4, 2);
        Race race = new Race("sprint", "winter", maxItems);
        rs = new RaceStrategy(athlete, race, rn);
    }

    @Test
    void testGetEstimatedFinishTimeThrowsException() {
        assertThrows(FileNotFoundException.class, () ->
                rs.getEstimatedFinishTime("randomFileThatDoesntExist.csv"));
    }

    @Test
    void testGetEstimatedFinishTime() {
        try {
            String filePath = "data/estimatedfinishtimes.csv";
            assertEquals(87.0, rs.getEstimatedFinishTime(filePath));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testCalcRaceRequirement() {
        NutritionSummary ns = rs.calcRaceRequirement();
        assertEquals(1174, ns.getCalories());
        assertEquals(108, ns.getCarbs());
        assertEquals(362, ns.getPotassium());
        assertEquals(1160, ns.getSodium());
    }

    @Test
    void testCalculateOptimumNutritionPlanNull() {
        NutritionSummary req = new NutritionSummary(5000, 300, 300, 300);
        RaceNutrition rn = rs.calculateOptimumNutritionPlan(req);
        assertNull(rn);
    }

    @Test
    void testCalculateOptimumNutritionPlan() {
        NutritionSummary req = rs.calcRaceRequirement();
        RaceNutrition rn = rs.calculateOptimumNutritionPlan(req);
        assertEquals(8, rn.getNumSupplements());
        assertEquals(4, rn.getNumLiquids());
        assertEquals(1, rn.getNumSolids());
    }
}
