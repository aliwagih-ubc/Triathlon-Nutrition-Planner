package ui;

import model.nutrition.NutritionItem;
import model.nutrition.NutritionSummary;
import model.nutrition.RaceNutrition;
import model.race.Race;
import model.strategy.RaceStrategy;
import model.strategy.SeasonStrategies;
import model.triathlete.Triathlete;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Defines the possible options for the user to pick from and the max allowable nutrition items.
// Instantiates nutrition items. Queries user for inputs and calls methods from main package to return output.
public class SeasonStrategiesApp {
    private final List<String> possibleGenders = new ArrayList<>();

    private final List<String> possibleRaceDistance = new ArrayList<>();
    private final List<String> possibleRaceSeason = new ArrayList<>();

    private final List<String> availableSupplementNames = new ArrayList<>();
    private final List<String> availableLiquidNames = new ArrayList<>();
    private final List<String> availableSolidNames = new ArrayList<>();

    private final List<NutritionItem> availableSupplements = new ArrayList<>();
    private final List<NutritionItem> availableLiquids = new ArrayList<>();
    private final List<NutritionItem> availableSolids = new ArrayList<>();

    // EFFECTS: constructs a SeasonStrategiesApp object
    public SeasonStrategiesApp() {
        // Populate possible genders
        populatePossibleGenders();

        // Populate possible race conditions (distance and season)
        populatePossibleRaceConditions();

        // Populate available nutrition item names
        populateAvailableNutritionItemNames();

        // Populate available nutrition items
        populateAvailableNutritionItems();
    }

    // MODIFIES: this
    // EFFECTS: adds the possible gender options in the app to the list of possible genders.
    public void populatePossibleGenders() {
        possibleGenders.add("Male");
        possibleGenders.add("Female");
    }

    // MODIFIES: this
    // EFFECTS: adds the possible race conditions in the app to the list of possible race conditions.
    public void populatePossibleRaceConditions() {
        // possible distances
        possibleRaceDistance.add("sprint");
        possibleRaceDistance.add("olympic");
        possibleRaceDistance.add("halfIM");
        possibleRaceDistance.add("fullIM");

        // possible seasons
        possibleRaceSeason.add("summer");
        possibleRaceSeason.add("fall");
        possibleRaceSeason.add("winter");
        possibleRaceSeason.add("spring");
    }

    // MODIFIES: this
    // EFFECTS: adds the possible nutrition item names in the app to a list.
    public void populateAvailableNutritionItemNames() {
        availableSupplementNames.add("gel");
        availableSupplementNames.add("chew");

        availableLiquidNames.add("water");
        availableLiquidNames.add("gatorade");
        availableLiquidNames.add("coke");

        availableSolidNames.add("banana");
        availableSolidNames.add("pretzels");
    }

    // MODIFIES: this
    // EFFECTS: adds the possible nutrition items in the app to a list. Instantiates them with their macronutrients.
    public void populateAvailableNutritionItems() {
        availableSupplements.add(new NutritionItem("gel", 100, 25, 0, 22));
        availableSupplements.add(new NutritionItem("chew", 100, 24, 18, 50));

        availableLiquids.add(new NutritionItem("water", 0,0,0,10));
        availableLiquids.add(new NutritionItem("gatorade", 90,22,140,300));
        availableLiquids.add(new NutritionItem("coke", 200, 55, 20, 40));

        availableSolids.add(new NutritionItem("banana", 105, 27, 422, 1));
        availableSolids.add(new NutritionItem("pretzels", 110, 23, 0, 280));
    }

    // EFFECTS: validates a string input. Returns true if valid, false otherwise
    public interface ValidateStringInput {
        boolean validate(String input);
    }

    // EFFECTS: validates an integer input. Returns true if valid, false otherwise
    public interface ValidateIntInput {
        boolean validate(int input);
    }

    // EFFECTS: Queries the user for a string input. Allows the user to continue querying if an unexpected input or
    //    // wrong input type is received.
    public String queryUserForStringWithRetry(Scanner sc, String inputMessage, ValidateStringInput vs) {
        while (true) {
            System.out.println(inputMessage);
            try {
                String input = sc.nextLine();
                if (vs.validate(input)) {
                    return input;
                } else {
                    System.out.println("Received unexpected input, please try again");
                }
            } catch (Exception e) {
                System.out.println("Received wrong input type, please try again");
            }
        }
    }

    // EFFECTS: Queries the user for an int input. Allows the user to continue querying if an unexpected input or
    // wrong input type is received.
    public int queryUserForIntWithRetry(Scanner sc, String inputMessage, ValidateIntInput vi) {
        while (true) {
            System.out.println(inputMessage);
            try {
                int input = sc.nextInt();
                if (vi.validate(input)) {
                    return input;
                } else {
                    System.out.println("Received unexpected input, please try again");
                }
            } catch (Exception e) {
                System.out.println("Received wrong input type, please try again");
            }
        }
    }

    // EFFECTS: queries the user for their biometrics and validates that user is a Triathlete (as defined in class).
    public Triathlete queryBiometrics(Scanner sc) {
        // Check that the user gives a name
        ValidateStringInput v1 = (i) -> i.length() != 0;
        String name = queryUserForStringWithRetry(sc, "What is your name?", v1);

        // Check that user gives an age between [18, 29]
        ValidateIntInput v2 = (i) -> i >= 18 && i <= 29;
        int age = queryUserForIntWithRetry(sc, "What is your age (in years)? "
                + "Please enter a value between 18 and 29.", v2);

        // Check that user gives a weight between [40, 150]
        ValidateIntInput v3 = (i) -> i >= 40 && i <= 150;
        int weight = queryUserForIntWithRetry(sc, "What is your weight (in kg)? "
                + "Please enter a value between 40 and 150.", v3);

        // Check that user gives a possible gender
        ValidateStringInput v4 = (i) -> possibleGenders.contains(i);
        String gender = queryUserForStringWithRetry(sc, "\"Male\" or \"Female\"? "
                + "Please enter gender exactly as indicated.", v4);

        // Check that user gives a positive nonzero number of races
        ValidateIntInput v5 = (i) -> i > 0;
        int numRaces = queryUserForIntWithRetry(sc, "How many races are you participating in? "
                + "Please enter a value greater than zero", v5);
        return new Triathlete(name, age, weight, gender, numRaces);
    }

