package model.triathlete;

// Represents a triathlete with specific biometrics and number of races
// to be participating in over a season.

import org.json.JSONObject;
import persistence.Writable;

public class Triathlete implements Writable {
    private final String name;
    private final int age; // age in years [18, 29]
    private final int weight; // weight in kg [40, 150]
    private final String gender; // "Male" or "Female"
    private final int numRaces; // number of races to be participated in the season

    // EFFECTS: constructs a triathlete with a name, age, weight, gender, and number of races
    //          they would be participating in over the course of the racing season.
    public Triathlete(String name, int age, int weight, String gender, int numRaces) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.gender = gender;
        this.numRaces = numRaces;
    }

    // EFFECTS: returns the triathlete's name
    public String getName() {
        return name;
    }

    // EFFECTS: returns the triathlete's weight
    public int getWeight() {
        return weight;
    }

    // EFFECTS: returns the triathlete's number of races in the racing season.
    public int getNumRaces() {
        return numRaces;
    }

    // REQUIRES: a valid triathlete
    // MODIFIES: this
    // EFFECTS: generates the row index to be used to point to the correct row in the estimatedfinishtimes data file
    // based on the triathlete's age and gender.
    public String generateAgeGroupGenderIndex() {
        int ageGroupIndex = 1;
        if (this.age >= 25) {
            ageGroupIndex = 2;
        }
        return ageGroupIndex + this.gender;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("triathleteName", name);
        json.put("age", age);
        json.put("weight", weight);
        json.put("gender", gender);
        json.put("numRaces", numRaces);
        return json;
    }
}
