package persistence;

import model.strategy.SeasonStrategies;
import model.nutrition.RaceNutrition;
import model.nutrition.NutritionItem;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Modified from code in the JsonSerializationDemo project provided for reference.

public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            SeasonStrategies ss = new SeasonStrategies("Ali", 5);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            SeasonStrategies ss = new SeasonStrategies("Ali", 5);
            JsonWriter writer = new JsonWriter("./data/testWriterEmpty.json");
            writer.open();
            writer.write(ss);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmpty.json");
            ss = reader.read();
            assertEquals("Ali", ss.getAthleteName());
            assertEquals(5, ss.getRating());
            assertEquals(0, ss.getNumOfNutritionPlans());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneral() {
        try {
            SeasonStrategies ss = new SeasonStrategies("Mike", 4);
            NutritionItem supplement = new NutritionItem("chew", 100, 24, 18, 50);
            NutritionItem liquid = new NutritionItem("coke", 200, 55, 20, 40);
            NutritionItem solid = new NutritionItem("pretzels", 110, 23, 0, 280);
            ss.appendRaceNutrition(new RaceNutrition(supplement, liquid, solid, 5, 5, 5));
            ss.appendRaceNutrition(new RaceNutrition(supplement, liquid, solid, 2, 1, 3));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneral.json");
            writer.open();
            writer.write(ss);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneral.json");
            ss = reader.read();
            assertEquals("Mike", ss.getAthleteName());
            List<RaceNutrition> nutritionPlans = ss.getNutritionPlans();
            assertEquals(2, ss.getNumOfNutritionPlans());
            checkRaceNutritionPlan(new NutritionItem("chew", 100, 24, 18, 50),
                    new NutritionItem("coke", 200, 55, 20, 40),
                    new NutritionItem("pretzels", 110, 23, 0, 280),
                    5, 5, 5, nutritionPlans.get(0));
            checkRaceNutritionPlan(new NutritionItem("chew", 100, 24, 18, 50),
                    new NutritionItem("coke", 200, 55, 20, 40),
                    new NutritionItem("pretzels", 110, 23, 0, 280),
                    2, 1, 3, nutritionPlans.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
