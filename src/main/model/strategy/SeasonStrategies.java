package model.strategy;

import model.nutrition.RaceNutrition;

import java.util.ArrayList;
import java.util.List;

// Represents a complete racing season summary of the race nutrition strategies
// developed for all the races.

// EFFECTS: constructs
public class SeasonStrategies {
    private final String athleteName;
    private final List<RaceNutrition> nutritionPlans;
    private int rating;

    // EFFECTS: constructs a racing season summary for a triathlete and their rating of the summary's output.
    public SeasonStrategies(String athleteName, int rating) {
        this.athleteName = athleteName;
        this.nutritionPlans = new ArrayList<>();
        this.rating = rating;
    }

    public void appendRaceNutrition(RaceNutrition rn) {
        this.nutritionPlans.add(rn);
    }

    // EFFECTS: returns the rating of the season summary.
    public int getRating() {
        return rating;
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
        str.append("Season strategy for ").append(athleteName);
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
}
