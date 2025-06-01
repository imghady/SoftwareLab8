package org.example;

public interface PackageState {
    void onEnterState(Package pkgContext);

    boolean isDelivered();

    String getStatusDescription();
}