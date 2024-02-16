package model.race;

// Represents a triathlon race.
public class Race {
    private final String distance; // race distance e.g., "sprint", "olympic", etc.
    private final String season; // racing season e.g., "spring", "summer", etc.
    private final int maxSupplements;
    private final int maxLiquids;
    private final int maxSolids;

    // EFFECTS: creates a race with distance, estimated finish time, and season
    public Race(String distance, String season, int maxSupplements, int maxLiquids, int maxSolids) {
        this.distance = distance;
        this.season = season;
        this.maxSupplements = maxSupplements;
        this.maxLiquids = maxLiquids;
        this.maxSolids = maxSolids;
    }

    public int getMaxSupplements() {
        return maxSupplements;
    }

    public int getMaxLiquids() {
        return maxLiquids;
    }

    public int getMaxSolids() {
        return maxSolids;
    }

    // REQUIRES: A validated race with one of the 4 expected distances
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

    public double calcCaloricAbsorptionRate() {
        if (this.season.equals("summer") || this.season.equals("fall")) {
            return 0.18;
        }
        return 0.15;
    }
}
