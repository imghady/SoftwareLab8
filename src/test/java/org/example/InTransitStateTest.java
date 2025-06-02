package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito; // برای Mock کردن Package
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class InTransitStateTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Package mockPackage;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        mockPackage = Mockito.mock(Package.class);
        Mockito.when(mockPackage.getCurrentShippingSpeedDescription()).thenReturn("Test Speed");
        Mockito.when(mockPackage.getCurrentCost()).thenReturn(100.0);
    }

    @Test
    void onEnterState_shouldPrintInTransitMessage() {
        PackageState state = new InTransitState();
        state.onEnterState(mockPackage);
        String output = outContent.toString();
        assertTrue(output.contains("Status Update: Package is IN-TRANSIT."), "In-transit message not found.");
        assertTrue(output.contains("Shipping via: Test Speed"), "Shipping speed info not found.");
        assertTrue(output.contains("Current Estimated Cost: 100.00"), "Estimated cost info not found.");
    }

    @Test
    void isDelivered_shouldReturnFalse() {
        PackageState state = new InTransitState();
        assertFalse(state.isDelivered(), "InTransitState should not be delivered.");
    }

    @Test
    void getStatusDescription_shouldReturnCorrectDescription() {
        PackageState state = new InTransitState();
        assertEquals("in-transit", state.getStatusDescription(), "Description for in-transit state is incorrect.");
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }
}