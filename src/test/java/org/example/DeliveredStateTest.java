package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class DeliveredStateTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Package mockPackage;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        mockPackage = Mockito.mock(Package.class);
        Mockito.when(mockPackage.getCurrentShippingSpeedDescription()).thenReturn("Final Test Speed");
        Mockito.when(mockPackage.getCurrentCost()).thenReturn(120.50);
    }

    @Test
    void onEnterState_shouldPrintDeliveredMessage() {
        PackageState state = new DeliveredState();
        state.onEnterState(mockPackage);
        String output = outContent.toString();
        assertTrue(output.contains("Status Update: Package has been DELIVERED!"), "Delivered message not found.");
        assertTrue(output.contains("Final Shipping Method: Final Test Speed"), "Final shipping method info not found.");
        assertTrue(output.contains("Final Cost: 120.50"), "Final cost info not found.");
    }

    @Test
    void isDelivered_shouldReturnTrue() {
        PackageState state = new DeliveredState();
        assertTrue(state.isDelivered(), "DeliveredState should be delivered.");
    }

    @Test
    void getStatusDescription_shouldReturnCorrectDescription() {
        PackageState state = new DeliveredState();
        assertEquals("delivered", state.getStatusDescription(), "Description for delivered state is incorrect.");
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }
}