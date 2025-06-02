package org.example;

public class Package {
    private static Package instance;

    private double weight;
    private ShippingStrategy shippingStrategy;
    private PackageState packageState;

    Package(double weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be positive.");
        }
        this.weight = weight;
        this.shippingStrategy = new StandardShippingStrategy();

        this.packageState = new InTransitState();

        this.packageState.onEnterState(this);
    }

    public static synchronized Package getInstance(double weight) {
        if (instance == null) {
            instance = new Package(weight);
        }

        return instance;
    }


    static void resetInstanceForTesting() {
        instance = null;
    }

    public double getWeight() {
        return weight;
    }

    public void setShippingStrategy(ShippingStrategy shippingStrategy) {
        this.shippingStrategy = shippingStrategy;
        System.out.println("Shipping method changed to: " + shippingStrategy.getSpeedDescription());
        if (!isDelivered()) {
            System.out.println("Updated Estimated Cost: " + String.format("%.2f", getCurrentCost()));
        }
    }

    public double getCurrentCost() {
        if (this.shippingStrategy == null) {
            this.shippingStrategy = new StandardShippingStrategy();
        }
        return this.shippingStrategy.calculateCost(this.weight);
    }

    public String getCurrentShippingSpeedDescription() {
        if (this.shippingStrategy == null) {
            this.shippingStrategy = new StandardShippingStrategy();
        }
        return this.shippingStrategy.getSpeedDescription();
    }

    public void setPackageState(PackageState packageState) {
        this.packageState = packageState;
        this.packageState.onEnterState(this);
    }

    public boolean isDelivered() {
        if (this.packageState == null) {
            this.packageState = new InTransitState();
            this.packageState.onEnterState(this);
        }
        return this.packageState.isDelivered();
    }

    public String getCurrentStatusDescription() {
        if (this.packageState == null) {
            this.packageState = new InTransitState();
            this.packageState.onEnterState(this);
        }
        return this.packageState.getStatusDescription();
    }
}