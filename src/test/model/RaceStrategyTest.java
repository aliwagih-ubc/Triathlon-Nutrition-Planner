package model;

import model.nutrition.NutritionSummary;
import model.nutrition.RaceNutrition;
import model.race.Race;
import model.strategy.RaceStrategy;
import model.triathlete.Triathlete;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class RaceStrategyTest {

    private RaceStrategy rs;

    @BeforeEach
    void setUp() {
        Triathlete athlete = new Triathlete("ali", 28, 90, "Male", 1);
        RaceNutrition rn = new RaceNutrition(1, 1, 1);
        Race race = new Race("sprint", "winter", rn);
        rs = new RaceStrategy(athlete, race, rn);
    }

    @Test
    void getEstimatedFinishTime() {
        try {
            assertEquals(87, this.rs.getEstimatedFinishTime());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void calcRaceRequirement() {
        NutritionSummary ns = this.rs.calcRaceRequirement();
        assertEquals(0, ns.getCalories());
        assertEquals(0, ns.getCarbs());
        assertEquals(0, ns.getPotassium());
        assertEquals(0, ns.getSodium());
    }

    @Test
    void calculateOptimumNutritionPlan() {
        NutritionSummary reqs = new NutritionSummary(1, 1, 1, 1);
        RaceNutrition ns = this.rs.calculateOptimumNutritionPlan(reqs);
    }
}
