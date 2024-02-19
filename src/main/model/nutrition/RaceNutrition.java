package model.nutrition;

// Represents a race nutrition plan composed of a supplement, a liquid, and a solid nutrition item
// or their quantities.
public class RaceNutrition {
    private NutritionItem supplement;
    private NutritionItem liquid;
    private NutritionItem solid;

    private int numSupplements;
    private int numLiquids;
    private int numSolids;

    // EFFECTS: constructs a race nutrition plan with a supplement, liquid, and solid nutrition item.
    public RaceNutrition(NutritionItem supplement, NutritionItem liquid, NutritionItem solid) {
        this.supplement = supplement;
        this.liquid = liquid;
        this.solid = solid;
    }

    // EFFECTS: constructs a race nutrition plan with the quantities of the nutrition items.
    public RaceNutrition(int numSupplements, int numLiquids, int numSolids) {
        this.numSupplements = numSupplements;
        this.numLiquids = numLiquids;
        this.numSolids = numSolids;
    }

    // EFFECTS: returns the number of supplements in the race nutrition plan.
    public int getNumSupplements() {
        return numSupplements;
    }

    // EFFECTS: returns the number of liquids in the race nutrition plan.
    public int getNumLiquids() {
        return numLiquids;
    }

    // EFFECTS: returns the number of solids in the race nutrition plan.
    public int getNumSolids() {
        return numSolids;
    }

    // EFFECTS: returns the supplement nutrition item in this race nutrition plan.
    public NutritionItem getSupplement() {
        return supplement;
    }

    // EFFECTS: returns the liquid nutrition item in this race nutrition plan.
    public NutritionItem getLiquid() {
        return liquid;
    }

    // EFFECTS: returns the solid nutrition item in this race nutrition plan.
    public NutritionItem getSolid() {
        return solid;
    }

    // MODIFIES: this
    // EFFECTS: increments the number of supplement nutrition items in a race nutrition plan
    public void incrementNumSupplements() {
        this.numSupplements += 1;
    }

    // MODIFIES: this
    // EFFECTS: increments the number of liquid nutrition items in a race nutrition plan
    public void incrementNumLiquids() {
        this.numLiquids += 1;
    }

    // MODIFIES: this
    // EFFECTS: increments the number of solid nutrition items in a race nutrition plan
    public void incrementNumSolids() {
        this.numSolids += 1;
    }

    // MODIFIES: this
    // EFFECTS: returns an aggregated summary of the macronutrients in all the supplements of this
    //          race nutrition plan.
    public NutritionSummary getSupplementsConsumedNutrition() {
        int calories = this.numSupplements * this.supplement.getCalories();
        int carbs = this.numSupplements * this.supplement.getCarbs();
        int potassium = this.numSupplements * this.supplement.getPotassium();
        int sodium = this.numSupplements * this.supplement.getSodium();
        return new NutritionSummary(calories, carbs, potassium, sodium);
    }

    // MODIFIES: this
    // EFFECTS: returns an aggregated summary of the macronutrients in all the liquids of this
    //          race nutrition plan.
    public NutritionSummary getLiquidsConsumedNutrition() {
        int calories = this.numLiquids * this.liquid.getCalories();
        int carbs = this.numLiquids * this.liquid.getCarbs();
        int potassium = this.numLiquids * this.liquid.getPotassium();
        int sodium = this.numLiquids * this.liquid.getSodium();
        return new NutritionSummary(calories, carbs, potassium, sodium);
    }

    // MODIFIES: this
    // EFFECTS: returns an aggregated summary of the macronutrients in all the solids of this
    //          race nutrition plan.
    public NutritionSummary getSolidsConsumedNutrition() {
        int calories = this.numSolids * this.solid.getCalories();
        int carbs = this.numSolids * this.solid.getCarbs();
        int potassium = this.numSolids * this.solid.getPotassium();
        int sodium = this.numSolids * this.solid.getSodium();
        return new NutritionSummary(calories, carbs, potassium, sodium);
    }

    // MODIFIES: this
    // EFFECTS: returns an aggregated summary of the macronutrients of all the nutrition items in this
    //          race nutrition plan.
    public NutritionSummary totalConsumed() {
        NutritionSummary supplements = getSupplementsConsumedNutrition();
        NutritionSummary liquids = getLiquidsConsumedNutrition();
        NutritionSummary solids = getSolidsConsumedNutrition();
        return new NutritionSummary(supplements.getCalories() + liquids.getCalories() + solids.getCalories(),
                supplements.getCarbs() + liquids.getCarbs() + solids.getCarbs(),
                supplements.getPotassium() + liquids.getPotassium() + solids.getPotassium(),
                supplements.getSodium() + liquids.getSodium() + solids.getSodium());
    }

    // MODIFIES: this
    // EFFECTS: returns true if all the consumed macronutrients in a race plan are greater than or equal to the
    //          macronutrients required to complete the race.
    public boolean areAllNutritionRequirementsMet(NutritionSummary required) {
        NutritionSummary consumed = this.totalConsumed();
        return required.getCalories() <= consumed.getCalories() && required.getCarbs() <= consumed.getCarbs()
                && required.getPotassium() <= consumed.getPotassium() && required.getSodium() <= consumed.getSodium();
    }

    // MODIFIES: this
    // EFFECTS: returns a comprehensible and formatted string representation of the
    //          race nutrition plan including quantity of the items, the nutrition item's name, and the item's
    //          macronutrient content.
    public String toString() {
        return this.numSupplements + " " + this.supplement.getItemName() + " "
                + getSupplementsConsumedNutrition().toString() + "\n"
                + this.numLiquids + " " + this.liquid.getItemName() + " "
                + getLiquidsConsumedNutrition().toString() + "\n"
                + this.numSolids + " " + this.solid.getItemName() + " "
                + getSolidsConsumedNutrition().toString();
    }
}
