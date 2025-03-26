package nl.vu.cs.softwaredesign.households;

import java.util.*;
import nl.vu.cs.softwaredesign.appliances.*;
public class WhatIfExplorer {
    public HouseholdManager manager;
    public Household household;
    public WhatIfExplorer(HouseholdManager manager, Household household) {
        this.manager = manager;
        this.household = household;
    }

    public void explore(Scanner scanner, HouseholdManager manager, ApplianceRepo appliancesRepo) {

        boolean exploring = true;

        while(exploring) {
            System.out.println("What would you like to do? ");
            System.out.println("1. Add an appliance");
            System.out.println("2. Remove an appliance");
            System.out.println("3. View current appliances");
            System.out.println("4. Display Carbon Footprint");
            System.out.println("5. Exit what-if explorer");
            System.out.println("Enter your choice: ");

            String choice = scanner.nextLine();

            switch(choice) {
                case "1":
                    //print appliances
                    manager.addAppliance(scanner, appliancesRepo, household);
                    break;
                case "2":
                    //print appliances
                    manager.removeAppliance(scanner, household);
                    break;
                case "3":
                    //print appliances
                    manager.printAppliancesAndQuantities();
                    break;
                case "4":
                    //print(household.getCarbonFootprint());
                case "5":
                    exploring = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter an integer 1-5.");
            }
        }
    }
}
