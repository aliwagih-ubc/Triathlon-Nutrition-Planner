package ui;

import model.nutrition.NutritionItem;
import model.nutrition.NutritionSummary;
import model.nutrition.RaceNutrition;
import model.race.Race;
import model.strategy.RaceStrategy;
import model.strategy.SeasonStrategies;
import model.triathlete.Triathlete;

import persistence.JsonWriter;
import persistence.JsonReader;

import java.io.FileNotFoundException;
import java.io.IOException;
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

    private final List<String> possibleCommands = new ArrayList<>();

    private static final String JSON_STORE = "./data/seasonstrategies.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: constructs a SeasonStrategiesApp object
    public SeasonStrategiesApp() throws FileNotFoundException {
        // Populate possible genders
        populatePossibleGenders();

        // Populate possible race conditions (distance and season)
        populatePossibleRaceConditions();

        // Populate available nutrition item names
        populateAvailableNutritionItemNames();

        // Populate available nutrition items
        populateAvailableNutritionItems();

        // Populate possible saving and loading commands
        populatePossibleCommands();

        // Construct a jsonWriter to write to source file
        jsonWriter = new JsonWriter(JSON_STORE);

        // Construct a jsonReader to read from source file
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: adds the possible gender options in the app to the list of possible genders.
    public void populatePossibleGenders() {
        possibleGenders.add("Male");
        possibleGenders.add("Female");
    }

    // MODIFIES: this
    // EFFECTS: adds the possible loading and saving options to the list of possible commands.
    public void populatePossibleCommands() {
        possibleCommands.add("Load");
        possibleCommands.add("Save");
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

    // EFFECTS: Queries the user for a string input.
    public boolean queryUserForStringNoRetry(Scanner sc, String inputMessage, String expectedString) {
        System.out.println(inputMessage);
        try {
            String input = sc.nextLine();
            return (expectedString.equals(input));
        } catch (Exception e) {
            return false;
        }
    }


    // EFFECTS: Queries the user for an int input. Allows the user to continue querying if an unexpected input or
    // wrong input type is received.
    public int queryUserForIntWithRetry(Scanner sc, String inputMessage, ValidateIntInput vi) {
        while (true) {
            System.out.println(inputMessage);
            try {
                String strInput = sc.nextLine();
                int input = Integer.parseInt(strInput);
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
        ValidateStringInput v4 = possibleGenders::contains;
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
            return new RaceNutrition(4, 8, 2);
        } else if (raceDistance.equals("olympic")) {
            return new RaceNutrition(10, 14, 6);
        } else if (raceDistance.equals("halfIM")) {
            return new RaceNutrition(35, 28, 12);
        } else {
            return new RaceNutrition(50, 40, 40);
        }
    }

    // EFFECTS: queries the user for the race information and validates that inputs are possible.
    public Race queryRaceInfo(Scanner sc) {
        // Check that user gives a possible race distance
        ValidateStringInput v1 = possibleRaceDistance::contains;
        String raceDistance = queryUserForStringWithRetry(sc, "What distance is this race? "
                + "Please enter one of: sprint, olympic, halfIM, or fullIM", v1);

        // Get max nutrition amounts based on race distance
        RaceNutrition maxNutrition = getMaxNutritionInRace(raceDistance);

        // Check that user gives a possible race distance
        ValidateStringInput v2 = possibleRaceSeason::contains;
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
        ValidateStringInput v1 = availableSupplementNames::contains;
        String supplement = queryUserForStringWithRetry(sc, "What is your preferred supplement? "
                + "Please pick either gel or chew", v1);

        ValidateStringInput v2 = availableLiquidNames::contains;
        String liquid = queryUserForStringWithRetry(sc, "What is your preferred liquid? "
                + "Please pick one of water, gatorade, or coke", v2);

        ValidateStringInput v3 = availableSolidNames::contains;
        String solid = queryUserForStringWithRetry(sc, "What is your preferred solid food? "
                + "Please pick either banana or pretzels", v3);
        return new RaceNutrition(getPreferredSupplement(supplement), getPreferredLiquid(liquid),
                getPreferredSolid(solid));
    }

    // EFFECTS: queries the user for their rating of the season strategies and validates input.
    public int queryRating(Scanner sc) {
        ValidateIntInput v1 = (i) -> i >= 1 && i <= 5;
        return queryUserForIntWithRetry(sc, "How pleased are you with the provided plans [1-5]?", v1);
    }

    // Modified from code in the JsonSerializationDemo project provided for reference.
    // MODIFIES: this
    // EFFECTS: loads season strategies from file
    public SeasonStrategies loadSeasonStrategies() {
        try {
            SeasonStrategies ss = jsonReader.read();
            System.out.println("Loaded " + ss.getAthleteName()
                    + "'s Season Strategies from" + JSON_STORE);
            return ss;
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            return null;
        }
    }

    // Modified from code in the JsonSerializationDemo project provided for reference.
    // EFFECTS: saves the season strategies to file
    public void saveSeasonStrategies(SeasonStrategies ss) {
        try {
            jsonWriter.open();
            jsonWriter.write(ss);
            jsonWriter.close();
            System.out.println("Saved " + ss.getAthleteName()
                    + "'s Season Strategies to" + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }


    // EFFECTS: query for info about race conditions, create a race strategy for each race, and add to Season Strategies
    public void createRaceStrategy(Scanner sc, Triathlete athlete, RaceNutrition nutrition, SeasonStrategies ss) {
        for (int i = 1; i <= athlete.getNumRaces(); i++) {
            System.out.println("Asking for input about race #" + i);
            Race race = queryRaceInfo(sc);
            RaceStrategy strategy = new RaceStrategy(athlete, race, nutrition);
            NutritionSummary raceRequirements = strategy.calcRaceRequirement();
            RaceNutrition plan = strategy.calculateOptimumNutritionPlan(raceRequirements);
            ss.appendRaceNutrition(plan);
        }
    }

    // EFFECTS: queries user to load saved season strategies, calls methods to query user for biometrics, number of
    //          races, preferred nutrition items, and race information to create a race strategy based on the race's
    //          macronutrient requirements. Next, it creates a racing season strategy and outputs it. Lastly, it queries
    //          the user to rate the season strategy. Prior to exiting, it queries the user to save the season
    //          strategies to file.
    public void run() {
        // Create a scanner object to query user for input
        Scanner sc = new Scanner(System.in);

        // Ask user to load saved Season Strategies and validates input is possible.
        if (queryUserForStringNoRetry(sc, "Do you want to load the previous season's race "
                + "nutrition strategies? If yes, please enter 'Load' to load from file", possibleCommands.get(0))) {
            SeasonStrategies ss = loadSeasonStrategies();
            System.out.println(ss);
            return;
        }

        // Ask user for personal inputs
        Triathlete athlete = queryBiometrics(sc);

        // Ask user for preferred nutrition items
        RaceNutrition nutrition = queryNutritionInfo(sc);

        // Store final results in a SeasonStrategies object
        SeasonStrategies ss = new SeasonStrategies(athlete.getName(), 0);

        // For each race, query for info about race conditions, create a race strategy, and add to season strategies
        createRaceStrategy(sc, athlete, nutrition, ss);

        // Print final results to user
        System.out.println(ss);

        // Query user for plan rating and set it
        ss.setRating(queryRating(sc));
        System.out.println("Thank you for your rating of " + ss.getRating() + "!");


        // Ask user to save Season Strategies to file and validates input is possible.
        if (queryUserForStringNoRetry(sc, "Do you want to save this season's race "
                + "nutrition strategies? If yes, please enter 'Save' to save to file", possibleCommands.get(1))) {
            saveSeasonStrategies(ss);
        }
    }
}
