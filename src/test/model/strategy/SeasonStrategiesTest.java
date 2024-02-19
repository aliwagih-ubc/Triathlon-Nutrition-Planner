package model.strategy;

import model.nutrition.RaceNutrition;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class SeasonStrategiesTest {

    private SeasonStrategies ss;

    @BeforeEach
    void setUp() {
        String name = "Ali";
        int rating = 4;
        ss = new SeasonStrategies(name, rating);
    }

    @Test
    void testSeasonStrategies() {
        assertEquals(4, ss.getRating());
        ss.setRating(5);
        assertEquals(5, ss.getRating());
    }

    @Test
    void testAppendRaceNutrition() {
        RaceNutrition rn = new RaceNutrition(1, 1, 1);
        ss.appendRaceNutrition(rn);
        assertEquals(1, ss.getNutritionPlans().size());
    }

    @Test
    void testToString() {
        String str = "Season strategy for Ali";
        assertEquals(str, ss.toString());
    }




}
