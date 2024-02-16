package model.triathlete;

// Represents a triathlete with specific biometrics and number of races
// to be participating in over a season.

public class Triathlete {
    private final int age; // age in years
    private final int weight; //weight in kg
    private final char gender; //'m' or 'f'
    private final int numRaces; // number of races to be participated in the season

    public Triathlete(int age, int weight, char gender, int numRaces) {
        this.age = age;
        this.weight = weight;
        this.gender = gender;
        this.numRaces = numRaces;
    }

    public int getWeight() {
        return weight;
    }

    public int getNumRaces() {
        return numRaces;
    }

    // REQUIRES: validateTriathlete == true
    public String generateAgeGroupGenderIndex() {
        int ageGroupIndex = 1;
        if (this.age >= 25) {
            ageGroupIndex = 2;
        }
        return Integer.toString(ageGroupIndex) + this.gender;
    }
}
