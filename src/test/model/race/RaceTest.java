package model.race;

import model.nutrition.RaceNutrition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class RaceTest {

    private Race race1;
    private Race race2;

    @BeforeEach
    void setUp() {
        RaceNutrition rn = new RaceNutrition(1, 1, 1);
        this.race1 = new Race("sprint", "winter", rn);
        this.race2 = new Race("fullIM", "fall", rn);
    }

    @Test
    void testRace() {
        assertEquals(1, race1.getMaxSupplements());
        assertEquals(1, race2.getMaxLiquids());
        assertEquals(1, race1.getMaxSolids());
    }

    @Test
    void testGenerateColumnIndex() {
        assertEquals(1, this.race1.generateColumnIndex());
        assertEquals(4, this.race2.generateColumnIndex());
    }

    @Test
    void testCalcCaloricAbsorptionRate() {
        assertEquals(0.15, this.race1.calcCaloricAbsorptionRate());
        assertEquals(0.18, this.race2.calcCaloricAbsorptionRate());
    }
}