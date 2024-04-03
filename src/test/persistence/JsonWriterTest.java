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

public class JsonWriterTest extends JsonTest{


    @Test
    void testWriterInvalidFile() {
        try {
            NutritionItem gel = new NutritionItem("gel", 0, 0, 0, 0);
            NutritionItem gatorade = new NutritionItem("gatorade", 0, 0, 0, 0);
            NutritionItem banana = new NutritionItem("banana", 0, 0, 0, 0);
            Triathlete athlete = new Triathlete("Ali", 0, 0, "Male", 0);
            RaceNutrition preferredNutrition =
                    new RaceNutrition(gel, gatorade, banana, 0, 0, 0);
            int rating = 0;
            SeasonStrategies ss = new SeasonStrategies(athlete, preferredNutrition, rating);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmpty() {
        try {
            NutritionItem gel = new NutritionItem("gel", 0, 0, 0, 0);
            NutritionItem gatorade = new NutritionItem("gatorade", 0, 0, 0, 0);
            NutritionItem banana = new NutritionItem("banana", 0, 0, 0, 0);
            Triathlete athlete = new Triathlete("Ali", 0, 0, "Male", 0);
            RaceNutrition preferredNutrition =
                    new RaceNutrition(gel, gatorade, banana, 0, 0, 0);
            int rating = 0;
            JsonWriter writer = new JsonWriter("./data/testWriterEmpty.json");
            writer.open();
            writer.write(new SeasonStrategies(athlete, preferredNutrition, rating));
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmpty.json");
            SeasonStrategies ss = reader.read();
            assertEquals("Ali", ss.getAthleteName());
            assertEquals(0, ss.getRating());
            assertEquals(0, ss.getNumOfNutritionPlans());
            assertEquals(0, ss.getNumOfStrategies());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneral() {
        try {
            Triathlete athlete = new Triathlete("Ali", 28, 90, "Male", 2);
            int rating = 5;
            NutritionItem gel = new NutritionItem("gel", 0, 0, 0, 0);
            NutritionItem gatorade = new NutritionItem("gatorade", 0, 0, 0, 0);
            NutritionItem banana = new NutritionItem("banana", 0, 0, 0, 0);
            RaceNutrition preferredNutrition =
                    new RaceNutrition(gel, gatorade, banana, 0, 0, 0);

            SeasonStrategies ss = new SeasonStrategies(athlete, preferredNutrition, rating);

            RaceNutrition maxNutrition1 = new RaceNutrition(4 ,8, 2);
            RaceNutrition maxNutrition2 = new RaceNutrition(50 ,40, 40);
            Race race1 = new Race("sprint", "winter", maxNutrition1);
            Race race2 = new Race("fullIM", "summer", maxNutrition2);
            ss.appendRaceStrategy(new RaceStrategy(athlete, race1, preferredNutrition));
            ss.appendRaceStrategy(new RaceStrategy(athlete, race2, preferredNutrition));
            ss.appendRaceStrategy(null);

            NutritionItem supplement = new NutritionItem("gel", 100, 25, 0, 22);
            NutritionItem liquid = new NutritionItem("gatorade", 90, 22, 140, 300);
            NutritionItem solid = new NutritionItem("banana", 105, 27, 422, 1);
            ss.appendRaceNutrition(new RaceNutrition(supplement, liquid, solid, 4, 8, 1));
            ss.appendRaceNutrition(new RaceNutrition(supplement, liquid, solid, 50, 40, 33));
            ss.appendRaceNutrition(null);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneral.json");
            writer.open();
            writer.write(ss);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneral.json");
            ss = reader.read();
            assertTrue(checkTriathlete("Ali", "Male", 2, 90, 28, ss.getAthlete()));
            assertEquals(5, ss.getRating());
            assertTrue(checkPreferredNutrition(new NutritionItem("gel",0, 0, 0,
                            0), new NutritionItem("gatorade", 0, 0, 0, 0),
                    new NutritionItem("banana", 0, 0, 0, 0), 0,
                    0, 0, ss.getPreferredNutrition()));
            List<RaceStrategy> strategies = ss.getStrategies();
            assertEquals(3, ss.getNumOfStrategies());
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
            assertNull(strategies.get(2));

            List<RaceNutrition> nutritionPlans = ss.getNutritionPlans();
            assertEquals(3, ss.getNumOfNutritionPlans());
            checkRaceNutritionPlan(new NutritionItem("gel", 100, 25, 0, 22),
                    new NutritionItem("gatorade", 90, 22, 140, 300),
                    new NutritionItem("banana", 105, 27, 422, 1),
                    4, 8, 1, nutritionPlans.get(0));
            checkRaceNutritionPlan(new NutritionItem("gel", 100, 25, 0, 22),
                    new NutritionItem("gatorade", 90, 22, 140, 300),
                    new NutritionItem("banana", 105, 27, 422, 1),
                    50, 40, 33, nutritionPlans.get(1));
            assertNull(nutritionPlans.get(2));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
