package org.example;

public class Package {
    private double weight;
    private ShippingStrategy shippingStrategy;
    private PackageState packageState;

    public Package(double weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be positive.");
        }
        this.weight = weight;
        // Default strategy and state
        this.shippingStrategy = new StandardShippingStrategy(); // Default
        this.packageState = new InTransitState(); // Default
        this.packageState.onEnterState(this); // Initial state message
    }

    public double getWeight() {
        return weight;
    }

    public void setShippingStrategy(ShippingStrategy shippingStrategy) {
        this.shippingStrategy = shippingStrategy;
        System.out.println("Shipping method changed to: " + shippingStrategy.getSpeedDescription());
        // If not yet delivered, print updated cost with new strategy
        if (!isDelivered()) {
            System.out.println("Updated Estimated Cost: " + String.format("%.2f", getCurrentCost()));
        }
    }

    public double getCurrentCost() {
        return this.shippingStrategy.calculateCost(this.weight);
    }

    public String getCurrentShippingSpeedDescription() {
        return this.shippingStrategy.getSpeedDescription();
    }

    public void setPackageState(PackageState packageState) {
        this.packageState = packageState;
        this.packageState.onEnterState(this); // Trigger action on state change
    }

    public boolean isDelivered() {
        return this.packageState.isDelivered();
    }

    public String getCurrentStatusDescription() {
        return this.packageState.getStatusDescription();
    }
}