package org.example;

public class InTransitState implements PackageState {
    @Override
    public void onEnterState(Package pkgContext) {
        System.out.println("------------------------------------");
        System.out.println("Status Update: Package is IN-TRANSIT.");
        System.out.println("Shipping via: " + pkgContext.getCurrentShippingSpeedDescription());
        System.out.println("Current Estimated Cost: " + String.format("%.2f", pkgContext.getCurrentCost()));
        System.out.println("------------------------------------");
    }

    @Override
    public boolean isDelivered() {
        return false;
    }

    @Override
    public String getStatusDescription() {
        return "in-transit";
    }
}