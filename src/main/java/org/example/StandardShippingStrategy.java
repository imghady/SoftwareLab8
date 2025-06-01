package org.example;


public class StandardShippingStrategy implements ShippingStrategy {
    private static final double COST_PER_KG = 2.5;

    @Override
    public double calculateCost(double weight) {
        return weight * COST_PER_KG;
    }

    @Override
    public String getSpeedDescription() {
        return "Standard (normal speed)";
    }
}