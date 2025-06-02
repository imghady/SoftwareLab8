package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExpressShippingStrategyTest {

    @Test
    void calculateCost_shouldReturnCorrectCost_forGivenWeight() {
        ShippingStrategy strategy = new ExpressShippingStrategy();
        double weight = 10.0;
        double expectedCost = weight * 3.5; // 35.0
        assertEquals(expectedCost, strategy.calculateCost(weight), 0.001,
                "Cost should be weight * 3.5");
    }

    @Test
    void calculateCost_shouldReturnZero_forZeroWeight() {
        ShippingStrategy strategy = new ExpressShippingStrategy();
        assertEquals(0.0, strategy.calculateCost(0.0), 0.001,
                "Cost should be 0 for zero weight");
    }

    @Test
    void getSpeedDescription_shouldReturnCorrectDescription() {
        ShippingStrategy strategy = new ExpressShippingStrategy();
        assertEquals("Express (high speed)", strategy.getSpeedDescription(),
                "Description for express shipping is incorrect.");
    }
}