package persistence;

import model.strategy.SeasonStrategies;
import model.nutrition.RaceNutrition;
import model.nutrition.NutritionItem;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Modified from code in the JsonSerializationDemo project provided for reference.

public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmpty() {
        JsonReader reader = new JsonReader("./data/testReaderEmpty.json");
        try {
            SeasonStrategies ss = reader.read();
            assertEquals("Ali", ss.getAthleteName());
            assertEquals(0, ss.getRating());
            assertEquals(0, ss.getNumOfNutritionPlans());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testReaderGeneral() {
        JsonReader reader = new JsonReader("./data/testReaderGeneral.json");
        try {
            SeasonStrategies ss = reader.read();
            assertEquals("Ali", ss.getAthleteName());
            List<RaceNutrition> nutritionPlans = ss.getNutritionPlans();
            assertEquals(2, ss.getNumOfNutritionPlans());
            checkRaceNutritionPlan(new NutritionItem("gel", 100, 25, 0, 22),
                    new NutritionItem("gatorade", 90, 22, 140, 300),
                    new NutritionItem("banana", 105, 27, 422, 1),
                    4, 8, 1, nutritionPlans.get(0));
            checkRaceNutritionPlan(new NutritionItem("gel", 100, 25, 0, 22),
                    new NutritionItem("gatorade", 90, 22, 140, 300),
                    new NutritionItem("banana", 105, 27, 422, 1),
                    35, 28, 2, nutritionPlans.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
