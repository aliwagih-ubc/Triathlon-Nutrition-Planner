package model.strategy;

import model.Event;
import model.EventLog;
import model.nutrition.NutritionSummary;
import model.nutrition.RaceNutrition;
import model.race.Race;
import model.triathlete.Triathlete;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Represents a complete racing season summary of the race nutrition strategies
// developed for all the races.

public class SeasonStrategies implements Writable {
    private final List<RaceNutrition> nutritionPlans;
    private int rating;
    private Triathlete athlete;
    private final List<RaceStrategy> strategies;
    private RaceNutrition preferredNutrition;


    // EFFECTS: constructs a racing season summary for a triathlete and their rating of the summary's output.
    public SeasonStrategies(Triathlete athlete, RaceNutrition preferredNutrition, int rating) {
        this.athlete = athlete;
        this.preferredNutrition = preferredNutrition;
        this.nutritionPlans = new ArrayList<>();
        this.strategies = new ArrayList<>();
        this.rating = rating;
    }

    // EFFECTS: appends a race nutrition plan to a list of plans.
    public void appendRaceNutrition(RaceNutrition rn) {
        this.nutritionPlans.add(rn);
    }

    // EFFECTS: appends a race strategy to a list of strategies.
    public void appendRaceStrategy(RaceStrategy rs) {
        this.strategies.add(rs);
    }

    // EFFECTS: returns the nutrition plans in the season strategies object.
    public List<RaceNutrition> getNutritionPlans() {
        return nutritionPlans;
    }

    // EFFECTS: returns the strategies in the season strategies object.
    public List<RaceStrategy> getStrategies() {
        return strategies;
    }

    // EFFECTS: returns the number of nutrition plans in the season strategies object.
    public int getNumOfStrategies() {
        return strategies.size();
    }

    // EFFECTS: returns the number of nutrition plans in the season strategies object.
    public int getNumOfNutritionPlans() {
        return nutritionPlans.size();
    }

    // EFFECTS: returns the rating of the season summary.
    public int getRating() {
        return rating;
    }

    // EFFECTS: returns the name of the of athlete in the season strategies object.
    public String getAthleteName() {
        return athlete.getName();
    }

    // EFFECTS: returns the triathlete object in the season strategies object.
    public Triathlete getAthlete() {
        return athlete;
    }

    // EFFECTS: sets this triathlete to triathlete
    public void setAthlete(Triathlete athlete) {
        this.athlete = athlete;
    }

    // EFFECTS: returns the preferred nutrition object in the season strategies object.
    public RaceNutrition getPreferredNutrition() {
        return preferredNutrition;
    }

//    // EFFECTS: sets this preferred nutrition to preferred nutrition
//    public void setPreferredNutrition(RaceNutrition preferredNutrition) {
//        this.preferredNutrition = preferredNutrition;
//    }

    // EFFECTS: sets this rating to rating
    public void setRating(int rating) {
        this.rating = rating;
        EventLog.getInstance().logEvent(new Event("Season strategies rated."));
    }

    
    // EFFECTS: calculates the most frequent nutrition item in the season's strategies and returns its name and 
    //          number of items
    public HashMap obtainMaxNutrition(SeasonStrategies ss) {
        HashMap<String, Integer> itemCounts = new HashMap<>();
        aggregateNumberOfItems(ss, itemCounts);
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
            EventLog.getInstance().logEvent(new Event("Most frequent nutrition item name and count determined."));
            return maxNutrition;
        }
        return null;
    }


    // EFFECTS: aggregates the number of each category of nutrition items (solid, liquid, supplement) in the season
    //          strategy.
    public static void aggregateNumberOfItems(SeasonStrategies ss, HashMap<String, Integer> itemCounts) {
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
    }

    // MODIFIES: this
    // EFFECTS: create a race strategy for the race and adds it to Season Strategies
    public void createRaceStrategy(Race race, Triathlete athlete, RaceNutrition nutrition,
                                   SeasonStrategies ss) {
//        for (int i = 1; i <= numRaces; i++) {
        RaceStrategy strategy = new RaceStrategy(athlete, race, nutrition);
        ss.appendRaceStrategy(strategy);
        NutritionSummary raceRequirements = strategy.calcRaceRequirement();
        EventLog.getInstance().logEvent(new Event("Anticipated caloric absorption rate determined based on "
                + "anticipated weather conditions."));
        EventLog.getInstance().logEvent(new Event("Average finish time obtained based on user biometrics and "
                + "race details."));
        EventLog.getInstance().logEvent(new Event("Average macronutrients to complete the race calculated."));

        RaceNutrition plan = strategy.calculateOptimumNutritionPlan(raceRequirements);
        EventLog.getInstance().logEvent(new Event("Optimum nutrition plan to complete the race with the "
                + "athlete's preferred nutrition items determined."));
        ss.appendRaceNutrition(plan);
        EventLog.getInstance().logEvent(new Event("Race nutrition plan added to the season's strategies."));

//        }
    }


    // MODIFIES: this
    // EFFECTS: returns a comprehensible and formatted string representation of all the racing
    // strategies throughout a racing season to be printed in the SeasonStrategiesApp class.
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Season strategy for ").append(athlete.getName());
        for (int i = 1; i <= nutritionPlans.size(); i++) {
            str.append("\n").append("Race #").append(i).append("\n");
            RaceNutrition plan = nutritionPlans.get(i - 1);
            if (plan == null) {
                str.append("No possible plan within the maximum nutrition limit for this race");
            } else {
                str.append(plan);
            }
        }
        return str.toString();
    }
    // Modified from code in the JsonSerializationDemo project provided for reference.
    // EFFECTS: returns a JSONObject containing the current state of the Season Strategies object

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("athlete", athlete.toJson());
        json.put("preferred nutrition", preferredNutrition.toJson());
        json.put("rating", rating);
        json.put("nutrition plans", nutritionPlansToJson());
        json.put("race strategies", strategiesToJson());
        return json;
    }

    // Modified from code in the JsonSerializationDemo project provided for reference.
    // EFFECTS: returns nutrition plans in this Season Strategies object as a JSON array
    private JSONArray nutritionPlansToJson() {
        JSONArray jsonArray = new JSONArray();

        for (RaceNutrition rn : nutritionPlans) {
            if (rn == null) {
                JSONObject noPossiblePlanJson = new JSONObject();
                noPossiblePlanJson.put("nutrition plan", "No possible plan within the maximum nutrition limit for "
                        + "this race");
                jsonArray.put(noPossiblePlanJson);
            } else {
                jsonArray.put(rn.toJson());
            }
        }
        return jsonArray;
    }

    // Modified from code in the JsonSerializationDemo project provided for reference.
    // EFFECTS: returns strategies in this Season Strategies object as a JSON array
    private JSONArray strategiesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (RaceStrategy rs : strategies) {
            if (rs == null) {
                JSONObject noPossibleStrategyJson = new JSONObject();
                noPossibleStrategyJson.put("race strategy", "No strategy exists for this race");
                jsonArray.put(noPossibleStrategyJson);
            } else {
                jsonArray.put(rs.toJson());
            }
        }
        return jsonArray;
    }
}
