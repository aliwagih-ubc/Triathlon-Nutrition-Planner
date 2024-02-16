package model.nutrition;

public class NutritionPlan {
    private final NutritionItem supplement;
    private final NutritionItem liquid;
    private final NutritionItem solid;

    private int numSupplements;
    private int numLiquids;
    private int numSolids;

    public NutritionPlan(NutritionItem supplement, NutritionItem liquid, NutritionItem solid) {
        this.supplement = supplement;
        this.liquid = liquid;
        this.solid = solid;
    }

    public NutritionItem getSupplement() {
        return supplement;
    }

    public NutritionItem getLiquid() {
        return liquid;
    }

    public NutritionItem getSolid() {
        return solid;
    }

    public void incrementNumSupplements() {
        this.numSupplements += 1;
    }

    public void incrementNumLiquids() {
        this.numLiquids += 1;
    }

    public void incrementNumSolids() {
        this.numSolids += 1;
    }

    public NutritionSummary getSupplementsConsumedNutrition() {
        int calories = this.numSupplements * this.supplement.getCalories();
        int carbs = this.numSupplements * this.supplement.getCarbs();
        int potassium = this.numSupplements * this.supplement.getPotassium();
        int sodium = this.numSupplements * this.supplement.getSodium();
        return new NutritionSummary(calories, carbs, potassium, sodium);
    }

    public NutritionSummary getLiquidsConsumedNutrition() {
        int calories = this.numLiquids * this.liquid.getCalories();
        int carbs = this.numLiquids * this.liquid.getCarbs();
        int potassium = this.numLiquids * this.liquid.getPotassium();
        int sodium = this.numLiquids * this.liquid.getSodium();
        return new NutritionSummary(calories, carbs, potassium, sodium);
    }

    public NutritionSummary getSolidsConsumedNutrition() {
        int calories = this.numSolids * this.solid.getCalories();
        int carbs = this.numSolids * this.solid.getCarbs();
        int potassium = this.numSolids * this.solid.getPotassium();
        int sodium = this.numSolids * this.solid.getSodium();
        return new NutritionSummary(calories, carbs, potassium, sodium);
    }

    public NutritionSummary totalConsumedSoFar() {
        NutritionSummary supplements = getSupplementsConsumedNutrition();
        NutritionSummary liquids = getLiquidsConsumedNutrition();
        NutritionSummary solids = getSolidsConsumedNutrition();
        return new NutritionSummary(
                supplements.getCalories() + liquids.getCalories() + solids.getCalories(),
                supplements.getCarbs() + liquids.getCarbs() + solids.getCarbs(),
                supplements.getPotassium() + liquids.getPotassium() + solids.getPotassium(),
                supplements.getSodium() + liquids.getSodium() + solids.getSodium()
                );
    }

    public boolean areAllNutritionRequirementsMet(NutritionSummary reqs) {
        NutritionSummary curr = this.totalConsumedSoFar();
        return reqs.getCalories() >= curr.getCalories() && reqs.getCarbs() >= curr.getCarbs()
                && reqs.getPotassium() >= curr.getPotassium() && reqs.getSodium() >= curr.getSodium();
    }

    @Override
    public String toString() {
        return this.numSupplements + " " + this.supplement.getItemName() + " "
                + getSupplementsConsumedNutrition().toString() + "\n"
                + this.numLiquids + " " + this.liquid.getItemName() + " "
                + getLiquidsConsumedNutrition().toString() + "\n"
                + this.numSolids + " " + this.solid.getItemName() + " "
                + getSolidsConsumedNutrition().toString();
    }
}
