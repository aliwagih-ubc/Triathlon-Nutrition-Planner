package model.nutrition;

//Represents a nutritional item.

public class NutritionSummary {
    private final int calories;
    private final int carbs;
    private final int potassium;
    private final int sodium;

    public NutritionSummary(int calories, int carbs, int potassium, int sodium) {
        this.calories = calories;
        this.carbs = carbs;
        this.potassium = potassium;
        this.sodium = sodium;
    }

    public int getCalories() {
        return calories;
    }

    public int getCarbs() {
        return carbs;
    }

    public int getPotassium() {
        return potassium;
    }

    public int getSodium() {
        return sodium;
    }

    @Override
    public String toString() {
        return "(Calories: " + this.calories + " - "
                + "Carbs: " + this.carbs + " - "
                + "Potassium: " + this.potassium + " - "
                + "Sodium: " + this.sodium + ")";
    }
}
