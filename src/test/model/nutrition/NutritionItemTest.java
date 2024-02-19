package model.nutrition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NutritionItemTest {
    private NutritionItem ni;

    @BeforeEach
    void setUp() {
        ni = new NutritionItem("apple", 100, 10, 10, 10);
    }

    @Test
    void testGetItemName() {
        assertEquals("apple", ni.getItemName());
    }
}