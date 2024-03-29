package model.race;

import model.nutrition.RaceNutrition;
import org.json.JSONObject;
import persistence.Writable;

// Represents a triathlon race with a distance, season held in, and a maximum number of nutritional items allowed.
public class Race implements Writable {
    private final String distance; // race distance e.g., "sprint", "olympic", etc.
    private final String season; // racing season e.g., "spring", "summer", etc.
    private final RaceNutrition maxNutrition;

    // EFFECTS: creates a race with its distance, season its held in, and the maximum number of nutrition items allowed.
    public Race(String distance, String season, RaceNutrition maxNutrition) {
        this.distance = distance;
        this.season = season;
        this.maxNutrition = maxNutrition;
    }

    // EFFECTS: returns the maximum number of supplements allowed in the race.
    public int getMaxSupplements() {
        return maxNutrition.getNumSupplements();
    }

    // EFFECTS: returns the maximum number of liquids allowed in the race.
    public int getMaxLiquids() {
        return maxNutrition.getNumLiquids();
    }

    // EFFECTS: returns the maximum number of solids allowed in the race.
    public int getMaxSolids() {
        return maxNutrition.getNumSolids();
    }

    // REQUIRES: A validated race with one of the 4 expected distances
    // MODIFIES: this
    // EFFECTS: generates an index pointer to point to the correct column based on the user's inputs.
    public int generateColumnIndex() {
        if (this.distance.equals("sprint")) {
            return 1;
        } else if (this.distance.equals("olympic")) {
            return 2;
        } else if (this.distance.equals("halfIM")) {
            return 3;
        } else {
            return 4;
        }
    }

    // REQUIRES: A validated race happening during one of the four seasons.
    // MODIFIES: this
    // EFFECTS: calculates the anticipated caloric absorption rate based on the anticipated weather conditions.
    public double calcCaloricAbsorptionRate() {
        if (this.season.equals("summer") || this.season.equals("fall")) {
            return 0.18;
        }
        return 0.15;
    }

    // Modified from code in the JsonSerializationDemo project provided for reference.
    // EFFECTS: returns a JSONObject containing the current state of the race object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("distance", distance);
        json.put("season", season);
        json.put("maxNutrition", maxNutrition.toJson());
        return json;
    }
}
