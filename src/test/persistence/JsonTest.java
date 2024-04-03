package persistence;

import model.nutrition.NutritionItem;
import model.nutrition.RaceNutrition;
import model.race.Race;
import model.strategy.RaceStrategy;
import model.triathlete.Triathlete;

import static org.junit.jupiter.api.Assertions.*;

// Modified from code in the JsonSerializationDemo project provided for reference.
public class JsonTest {
    protected void checkRaceNutritionPlan(NutritionItem supplement, NutritionItem liquid, NutritionItem solid,
                                          int numSupplements, int numLiquids, int numSolids, RaceNutrition rn) {
        assertTrue(checkNutritionItem(supplement.getItemName(), supplement.getCalories(), supplement.getCarbs(),
                supplement.getPotassium(), supplement.getSodium(), rn.getSupplement()));
        assertTrue(checkNutritionItem(liquid.getItemName(), liquid.getCalories(), liquid.getCarbs(),
                liquid.getPotassium(), liquid.getSodium(), rn.getLiquid()));
        assertTrue(checkNutritionItem(solid.getItemName(), solid.getCalories(), solid.getCarbs(),
                solid.getPotassium(), solid.getSodium(), rn.getSolid()));
        assertEquals(numSupplements, rn.getNumSupplements());
        assertEquals(numLiquids, rn.getNumLiquids());
        assertEquals(numSolids, rn.getNumSolids());
    }

    protected void checkRaceStrategies(Triathlete athlete, Race race, RaceNutrition preferredNutrition,
                                       RaceStrategy rs) {
        assertTrue(checkTriathlete(athlete.getName(), athlete.getGender(), athlete.getNumRaces(), athlete.getWeight(),
                athlete.getAge(), rs.getTriathlete()));
        assertTrue(checkRace(race.getMaxNutrition(), race.getDistance(), race.getSeason(), rs.getRace()));
        assertTrue(checkPreferredNutrition(preferredNutrition.getSupplement(), preferredNutrition.getLiquid(),
                preferredNutrition.getSolid(), preferredNutrition.getNumSupplements(),
                preferredNutrition.getNumLiquids(), preferredNutrition.getNumSolids(), rs.getPreferredNutrition()));
    }

    protected boolean checkNutritionItem(String name, int calories, int carbs, int potassium, int sodium,
                                      NutritionItem ni) {
        return name.equals(ni.getItemName()) && calories == ni.getCalories() && carbs == ni.getCarbs() &&
                potassium == ni.getPotassium() && sodium == ni.getSodium();
    }

    protected boolean checkTriathlete(String name, String gender, int numRaces, int weight, int age,
                                      Triathlete athlete) {
        return name.equals(athlete.getName()) && gender.equals(athlete.getGender()) && numRaces == athlete.getNumRaces()
                && weight == athlete.getWeight() && age == athlete.getAge();
    }

    protected boolean checkRace(RaceNutrition maxNutrition, String distance, String season, Race race) {
        return distance.equals(race.getDistance()) && season.equals(race.getSeason()) &&
                maxNutrition.getNumSupplements() == race.getMaxSupplements() &&
                maxNutrition.getNumSolids() == race.getMaxSolids() &&
                maxNutrition.getNumLiquids() == race.getMaxLiquids();
    }

    protected boolean checkPreferredNutrition(NutritionItem supplement, NutritionItem liquid, NutritionItem solid,
                                          int numSupplements, int numLiquids, int numSolids,
                                           RaceNutrition preferredNutrition) {
        return checkNutritionItem(supplement.getItemName(), supplement.getCalories(), supplement.getCarbs(),
                supplement.getPotassium(), supplement.getSodium(), preferredNutrition.getSupplement()) &&
        checkNutritionItem(liquid.getItemName(), liquid.getCalories(), liquid.getCarbs(),
                liquid.getPotassium(), liquid.getSodium(), preferredNutrition.getLiquid()) &&
        checkNutritionItem(solid.getItemName(), solid.getCalories(), solid.getCarbs(),
                solid.getPotassium(), solid.getSodium(), preferredNutrition.getSolid()) &&
        numSupplements == preferredNutrition.getNumSupplements() &&
        numLiquids == preferredNutrition.getNumLiquids() &&
        numSolids == preferredNutrition.getNumSolids();
    }
}