    // REQUIRES: a raceDistance that is possible
    // EFFECTS: returns a race nutrition plan with the max allowable number of nutrition items
    //          depending on the race distance.
    public RaceNutrition getMaxNutritionInRace(String raceDistance) {
        if (raceDistance.equals("sprint")) {
            return new RaceNutrition(2, 2, 1);
        } else if (raceDistance.equals("olympic")) {
            return new RaceNutrition(4, 3, 2);
        } else if (raceDistance.equals("halfIM")) {
            return new RaceNutrition(12, 8, 4);
        } else {
            return new RaceNutrition(20, 16, 10);
        }
    }

    // EFFECTS: queries the user for the race information and validates that inputs are possible.
    public Race queryRaceInfo(Scanner sc) {
        // Check that user gives a possible race distance
        ValidateStringInput v1 = (i) -> possibleRaceDistance.contains(i);
        String raceDistance = queryUserForStringWithRetry(sc, "What distance is this race? "
                + "Please enter one of: sprint, olympic, halfIM, or fullIM", v1);

        // Get max nutrition amounts based on race distance
        RaceNutrition maxNutrition = getMaxNutritionInRace(raceDistance);

        // Check that user gives a possible race distance
        ValidateStringInput v2 = (i) -> possibleRaceSeason.contains(i);
        String raceSeason = queryUserForStringWithRetry(sc, "What season is this race being held in? "
                + "Please enter one of: summer, fall, winter, or spring", v2);
        return new Race(raceDistance, raceSeason, maxNutrition);
    }

    // EFFECTS: returns the triathlete's preferred supplement
    public NutritionItem getPreferredSupplement(String supplement) {
        int supplementIndex = 0;
        if (supplement.equals("chew")) {
            supplementIndex = 1;
        }
        return availableSupplements.get(supplementIndex);
    }

    // EFFECTS: returns the triathlete's preferred liquid
    public NutritionItem getPreferredLiquid(String liquid) {
        int liquidIndex = 0;
        if (liquid.equals("gatorade")) {
            liquidIndex = 1;
        } else if (liquid.equals("coke")) {
            liquidIndex = 2;
        }
        return availableLiquids.get(liquidIndex);
    }

    // EFFECTS: returns the triathlete's preferred solid item
    public NutritionItem getPreferredSolid(String solid) {
        int solidIndex = 0;
        if (solid.equals("pretzels")) {
            solidIndex = 1;
        }
        return availableSolids.get(solidIndex);
    }

    // EFFECTS: queries the user for their preferred nutrition items and validates that inputs are possible.
    public RaceNutrition queryNutritionInfo(Scanner sc) {
        ValidateStringInput v1 = (i) -> availableSupplementNames.contains(i);
        String supplement = queryUserForStringWithRetry(sc, "What is your preferred supplement? "
                + "Please pick either gel or chew", v1);

        ValidateStringInput v2 = (i) -> availableLiquidNames.contains(i);
        String liquid = queryUserForStringWithRetry(sc, "What is your preferred liquid? "
                + "Please pick one of water, gatorade, or coke", v2);

        ValidateStringInput v3 = (i) -> availableSolidNames.contains(i);
        String solid = queryUserForStringWithRetry(sc, "What is your preferred solid food? "
                + "Please pick either banana or pretzels", v3);
        return new RaceNutrition(getPreferredSupplement(supplement), getPreferredLiquid(liquid),
                getPreferredSolid(solid));
    }

    // EFFECTS: calls methods to query user for biometrics, number of races, and preferred nutrition items.
    //          For each race, calls methods to query user for race information and creates a race strategy based on the
    //          race's macronutrient requirements. Next, it creates a racing season strategy and outputs it.
    //          Lastly, it queries the user to rate the season strategy and exits.
    public void run() {
        // Create a scanner object to query user for input
        Scanner sc = new Scanner(System.in);

        // Ask user for personal inputs
        Triathlete athlete = queryBiometrics(sc);

        // Ask user for preferred nutrition items
        RaceNutrition nutrition = queryNutritionInfo(sc);

        // Store final results in a SeasonStrategies object
        SeasonStrategies ss = new SeasonStrategies(athlete.getName(), 0);

        // For each race, query for info about race conditions and create a race strategy
        for (int i = 1; i <= athlete.getNumRaces(); i++) {
            System.out.println("Asking for input about race #" + i);
            Race race = queryRaceInfo(sc);
            RaceStrategy strategy = new RaceStrategy(athlete, race, nutrition);
            NutritionSummary raceRequirements = strategy.calcRaceRequirement();
            RaceNutrition plan = strategy.calculateOptimumNutritionPlan(raceRequirements);
            ss.appendRaceNutrition(plan);
        }

        // Print final results to user
        System.out.println(ss);

        // Query user for plan rating and exit
        ValidateIntInput v1 = (i) -> i >= 1 && i <= 5;
        int rating = queryUserForIntWithRetry(sc, "How pleased are you with the provided plans [1-5]?", v1);
        ss.setRating(rating);
        System.out.println("Thank you for your rating of " + ss.getRating() + "!");
    }
}
