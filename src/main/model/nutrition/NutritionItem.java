package model.nutrition;

//Represents a nutritional item.

public class NutritionItem extends NutritionSummary {
    private final String itemName;

    public NutritionItem(String itemName, int calories, int carbs, int potassium, int sodium) {
        super(calories, carbs, potassium, sodium);
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }
}
