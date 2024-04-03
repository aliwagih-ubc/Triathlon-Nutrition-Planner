package persistence;

import model.race.Race;
import model.strategy.RaceStrategy;
import model.strategy.SeasonStrategies;
import model.nutrition.RaceNutrition;
import model.nutrition.NutritionItem;

import model.triathlete.Triathlete;
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
            assertEquals("Male", ss.getAthlete().getGender());
            assertEquals(0, ss.getAthlete().getNumRaces());
            assertEquals(0, ss.getAthlete().getWeight());
            assertEquals(0, ss.getAthlete().getAge());
            assertEquals(0, ss.getRating());
            assertEquals(0, ss.getNumOfNutritionPlans());
            assertEquals(0, ss.getNumOfStrategies());
            assertEquals(0, ss.getPreferredNutrition().getNumSolids());
            assertEquals(0, ss.getPreferredNutrition().getNumLiquids());
            assertEquals(0, ss.getPreferredNutrition().getNumSupplements());
            assertEquals("banana", ss.getPreferredNutrition().getSolid().getItemName());
            assertEquals("gel", ss.getPreferredNutrition().getSupplement().getItemName());
            assertEquals("gatorade", ss.getPreferredNutrition().getLiquid().getItemName());
            assertEquals("(Calories: 0 - Carbs: 0 - Potassium: 0 - Sodium: 0)",
                    ss.getPreferredNutrition().getSolidsConsumedNutrition().toString());
            assertEquals("(Calories: 0 - Carbs: 0 - Potassium: 0 - Sodium: 0)",
                    ss.getPreferredNutrition().getSupplementsConsumedNutrition().toString());
            assertEquals("(Calories: 0 - Carbs: 0 - Potassium: 0 - Sodium: 0)",
                    ss.getPreferredNutrition().getLiquidsConsumedNutrition().toString());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testReaderGeneral() {
        JsonReader reader = new JsonReader("./data/testReaderGeneral.json");
        try {
            SeasonStrategies ss = reader.read();
            assertTrue(checkTriathlete("Ali", "Male", 2, 90, 28, ss.getAthlete()));
            assertEquals(5, ss.getRating());
            assertTrue(checkPreferredNutrition(new NutritionItem("gel",0, 0, 0,
                    0), new NutritionItem("gatorade", 0, 0, 0, 0),
                    new NutritionItem("banana", 0, 0, 0, 0), 0,
                    0, 0, ss.getPreferredNutrition()));
            List<RaceStrategy> strategies = ss.getStrategies();
            assertEquals(2, ss.getNumOfStrategies());
            checkRaceStrategies(new Triathlete("Ali", 28, 90, "Male", 2),
                    new Race("sprint", "winter",
                            new RaceNutrition(4, 8, 2)),
                    new RaceNutrition(new NutritionItem("gel",0, 0, 0, 0),
                            new NutritionItem("gatorade", 0, 0, 0, 0),
                            new NutritionItem("banana", 0, 0, 0, 0),
                            0, 0, 0), strategies.get(0));
            checkRaceStrategies(new Triathlete("Ali", 28, 90, "Male", 2),
                    new Race("fullIM", "summer",
                            new RaceNutrition(50, 40, 40)),
                    new RaceNutrition(new NutritionItem("gel",0, 0, 0, 0),
                            new NutritionItem("gatorade", 0, 0, 0, 0),
                            new NutritionItem("banana", 0, 0, 0, 0),
                            0, 0, 0), strategies.get(1));

            List<RaceNutrition> nutritionPlans = ss.getNutritionPlans();
            assertEquals(2, ss.getNumOfNutritionPlans());
            checkRaceNutritionPlan(new NutritionItem("gel", 100, 25, 0, 22),
                    new NutritionItem("gatorade", 90, 22, 140, 300),
                    new NutritionItem("banana", 105, 27, 422, 1),
                    4, 8, 1, nutritionPlans.get(0));
            checkRaceNutritionPlan(new NutritionItem("gel", 100, 25, 0, 22),
                    new NutritionItem("gatorade", 90, 22, 140, 300),
                    new NutritionItem("banana", 105, 27, 422, 1),
                    50, 40, 33, nutritionPlans.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
