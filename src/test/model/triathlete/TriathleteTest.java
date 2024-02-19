package model.triathlete;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class TriathleteTest{

    private Triathlete triathlete1;
    private Triathlete triathlete2;
    private Triathlete triathlete3;

    @BeforeEach
    void setUp() {
        triathlete1 = new Triathlete("ali", 28, 90, "Male", 1);
        triathlete2 = new Triathlete("ali", 25, 90, "Male", 1);
        triathlete3 = new Triathlete("ali", 20, 90, "Male", 1);
    }

    @Test
    void testTriathlete() {
        assertEquals("ali", triathlete1.getName());
        assertEquals(90, triathlete1.getWeight());
        assertEquals(1, triathlete1.getNumRaces());
    }

    @Test
    void testGenerateAgeGroupIndex() {
        assertEquals("2Male", triathlete1.generateAgeGroupGenderIndex());
        assertEquals("2Male", triathlete2.generateAgeGroupGenderIndex());
        assertEquals("1Male", triathlete3.generateAgeGroupGenderIndex());
    }
}