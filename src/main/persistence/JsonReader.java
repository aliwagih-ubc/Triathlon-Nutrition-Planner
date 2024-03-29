package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.nutrition.NutritionItem;
import model.nutrition.RaceNutrition;
import model.race.Race;
import model.strategy.RaceStrategy;
import model.strategy.SeasonStrategies;

import model.triathlete.Triathlete;
import org.json.*;

// Modified from code in the JsonSerializationDemo project provided for reference.

// Represents a reader that reads SeasonStrategies from JSON data stored in file
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads SeasonStrategies from file and returns it;
    // throws IOException if an error occurs reading data from file
    public SeasonStrategies read() throws IOException, JSONException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSeasonStrategies(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses SeasonStrategies from JSON object and returns it
    private SeasonStrategies parseSeasonStrategies(JSONObject jsonObject) {
        int rating = jsonObject.getInt("rating");
        Triathlete athlete = parseTriathlete(jsonObject.getJSONObject("athlete"));
        RaceNutrition preferredNutrition = parseRaceNutrition(jsonObject.getJSONObject("preferred nutrition"));
        SeasonStrategies ss = new SeasonStrategies(athlete, preferredNutrition, rating);
        addRaceNutritionPlans(ss, jsonObject);
        addStrategies(ss, jsonObject);
        return ss;
    }

    // EFFECTS: parses the string representation of the triathlete and returns a Triathlete object
    public Triathlete parseTriathlete(JSONObject jsonObject) {
        String triathleteName = jsonObject.getString("triathleteName");
        int age = jsonObject.getInt("age");
        int weight = jsonObject.getInt("weight");
        String gender = jsonObject.getString("gender");
        int numRaces = jsonObject.getInt("numRaces");
        return new Triathlete(triathleteName, age, weight, gender, numRaces);
    }

    // EFFECTS: parses the string representation of the nutrition summary and returns a Nutrition Item object with
    //          the item's name and its macronutrients.
    private NutritionItem parseNutritionSummaryString(String name,  int count, String summary) {
        String[] split = summary.split("-");
        int[] macros = new int[4];
        for (int i = 0; i < split.length; i++) {
            if (count == 0) {
                macros[i] = 0;
            } else {
                macros[i] = Integer.parseInt(split[i].replace(")", "").split(": ")[1].strip()) / count;
            }
        }
        return new NutritionItem(name, macros[0], macros[1], macros[2], macros[3]);
    }

    // MODIFIES: ss
    // EFFECTS: parses race nutrition plans from JSON object and adds them to Season Strategies
    private void addRaceNutritionPlans(SeasonStrategies ss, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("nutrition plans");
        for (Object json : jsonArray) {
            JSONObject nextRace = (JSONObject) json;
            addRaceNutritionPlan(ss, nextRace);
        }
    }

    // EFFECTS: parses the string representation of the race nutrition and returns a race nutrition object
    private RaceNutrition parseRaceNutrition(JSONObject jsonObject) {
        int numSupplements = jsonObject.getInt("numSupplements");
        String supplement = jsonObject.getString("supplement");
        NutritionItem supplementNutrition = parseNutritionSummaryString(supplement, numSupplements,
                jsonObject.getString("supplementNutrition"));

        int numLiquids = jsonObject.getInt("numLiquids");
        String liquid = jsonObject.getString("liquid");
        NutritionItem liquidNutrition = parseNutritionSummaryString(liquid, numLiquids,
                jsonObject.getString("liquidNutrition"));

        int numSolids = jsonObject.getInt("numSolids");
        String solid = jsonObject.getString("solid");
        NutritionItem solidNutrition = parseNutritionSummaryString(solid, numSolids,
                jsonObject.getString("solidNutrition"));

        return new RaceNutrition(supplementNutrition, liquidNutrition, solidNutrition,
                numSupplements, numLiquids, numSolids);
    }

    private RaceNutrition parseMaxNutrition(JSONObject jsonObject) {
        int numSupplements = jsonObject.getInt("numSupplements");
        int numLiquids = jsonObject.getInt("numLiquids");
        int numSolids = jsonObject.getInt("numSolids");
        return new RaceNutrition(numSupplements, numLiquids, numSolids);
    }

    // EFFECTS: parses the string representation of the race and returns a race object
    private Race parseRace(JSONObject jsonObject) {
        String distance = jsonObject.getString("distance");
        String season = jsonObject.getString("season");
        RaceNutrition rn = parseMaxNutrition(jsonObject.getJSONObject("maxNutrition"));
        return new Race(distance, season, rn);
    }


    // MODIFIES: ss
    // EFFECTS: parses race nutrition plan from JSON object and adds it to Season Strategies
    private void addRaceNutritionPlan(SeasonStrategies ss, JSONObject jsonObject) {
        ss.appendRaceNutrition(parseRaceNutrition(jsonObject));
    }

    // MODIFIES: ss
    // EFFECTS: parses race strategies from JSON object and adds them to Season Strategies
    private void addStrategies(SeasonStrategies ss, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("race strategies");
        for (Object json : jsonArray) {
            JSONObject nextRace = (JSONObject) json;
            addStrategy(ss, nextRace);
        }
    }


    // MODIFIES: ss
    // EFFECTS: parses race strategy from JSON object and adds it to Season Strategies
    private void addStrategy(SeasonStrategies ss, JSONObject jsonObject) {
        Triathlete athlete = parseTriathlete(jsonObject.getJSONObject("triathlete"));
        RaceNutrition preferredNutrition = parseRaceNutrition(jsonObject.getJSONObject("preferredNutrition"));
        Race race = parseRace(jsonObject.getJSONObject("race"));
        ss.appendRaceStrategy(new RaceStrategy(athlete, race, preferredNutrition));
    }

}
