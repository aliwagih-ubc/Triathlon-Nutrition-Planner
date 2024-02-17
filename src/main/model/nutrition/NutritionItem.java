package model.nutrition;

//Represents a nutrition item with its name and its macronutrients.

public class NutritionItem extends NutritionSummary {
    private final String itemName;

    // EFFECTS: constructs a nutrition item with its name, calories (cal), carbohydrate content (g),
    //          potassium content (mg), and sodium content (mg).
    public NutritionItem(String itemName, int calories, int carbs, int potassium, int sodium) {
        super(calories, carbs, potassium, sodium);
        this.itemName = itemName;
    }

    // EFFECTS: returns the name of the nutrition item.
    public String getItemName() {
        return itemName;
    }
}

