package org.example;


public class ExpressShippingStrategy implements ShippingStrategy {
    private static final double COST_PER_KG = 3.5;

    @Override
    public double calculateCost(double weight) {
        return weight * COST_PER_KG;
    }

    @Override
    public String getSpeedDescription() {
        return "Express (high speed)";
    }
}