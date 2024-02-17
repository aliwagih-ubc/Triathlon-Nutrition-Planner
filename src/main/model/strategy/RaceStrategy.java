package model.strategy;

import model.nutrition.NutritionItem;
import model.nutrition.RaceNutrition;
import model.nutrition.NutritionSummary;
import model.race.Race;
import model.triathlete.Triathlete;

import java.io.File;
import java.util.Scanner;

// Represents a complete race strategy specific to a triathlete participating in
// a race event and with defined preferred nutrition items.

public class RaceStrategy {
    private final Triathlete triathlete; // the triathlete participating in race
    private final Race race; // race that this strategy is for
    private final RaceNutrition preferredNutrition;

    // EFFECTS: constructs a race strategy for a specific triathlete, participating in a specific race, and with
    //          preferred nutrition items.
    public RaceStrategy(Triathlete triathlete, Race race, RaceNutrition preferredNutrition) {
        this.triathlete = triathlete;
        this.race = race;
        this.preferredNutrition = preferredNutrition;
    }

    // REQUIRES: this.triathlete.generateAgeGroupGenderIndex() and this.race.generateColumnIndex() are within range.
    // MODIFIES: this
    // EFFECTS: gets the average estimated race finish time in minutes from the file based on the provided indices.
    // Data is obtained from mymottiv.com
    public int getEstimatedFinishTime() throws Exception {
        // read from file.
        String rowIndex = this.triathlete.generateAgeGroupGenderIndex();
        int colIndex = this.race.generateColumnIndex();
        Scanner sc = new Scanner(new File("./data/estimatedfinishtimes.csv"));
        sc.useDelimiter(",");
        while (sc.hasNext()) {
            String[] rowSplit = sc.next().split(",");
            if (rowSplit[0].equals(rowIndex)) {
                return Integer.parseInt(rowSplit[colIndex]);
            }
        }
        return 0;
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: calculates the required macronutrients to complete a race based on estimated finish times,
    // race conditions, and nutrition guidelines.
    public NutritionSummary calcRaceRequirement() {
        int estimatedFinishTime;
        try {
            estimatedFinishTime = getEstimatedFinishTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        double caloricAbsorptionRate = this.race.calcCaloricAbsorptionRate();
        int calories = (int) (caloricAbsorptionRate * estimatedFinishTime * this.triathlete.getWeight());
        int carbs = 75 * (estimatedFinishTime / 60);
        int potassium = 250 * (estimatedFinishTime / 60);
        int sodium = 1000 * (estimatedFinishTime / 60);
        return new NutritionSummary(calories, carbs, potassium, sodium);
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: calculates the optimum nutrition plan to complete a race based on the user's preferred nutrition items.
    public RaceNutrition calculateOptimumNutritionPlan(NutritionSummary reqs) {
        NutritionItem preferredSupplement = preferredNutrition.getSupplement();
        NutritionItem preferredLiquid = preferredNutrition.getLiquid();
        NutritionItem preferredSolid = preferredNutrition.getSolid();
        RaceNutrition recommendedPlan = new RaceNutrition(preferredSupplement, preferredLiquid, preferredSolid);

        for (int i = 1; i <= this.race.getMaxSupplements(); i++) {
            recommendedPlan.incrementNumSupplements();
            if (recommendedPlan.areAllNutritionRequirementsMet(reqs)) {
                return recommendedPlan;
            }
        }

        for (int i = 1; i <= this.race.getMaxLiquids(); i++) {
            recommendedPlan.incrementNumLiquids();
            if (recommendedPlan.areAllNutritionRequirementsMet(reqs)) {
                return recommendedPlan;
            }
        }

        for (int i = 1; i <= this.race.getMaxSolids(); i++) {
            recommendedPlan.incrementNumSolids();
            if (recommendedPlan.areAllNutritionRequirementsMet(reqs)) {
                return recommendedPlan;
            }
        }

        // Requirements are NOT met, even with max amount of nutrition given
        return null;
    }
}
