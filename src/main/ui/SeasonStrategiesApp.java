package ui;

import model.nutrition.NutritionItem;
import model.nutrition.NutritionSummary;
import model.nutrition.RaceNutrition;
import model.race.Race;
import model.strategy.RaceStrategy;
import model.strategy.SeasonStrategies;
import model.triathlete.Triathlete;

import org.json.JSONException;
import persistence.JsonWriter;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

// Defines the possible options for the user to pick from and the max allowable nutrition items.
// Instantiates nutrition items. Queries user for inputs and calls methods from main package to return output.
public class SeasonStrategiesApp extends JFrame {
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
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    private static final int WIDTH = 1800;
    private static final int HEIGHT = 1600;
    private final JDesktopPane desktop;

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

        // Construct GUI desktop pane
        desktop = new JDesktopPane();
        setContentPane(desktop);
        setTitle("Season Strategies App");
        setSize(WIDTH, HEIGHT);
        setVisible(true);
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

        availableLiquids.add(new NutritionItem("water", 0, 0, 0, 10));
        availableLiquids.add(new NutritionItem("gatorade", 90, 22, 140, 300));
        availableLiquids.add(new NutritionItem("coke", 200, 55, 20, 40));

        availableSolids.add(new NutritionItem("banana", 105, 27, 422, 1));
        availableSolids.add(new NutritionItem("pretzels", 110, 23, 0, 280));
    }

    public interface UserInteraction {
        String queryUserForStringWithRetry(String inputMessage, ValidateStringInput vs);

        int queryUserForIntWithRetry(String inputMessage, ValidateIntInput vi);

        void output(String message);
    }

    // EFFECTS: validates a string input. Returns true if valid, false otherwise
    public interface ValidateStringInput {
        boolean validate(String input);
    }

    // EFFECTS: validates an integer input. Returns true if valid, false otherwise
    public interface ValidateIntInput {
        boolean validate(int input);
    }

    // EFFECTS: queries the user for their biometrics and validates that user is a Triathlete (as defined in class).
    public Triathlete queryBiometrics(UserInteraction ui) {
        // Check that the user gives a name
        ValidateStringInput v1 = (i) -> i.length() != 0;
        String name = ui.queryUserForStringWithRetry("What is your name?", v1);

        // Check that user gives an age between [18, 29]
        ValidateIntInput v2 = (i) -> i >= 18 && i <= 29;
        int age = ui.queryUserForIntWithRetry("What is your age (in years)? "
                + "Please enter a value between 18 and 29.", v2);

        // Check that user gives a weight between [40, 150]
        ValidateIntInput v3 = (i) -> i >= 40 && i <= 150;
        int weight = ui.queryUserForIntWithRetry("What is your weight (in kg)? "
                + "Please enter a value between 40 and 150.", v3);

        // Check that user gives a possible gender
        ValidateStringInput v4 = possibleGenders::contains;
        String gender = ui.queryUserForStringWithRetry("\"Male\" or \"Female\"? "
                + "Please enter gender exactly as indicated.", v4);

        // Check that user gives a positive nonzero number of races
        ValidateIntInput v5 = (i) -> i > 0;
        int numRaces = ui.queryUserForIntWithRetry("How many races are you participating in? "
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
    public Race queryRaceInfo(UserInteraction ui) {
        // Check that user gives a possible race distance
        ValidateStringInput v1 = possibleRaceDistance::contains;
        String raceDistance = ui.queryUserForStringWithRetry("What distance is this race? "
                + "Please enter one of: sprint, olympic, halfIM, or fullIM", v1);

        // Get max nutrition amounts based on race distance
        RaceNutrition maxNutrition = getMaxNutritionInRace(raceDistance);

        // Check that user gives a possible race distance
        ValidateStringInput v2 = possibleRaceSeason::contains;
        String raceSeason = ui.queryUserForStringWithRetry("What season is this race being held in? "
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
    public RaceNutrition queryNutritionInfo(UserInteraction ui) {
        ValidateStringInput v1 = availableSupplementNames::contains;
        String supplement = ui.queryUserForStringWithRetry("What is your preferred supplement? "
                + "Please pick either gel or chew", v1);

        ValidateStringInput v2 = availableLiquidNames::contains;
        String liquid = ui.queryUserForStringWithRetry("What is your preferred liquid? "
                + "Please pick one of water, gatorade, or coke", v2);

        ValidateStringInput v3 = availableSolidNames::contains;
        String solid = ui.queryUserForStringWithRetry("What is your preferred solid food? "
                + "Please pick either banana or pretzels", v3);
        return new RaceNutrition(getPreferredSupplement(supplement), getPreferredLiquid(liquid),
                getPreferredSolid(solid));
    }

    // EFFECTS: queries the user for their rating of the season strategies and validates input.
    public int queryRating(UserInteraction ui) {
        ValidateIntInput v1 = (i) -> i >= 1 && i <= 5;
        return ui.queryUserForIntWithRetry("How pleased are you with the provided plans [1-5]?", v1);
    }

    // Modified from code in the JsonSerializationDemo project provided for reference.
    // MODIFIES: this
    // EFFECTS: loads season strategies from file
    public SeasonStrategies loadSeasonStrategies(UserInteraction ui) {
        try {
            return jsonReader.read();
        } catch (IOException e) {
            ui.output("Unable to read from file: " + JSON_STORE);
            return null;
        } catch (JSONException e) {
            ui.output("Failed to parse JSON object from file: " + JSON_STORE);
            return null;
        }
    }

    // Modified from code in the JsonSerializationDemo project provided for reference.
    // EFFECTS: saves the season strategies to file
    public void saveSeasonStrategies(SeasonStrategies ss, UserInteraction ui) {
        try {
            jsonWriter.open();
            jsonWriter.write(ss);
            jsonWriter.close();
            ui.output("Saved " + ss.getAthleteName()
                    + "'s Season Strategies to" + JSON_STORE);
        } catch (FileNotFoundException e) {
            ui.output("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: query for info about race conditions, create a race strategy for each race, and add to Season Strategies
    public void createRaceStrategy(UserInteraction ui, Triathlete athlete, RaceNutrition nutrition, SeasonStrategies ss,
                                   int numRaces) {
        for (int i = 1; i <= numRaces; i++) {
            ui.output("Asking for input about race #" + i);
            Race race = queryRaceInfo(ui);
            RaceStrategy strategy = new RaceStrategy(athlete, race, nutrition);
            ss.appendRaceStrategy(strategy);
            NutritionSummary raceRequirements = strategy.calcRaceRequirement();
            RaceNutrition plan = strategy.calculateOptimumNutritionPlan(raceRequirements);
            ss.appendRaceNutrition(plan);
        }
    }

    @SuppressWarnings("methodlength")
    public HashMap obtainMaxNutrition(SeasonStrategies ss) {
        HashMap<String, Integer> itemCounts = new HashMap<>();
        for (RaceNutrition rn : ss.getNutritionPlans()) {
            if (rn != null) {
                String itemName = rn.getSupplement().getItemName();
                itemCounts.put(itemName, itemCounts.getOrDefault(itemName, 0) + rn.getNumSupplements());
                itemName = rn.getLiquid().getItemName();
                itemCounts.put(itemName, itemCounts.getOrDefault(itemName, 0) + rn.getNumLiquids());
                itemName = rn.getSolid().getItemName();
                itemCounts.put(itemName, itemCounts.getOrDefault(itemName, 0) + rn.getNumSolids());
            }
        }
        int max = 0;
        String maxNI = "";
        for (Map.Entry<String, Integer> entry : itemCounts.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                maxNI = entry.getKey();
            }
        }
        HashMap<String, Integer> maxNutrition = new HashMap<>();
        if (!maxNI.isEmpty()) {
            maxNutrition.put(maxNI, max);
            return maxNutrition;
        }
        return null;
    }


    // EFFECTS: creates race nutrition object to store athlete's preferred nutrition.
    public RaceNutrition createPreferredNutrition(SeasonStrategies ss) {
        RaceNutrition savedPreferredNutrition = ss.getPreferredNutrition();
        return new RaceNutrition(
                getPreferredSupplement(savedPreferredNutrition.getSupplement().getItemName()),
                getPreferredLiquid(savedPreferredNutrition.getLiquid().getItemName()),
                getPreferredSolid(savedPreferredNutrition.getSolid().getItemName()));
    }

    // EFFECTS: queries user for rating, sets it, and prints thank you message.
    public void setQueriedRating(UserInteraction ui, SeasonStrategies ss) {
        ss.setRating(queryRating(ui));
        ui.output("Thank you for your rating of " + ss.getRating() + "!");
    }

    // EFFECTS: checks if loaded season strategies exist, queries user for race strategies to add. Calls methods to
    //          query race information for added races and creates a race strategy based on the race's
    //          macronutrient requirements. Lastly, it queries the user to rate the updated season strategy. Prior to
    //          exiting, it queries the user to save the season strategies to file.
    @SuppressWarnings("methodlength")
    public void runAfterLoadingFile(SeasonStrategies ss, UserInteraction ui) {
        if (ss != null) {
            ui.output("Loaded " + ss.getAthleteName() + "'s Season Strategies from" + JSON_STORE);
            ValidateIntInput v1 = (i) -> i >= 0;
            int racesToAdd = ui.queryUserForIntWithRetry("How many races would you like to add to this plan?", v1);
            if (racesToAdd == 0) {
                ui.output(ss.toString());
                ui.output("No changes made to existing plan!");
            } else {
                createRaceStrategy(ui, ss.getAthlete(), createPreferredNutrition(ss), ss, racesToAdd);
                ValidateStringInput v2 = (i) -> i.equals("Yes") || i.equals("No");
                String summary = ui.queryUserForStringWithRetry("Do you want to view a summary of this "
                        + "season's race nutrition strategies? If yes, please enter 'Yes' to save to file, "
                        + "if no, please enter 'No'", v2);
                if (summary.equals("Yes")) {
                    ui.output(ss.toString());
                }
                displayMaxAndImage(ui, ss);
                // Query user for a new plan rating and set it
                setQueriedRating(ui, ss);
                // Ask user to save Season Strategies to file and validates input is possible.
                ValidateStringInput v3 = (i) -> i.equals(possibleCommands.get(1)) || i.equals("No");
                String save = ui.queryUserForStringWithRetry("Do you want to save this season's race "
                        + "nutrition strategies? If yes, please enter 'Save' to save to file, "
                        + "if no, please enter 'No'", v3);
                if (save.equals(possibleCommands.get(1))) {
                    saveSeasonStrategies(ss, ui);
                }
            }
        } else {
            ui.output("Failed to load existing strategies or no saved strategies. Starting new.");
        }
    }

    // EFFECTS: calls methods to query user for biometrics, number of
    //          races, preferred nutrition items, and race information to create a race strategy based on the race's
    //          macronutrient requirements. Next, it creates a racing season strategy and outputs it. Lastly, it queries
    //          the user to rate the season strategy. Prior to exiting, it queries the user to save the season
    //          strategies to file.
    public void runWithoutLoadingFile(UserInteraction ui) {
        Triathlete athlete = queryBiometrics(ui);
        RaceNutrition preferredNutrition = queryNutritionInfo(ui);
        SeasonStrategies ss = new SeasonStrategies(athlete, preferredNutrition,0);
        // For each race, query for info about race conditions, create a race strategy, and add to season strategies
        createRaceStrategy(ui, ss.getAthlete(), ss.getPreferredNutrition(), ss, athlete.getNumRaces());
        // Print final results to user if prompted
        ValidateStringInput v1 = (i) -> i.equals("Yes") || i.equals("No");
        String summary = ui.queryUserForStringWithRetry("Do you want to view a summary of this "
                + "season's race nutrition strategies? If yes, please enter 'Yes' to view, "
                + "if no, please enter 'No'", v1);
        if (summary.equals("Yes")) {
            ui.output(ss.toString());
        }
        displayMaxAndImage(ui, ss);
        // Query user for plan rating and set it
        setQueriedRating(ui, ss);
        // Ask user to save Season Strategies to file and validates input is possible.
        ValidateStringInput v2 = (i) -> i.equals(possibleCommands.get(1)) || i.equals("No");
        String save = ui.queryUserForStringWithRetry("Do you want to save this season's race "
                + "nutrition strategies? If yes, please enter 'Save' to save to file, "
                + "if no, please enter 'No'", v2);
        if (save.equals(possibleCommands.get(1))) {
            saveSeasonStrategies(ss, ui);
        }
    }

    // EFFECTS: queries user to load saved season strategies. If loaded, calls method to run with loaded file. If not
    //          loaded, calls method to run without loaded file.
    public void run() {
        // Create a user interaction object to query user for input
        UserInteraction ui = new GuiInteraction();

        // Ask user if they want to continue with the GUI or revert to the console
        ValidateStringInput v1 = (i) -> i.equals("Yes") || i.equals("No");
        String useGui = ui.queryUserForStringWithRetry("Do you want to continue with the GUI? "
                + "If yes, please enter 'Yes', "
                + "if no, please enter 'No' to switch to the console version", v1);
        if (useGui.equals("No")) {
            ui = new ConsoleInteraction();
        } else {
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }

        // Ask user to load saved Season Strategies and validates input is possible.
        ValidateStringInput v2 = (i) -> i.equals(possibleCommands.get(0)) || i.equals("No");
        String load = ui.queryUserForStringWithRetry("Do you want to load the previous season's race "
                + "nutrition strategies? If yes, please enter 'Load' to load from file, "
                + "if no, please enter 'No'", v2);
        if (load.equals(possibleCommands.get(0))) {
            SeasonStrategies ss = loadSeasonStrategies(ui);
            //runs with loaded file
            runAfterLoadingFile(ss, ui);
        } else {
            ui.output("Not loading previous season's race nutrition strategies!");
            //runs without loaded file
            runWithoutLoadingFile(ui);
        }
        System.exit(0);
    }

    // EFFECTS: creates the image and resizes it. Creates the JFrame to display the label and the image. Displays the
    //          image along with the maximum number of nutrition items needed in the season strategy.
    @SuppressWarnings("methodlength")
    public void displayMaxAndImage(UserInteraction ui, SeasonStrategies ss) {
        ImageIcon icon = new ImageIcon("data/triathlon.jpeg");
        Image image = icon.getImage().getScaledInstance(1600, -1, Image.SCALE_SMOOTH);
        icon = new ImageIcon(image);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel imageLabel = new JLabel(icon, JLabel.CENTER);
        panel.add(imageLabel, BorderLayout.CENTER);

        // Ask user if they want to view the most frequent nutrition item
        ValidateStringInput v1 = (i) -> i.equals("Yes") || i.equals("No");
        String showMax = ui.queryUserForStringWithRetry("Do you want to view the most frequent nutrition "
                + "item this season? If yes, please enter 'Yes', otherwise please enter 'No'.", v1);
        if (showMax.equals("Yes")) {
            HashMap<String, Integer> maxNutritionMap = obtainMaxNutrition(ss);
            if (maxNutritionMap != null && !maxNutritionMap.isEmpty()) {
                String maxNI = new ArrayList<String>(maxNutritionMap.keySet()).get(0);
                int max = maxNutritionMap.get(maxNI);
                JLabel textLabel = new JLabel("You will need " + max + " " + maxNI + "s. Time to stock up!",
                        JLabel.CENTER);
                panel.add(textLabel, BorderLayout.NORTH);
            }
        } else {
            ui.output("Not displaying the most frequent nutrition item this season!");
        }
        JFrame maxImageFrame = new JFrame("Happy Racing!");
        maxImageFrame.add(panel);
        maxImageFrame.setSize(1800, 800);
        maxImageFrame.setLocationRelativeTo(null);
        maxImageFrame.setVisible(true);
    }

}
