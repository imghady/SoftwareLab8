package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StandardShippingStrategyTest {

    @Test
    void calculateCost_shouldReturnCorrectCost_forGivenWeight() {
        ShippingStrategy strategy = new StandardShippingStrategy();
        double weight = 10.0;
        double expectedCost = weight * 2.5; // 25.0
        assertEquals(expectedCost, strategy.calculateCost(weight), 0.001,
                "Cost should be weight * 2.5");
    }

    @Test
    void calculateCost_shouldReturnZero_forZeroWeight() {
        ShippingStrategy strategy = new StandardShippingStrategy();
        assertEquals(0.0, strategy.calculateCost(0.0), 0.001,
                "Cost should be 0 for zero weight");
    }

    @Test
    void getSpeedDescription_shouldReturnCorrectDescription() {
        ShippingStrategy strategy = new StandardShippingStrategy();
        assertEquals("Standard (normal speed)", strategy.getSpeedDescription(),
                "Description for standard shipping is incorrect.");
    }
}