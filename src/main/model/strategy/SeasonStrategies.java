package model.strategy;

import model.nutrition.RaceNutrition;
import model.triathlete.Triathlete;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

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

    public Triathlete getAthlete() {
        return athlete;
    }

    public void setAthlete(Triathlete athlete) {
        this.athlete = athlete;
    }

    public RaceNutrition getPreferredNutrition() {
        return preferredNutrition;
    }

    public void setPreferredNutrition(RaceNutrition preferredNutrition) {
        this.preferredNutrition = preferredNutrition;
    }

    // EFFECTS: sets this rating to rating
    public void setRating(int rating) {
        this.rating = rating;
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
