package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.nutrition.NutritionItem;
import model.nutrition.RaceNutrition;
import model.strategy.SeasonStrategies;

import org.json.*;

// Modified from code in the JsonSerializationDemo project provided for reference.

// Represents a reader that reads SeasonStrategies from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads SeasonStrategies from file and returns it;
    // throws IOException if an error occurs reading data from file
    public SeasonStrategies read() throws IOException {
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
        String name = jsonObject.getString("name");
        int rating = jsonObject.getInt("rating");
        SeasonStrategies ss = new SeasonStrategies(name, rating);
        addRaceNutritionPlans(ss, jsonObject);
        return ss;
    }

    // EFFECTS: parses the string representation of the nutrition summary and returns a Nutrition Item object with
    //          the item's name and its macronutrients.
    private NutritionItem parseNutritionSummaryString(String name,  int count, String summary) {
        String[] split = summary.split("-");
        int[] macros = new int[4];
        for (int i = 0; i < split.length; i++) {
            macros[i] = Integer.parseInt(split[i].replace(")", "").split(": ")[1].strip()) / count;
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


    // MODIFIES: ss
    // EFFECTS: parses race nutrition plan from JSON object and adds it to Season Strategies
    private void addRaceNutritionPlan(SeasonStrategies ss, JSONObject jsonObject) {
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

        RaceNutrition rn = new RaceNutrition(supplementNutrition, liquidNutrition, solidNutrition,
                numSupplements, numLiquids, numSolids);

        ss.appendRaceNutrition(rn);
    }

}
