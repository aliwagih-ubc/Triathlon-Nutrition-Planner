package ui;

import model.nutrition.NutritionItem;
import model.nutrition.NutritionPlan;
import model.nutrition.NutritionSummary;
import model.race.Race;
import model.strategy.RaceStrategy;
import model.triathlete.Triathlete;

import java.util.ArrayList;
import java.util.List;

public class SeasonStrategiesApp {
    private final List<Character> possibleGenders = new ArrayList<>();

    private final List<String> possibleRaceSeason = new ArrayList<>();

    private final List<NutritionItem> availableSupplements = new ArrayList<>();
    private final List<NutritionItem> availableLiquids = new ArrayList<>();
    private final List<NutritionItem> availableSolids = new ArrayList<>();

    public SeasonStrategiesApp() {
        // Populate possible genders
        possibleGenders.add('m');
        possibleGenders.add('f');

        // Populate possible race seasons
        possibleRaceSeason.add("summer");
        possibleRaceSeason.add("fall");
        possibleRaceSeason.add("winter");
        possibleRaceSeason.add("spring");

        // Populate available nutrition items
        availableSupplements.add(new NutritionItem("gel", 100, 25, 0, 22));
        availableSupplements.add(new NutritionItem("chew", 100, 24, 18, 50));

        availableLiquids.add(new NutritionItem("water", 0,0,0,10));
        availableLiquids.add(new NutritionItem("gatorade", 90,22,140,300));
        availableLiquids.add(new NutritionItem("coke", 200, 55, 20, 40));

        availableSolids.add(new NutritionItem("banana", 105, 27, 422, 1));
        availableSolids.add(new NutritionItem("pretzels", 110, 23, 0, 280));
    }

    public Triathlete queryBiometrics() {
        // TODO: Query with Scanner and validate at each input
        String name = "ali";
        if (name.length() == 0) {
            // WRONG INPUT
        }

        int age = 28;
        if (age < 18 || age > 29) {
            // WRONG INPUT
        }

        int height = 181;
        if (height < 100 || height > 230) {
            // WRONG INPUT
        }

        int weight = 90;
        if (weight < 40 || weight > 150) {
            // WRONG INPUT
        }

        char gender = 'm';
        if (!possibleGenders.contains(gender)) {
            // WRONG INPUT
        }

        int numRaces = 1;
        if (numRaces < 0) {
            // WRONG INPUT
        }
        return new Triathlete(age, weight, gender, numRaces);
    }

    public Race queryRaceInfo() {
        // TODO: Query with Scanner
        String raceDistance = "halfIM";
        int maxSupplements = 0;
        int maxLiquids = 0;
        int maxSolids = 0;
        if (raceDistance.equals("sprint")) {
            maxSupplements = 2;
            maxLiquids = 1;
            maxSolids = 0;
        } else if (raceDistance.equals("olympic")) {
            maxSupplements = 4;
            maxLiquids = 2;
            maxSolids = 1;
        } else if (raceDistance.equals("halfIM")) {
            maxSupplements = 10;
            maxLiquids = 8;
            maxSolids = 4;
        } else if (raceDistance.equals("fullIM")) {
            maxSupplements = 20;
            maxLiquids = 16;
            maxSolids = 10;
        } else {
            // WRONG INPUT
        }

        String raceSeason = "summer";
        if (!possibleRaceSeason.contains(raceSeason)) {
            // WRONG INPUT
        }
        return new Race(raceDistance, raceSeason, maxSupplements, maxLiquids, maxSolids);
    }

    public NutritionItem getPreferredSupplement(String supplement) {
        int supplementIndex;
        if (supplement.equals("gel")) {
            supplementIndex = 0;
        } else if (supplement.equals("chew")) {
            supplementIndex = 1;
        } else {
            return null;
        }
        return availableSupplements.get(supplementIndex);
    }

    public NutritionItem getPreferredLiquid(String liquid) {
        int liquidIndex;
        if (liquid.equals("water")) {
            liquidIndex = 0;
        } else if (liquid.equals("gatorade")) {
            liquidIndex = 1;
        } else if (liquid.equals("coke")) {
            liquidIndex = 2;
        } else {
            return null;
        }
        return availableLiquids.get(liquidIndex);
    }

    public NutritionItem getPreferredSolid(String solid) {
        int solidIndex;
        if (solid.equals("banana")) {
            solidIndex = 0;
        } else if (solid.equals("pretzels")) {
            solidIndex = 1;
        } else {
            return null;
        }
        return availableSolids.get(solidIndex);
    }

    public NutritionPlan queryNutritionInfo() {
        // TODO: Query with Scanner
        String supplement = "gel";
        NutritionItem preferredSupplement = getPreferredSupplement(supplement);
        if (preferredSupplement == null) {
            // WRONG INPUT
        }

        String liquid = "water";
        NutritionItem preferredLiquid = getPreferredLiquid(liquid);
        if (preferredLiquid == null) {
            // WRONG INPUT
        }

        String solid = "banana";
        NutritionItem preferredSolid = getPreferredSolid(solid);
        if (preferredSolid == null) {
            // WRONG INPUT
        }
        return new NutritionPlan(preferredSupplement, preferredLiquid, preferredSolid);
    }

    public void run() {
        // Ask user for personal inputs
        Triathlete athlete = queryBiometrics();

        // Ask user for preferred nutrition items
        NutritionPlan preferredItems = queryNutritionInfo();

        // Store final results in a string to be displayed at the end
        StringBuilder str = new StringBuilder();

        // For each race, query for info about race conditions and create a race strategy
        for (int i = 1; i <= athlete.getNumRaces(); i++) {
            String raceHeader = "Race #" + i + "\n";
            System.out.println(raceHeader);
            RaceStrategy strategy = new RaceStrategy(athlete, queryRaceInfo(), preferredItems);
            NutritionSummary requirement = strategy.calcRaceRequirement();
            NutritionPlan raceNutritionPlan = strategy.calculateOptimumNutritionPlan(requirement);
            str.append(raceHeader);
            str.append(raceNutritionPlan.toString());
        }

        // Print final results to user
        System.out.println(str);
    }
}
