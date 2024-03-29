package model.strategy;

import model.nutrition.NutritionItem;
import model.nutrition.RaceNutrition;

import model.triathlete.Triathlete;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class SeasonStrategiesTest {

    private SeasonStrategies ss;
    private Triathlete athlete;
    private RaceNutrition preferredNutrition;
    private NutritionItem gel;
    private NutritionItem gatorade;
    private NutritionItem banana;

    @BeforeEach
    void setUp() {
        gel = new NutritionItem("gel", 100, 25, 0, 22);
        gatorade = new NutritionItem("gatorade", 90, 22, 140, 300);
        banana = new NutritionItem("banana", 105, 27, 422, 1);
        athlete = new Triathlete("Ali", 28, 90, "Male", 2);
        preferredNutrition = new RaceNutrition(gel, gatorade, banana);
        int rating = 4;
        ss = new SeasonStrategies(athlete, preferredNutrition, rating);
    }

    @Test
    void testSeasonStrategies() {
        assertEquals(4, ss.getRating());
        ss.setRating(5);
        assertEquals(5, ss.getRating());
    }

    @Test
    void testAppendRaceNutrition() {
        NutritionItem gel = new NutritionItem("gel", 100, 25, 0, 22);
        NutritionItem gatorade = new NutritionItem("gatorade", 90,22,140,300);
        NutritionItem banana = new NutritionItem("banana", 105, 27, 422, 1);
        RaceNutrition rn = new RaceNutrition(gel, gatorade, banana);
        ss.appendRaceNutrition(rn);
        assertEquals(1, ss.getNutritionPlans().size());
    }

    @Test
    void testToString() {
        NutritionItem gel = new NutritionItem("gel", 100, 25, 0, 22);
        NutritionItem gatorade = new NutritionItem("gatorade", 90,22,140,300);
        NutritionItem banana = new NutritionItem("banana", 105, 27, 422, 1);
        RaceNutrition rn = new RaceNutrition(gel, gatorade, banana);
        ss.appendRaceNutrition(rn);
        String str = "Season strategy for Ali" + "\n" + "Race #1" + "\n"
        + "0 gel (Calories: 0 - Carbs: 0 - Potassium: 0 - Sodium: 0)" + "\n"
        + "0 gatorade (Calories: 0 - Carbs: 0 - Potassium: 0 - Sodium: 0)" + "\n"
        + "0 banana (Calories: 0 - Carbs: 0 - Potassium: 0 - Sodium: 0)";
        assertEquals(str, ss.toString());
    }

    @Test
    void testToStringNull() {
        RaceNutrition rn = null;
        ss.appendRaceNutrition(rn);
        String str = "Season strategy for Ali" + "\n" + "Race #1" + "\n"
                + "No possible plan within the maximum nutrition limit for this race";
        assertEquals(str, ss.toString());
    }




}
