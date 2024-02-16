package model.strategy;

import model.nutrition.NutritionItem;
import model.nutrition.NutritionPlan;
import model.nutrition.NutritionSummary;
import model.race.Race;
import model.triathlete.Triathlete;

import java.io.File;
import java.util.Scanner;

// Represents a complete race nutrition strategy including lists of the
// items to be consumed.

public class RaceStrategy {
    private final Triathlete triathlete; // the triathlete participating in race
    private final Race race; // race that this strategy is for
    private final NutritionPlan preferredNutrition;

    public RaceStrategy(Triathlete triathlete, Race race, NutritionPlan preferredNutrition) {
        this.triathlete = triathlete;
        this.race = race;
        this.preferredNutrition = preferredNutrition;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: get estimated race finish time in minutes.
    public int getEstimatedFinishTime() throws Exception {
        // read from file.
        String rowIndex = this.triathlete.generateAgeGroupGenderIndex();
        int colIndex = this.race.generateColumnIndex();
        Scanner sc = new Scanner(new File("../../../../data/estimatedfinishtimes.csv"));
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
    // MODIFIES:
    // EFFECTS: calculates race requirements
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
        // TODO: Calculate the following correctly
        int carbs = 100;
        int potassium = 100;
        int sodium = 100;
        return new NutritionSummary(calories, carbs, potassium, sodium);
    }

    public NutritionPlan calculateOptimumNutritionPlan(NutritionSummary reqs) {
        NutritionItem preferredSupplement = preferredNutrition.getSupplement();
        NutritionItem preferredLiquid = preferredNutrition.getLiquid();
        NutritionItem preferredSolid = preferredNutrition.getSolid();
        NutritionPlan recommendedPlan = new NutritionPlan(preferredSupplement, preferredLiquid, preferredSolid);

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
