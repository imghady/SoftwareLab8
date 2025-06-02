package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class PackageTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        Package.resetInstanceForTesting();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void getInstance_shouldPrintInitialInTransitMessage_onFirstCreation() {

        Package pkg = Package.getInstance(2.0);
        String output = outContent.toString();

        assertTrue(output.contains("Status Update: Package is IN-TRANSIT."), "Initial in-transit message not printed on first getInstance.");
        assertTrue(output.contains("Shipping via: Standard (normal speed)"), "Initial shipping speed info not found."); // با فرض استراتژی پیش‌فرض
        assertTrue(output.contains("Current Estimated Cost: " + String.format("%.2f", 2.0 * 2.5)), "Initial estimated cost info not found."); // با فرض هزینه پیش‌فرض
    }

    @Test
    void getInstance_shouldReturnSameInstance_forSubsequentCalls() {
        Package p1 = Package.getInstance(10.0);
        outContent.reset();
        Package p2 = Package.getInstance(10.0);
        assertSame(p1, p2, "getInstance should return the same instance.");
        assertTrue(outContent.toString().isEmpty(), "getInstance should not print new state messages on subsequent calls if state hasn't changed.");
    }

    @Test
    void getInstance_shouldInitializeWithGivenWeight_onFirstCall() {
        Package pkg = Package.getInstance(5.5);
        assertEquals(5.5, pkg.getWeight(), 0.001, "Package weight not initialized correctly.");
        assertTrue(pkg.getCurrentShippingSpeedDescription().contains("Standard"));
        assertFalse(pkg.isDelivered());
    }

    @Test
    void setShippingStrategy_shouldChangeStrategyAndCost_andPrintUpdate() {
        Package pkg = Package.getInstance(10.0);
        outContent.reset();

        pkg.setShippingStrategy(new ExpressShippingStrategy());

        assertEquals(35.0, pkg.getCurrentCost(), 0.001, "Cost not updated after strategy change.");
        assertTrue(pkg.getCurrentShippingSpeedDescription().contains("Express"), "Speed description not updated.");

        String output = outContent.toString();
        assertTrue(output.contains("Shipping method changed to: Express (high speed)"), "Change strategy message missing.");
        assertTrue(output.contains("Updated Estimated Cost: 35.00"), "Updated cost message missing.");
    }

    @Test
    void setPackageState_shouldChangeStateAndPrintUpdate() {
        Package pkg = Package.getInstance(5.0);
        outContent.reset();

        pkg.setPackageState(new DeliveredState());

        assertTrue(pkg.isDelivered(), "Package should be delivered after state change.");
        assertEquals("delivered", pkg.getCurrentStatusDescription(), "Status description not updated.");
        assertTrue(outContent.toString().contains("Status Update: Package has been DELIVERED!"), "Delivered state message missing.");
    }

    @Test
    void getInstance_shouldThrowException_forNegativeWeightOnFirstCall() {

        assertThrows(IllegalArgumentException.class, () -> {
            Package.getInstance(-1.0);
        }, "getInstance should propagate IllegalArgumentException from constructor for negative weight.");
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        Package.resetInstanceForTesting();
    }
}