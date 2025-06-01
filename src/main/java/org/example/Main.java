package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Package postalPackage;

        while (true) {
            try {
                System.out.print("Enter the weight of the package (kg): ");
                double weight = scanner.nextDouble();
                if (weight <= 0) {
                    System.out.println("Weight must be a positive number. Please try again.");
                    continue;
                }
                postalPackage = new Package(weight);
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value for weight.");
                scanner.next(); // Consume the invalid input
            }
        }

        while (!postalPackage.isDelivered()) {
            System.out.println("\n--- Package Details ---");
            System.out.println("Current Status: " + postalPackage.getCurrentStatusDescription());
            System.out.println("Current Shipping: " + postalPackage.getCurrentShippingSpeedDescription());
            System.out.println("Current Estimated Cost: " + String.format("%.2f", postalPackage.getCurrentCost()));
            System.out.println("-----------------------");

            // Get shipping method
            System.out.println("\nChoose shipping method:");
            System.out.println("1. Standard Shipping");
            System.out.println("2. Express Shipping");
            System.out.print("Enter choice (1 or 2): ");
            int shippingChoice = -1;
            try {
                shippingChoice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input for shipping choice. Defaulting to previous or initial.");
                scanner.next();
            }


            if (shippingChoice == 1) {
                postalPackage.setShippingStrategy(new StandardShippingStrategy());
            } else if (shippingChoice == 2) {
                postalPackage.setShippingStrategy(new ExpressShippingStrategy());
            } else {
                if (shippingChoice != -1) {
                    System.out.println("Invalid shipping choice. Keeping current method: " + postalPackage.getCurrentShippingSpeedDescription());
                }
            }

            if (!postalPackage.isDelivered()) {
                System.out.println("\nUpdate package status:");
                System.out.println("1. Mark as In-Transit (current if not yet delivered)");
                System.out.println("2. Mark as Delivered");
                System.out.print("Enter choice (1 or 2): ");
                int statusChoice = -1;
                try {
                    statusChoice = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input for status choice. Status remains unchanged.");
                    scanner.next(); // consume invalid input
                }


                if (statusChoice == 1) {
                    if (!(postalPackage.getCurrentStatusDescription().equals("in-transit"))) {
                        postalPackage.setPackageState(new InTransitState());
                    } else {
                        System.out.println("Package is already in-transit. No status change.");
                    }
                } else if (statusChoice == 2) {
                    postalPackage.setPackageState(new DeliveredState());
                } else {
                    if (statusChoice != -1) {
                        System.out.println("Invalid status choice. Status remains: " + postalPackage.getCurrentStatusDescription());
                    }
                }
            }
        }
        System.out.println("\nProgram terminated as package is delivered.");
        scanner.close();
    }
}