package model.nutrition;

//Represents a summary of the macronutrients of a nutrition item.

public class NutritionSummary {
    private final int calories;
    private final int carbs;
    private final int potassium;
    private final int sodium;

    // EFFECTS: constructs a summary of the macronutrients of a nutrition item,
    //          namely, calories, carbohydrates in grams, potassium in milligrams,
    //          and sodium in milligrams.
    public NutritionSummary(int calories, int carbs, int potassium, int sodium) {
        this.calories = calories;
        this.carbs = carbs;
        this.potassium = potassium;
        this.sodium = sodium;
    }

    // EFFECTS: returns the caloric content of the nutrition item in calories.
    public int getCalories() {
        return calories;
    }

    // EFFECTS: returns the carbohydrate content of the nutrition item in g.
    public int getCarbs() {
        return carbs;
    }

    // EFFECTS: returns the potassium content of the nutrition item in mg.
    public int getPotassium() {
        return potassium;
    }

    // EFFECTS: returns the sodium content of the nutrition item in mg.
    public int getSodium() {
        return sodium;
    }

    // MODIFIES: this
    // EFFECTS: returns a comprehensible and formatted string representation of the
    //          nutrition summary of a nutrition item.
    @Override
    public String toString() {
        return "(Calories: " + this.calories + " - "
                + "Carbs: " + this.carbs + " - "
                + "Potassium: " + this.potassium + " - "
                + "Sodium: " + this.sodium + ")";
    }
}
