package org.example;


public class DeliveredState implements PackageState {
    @Override
    public void onEnterState(Package pkgContext) {
        System.out.println("************************************");
        System.out.println("Status Update: Package has been DELIVERED!");
        System.out.println("Final Shipping Method: " + pkgContext.getCurrentShippingSpeedDescription());
        System.out.println("Final Cost: " + String.format("%.2f", pkgContext.getCurrentCost()));
        System.out.println("************************************");
    }

    @Override
    public boolean isDelivered() {
        return true;
    }

    @Override
    public String getStatusDescription() {
        return "delivered";
    }
}