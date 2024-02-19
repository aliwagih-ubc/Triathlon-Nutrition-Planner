package model.triathlete;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class TriathleteTest{

    private Triathlete triathlete;

    @BeforeEach
    void setUp() {
        triathlete = new Triathlete("ali", 28, 90, "Male", 1);
    }

    @Test
    void testTriathlete() {
        assertEquals("ali", triathlete.getName());
        assertEquals(90, triathlete.getWeight());
        assertEquals(1, triathlete.getNumRaces());
    }

    @Test
    void testGenerateAgeGroupIndex() {
        assertEquals("2Male", triathlete.generateAgeGroupGenderIndex());
    }
}