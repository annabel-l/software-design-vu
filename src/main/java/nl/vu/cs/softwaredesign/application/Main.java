package nl.vu.cs.softwaredesign.application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import nl.vu.cs.softwaredesign.appliances.*;
import nl.vu.cs.softwaredesign.households.*;
/*
application/UI
- Main.java
- Menu?

appliances
- Appliance
- ApplianceRepo
- JSONParser

households
- Household
- HouseholdManager
- WhatIfExplorer

recommendations
- RecommendationsGenerator

cfcalculator
- CFCalculator.java

*/

public class Main {
    public static void main (String[] args){
        //System.out.println("Welcome to Software Design!");
        Scanner scanner = new Scanner(System.in);

        //JSON file parser
        System.out.println("Enter JSON file path");
        String filePath = scanner.nextLine();
        JSONParser test = new JSONParser();
        ApplianceRepo repo = test.jsonParser(filePath);
        List<Appliance> apps = repo.getAllAppliances();
        for(Appliance app : apps) { //put this into AppliancesRepo
            System.out.println(app.getApplianceName() + " - " +
                    app.getAvgPowerConsumption() + "W - " +
                    app.getUsageMode() + " - Energy label: " +
                    app.getEnergyLabel());
        }

        // put all steps in methods for readability --> maybe in another class

        //User creates a household
        System.out.println("Enter Household Name: ");
        String householdName = scanner.nextLine();
        System.out.println("Enter Household Region: ");
        String householdRegion = scanner.nextLine();

        System.out.println("Enter Carbon Intensity for Region: ");
        String inputCarbonIntesity = scanner.nextLine();
        int carbonIntensity = Integer.parseInt(inputCarbonIntesity); // THIS COULD THROW AN EXCEPTION

        System.out.println("Enter START time for window being considered. Format: yyyy-MM-dd HH:mm ");
        String inputStartTime = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startTime = LocalDateTime.parse(inputStartTime, formatter);

        System.out.println("Enter END time for window being considered. Format: yyyy-MM-dd HH:mm ");
        String inputEndTime = scanner.nextLine();
        LocalDateTime endTime = LocalDateTime.parse(inputEndTime, formatter);

        Household newHousehold = new Household(householdName, householdRegion, carbonIntensity, startTime, endTime);
        HouseholdManager manager = new HouseholdManager(newHousehold);

        //User adds appliances to household
        System.out.println("Possible Appliances: ");
        for(Appliance app : repo){ //put this into AppliancesRepo
            System.out.println(app.getApplianceName());
        }

        while(true) {
            System.out.println("Enter the index of the appliance to add (or -1 to finish): ");
            String givenIndex = scanner.nextLine();
            int index = Integer.parseInt(givenIndex);

            // user done adding appliances
            if (index == -1) {
                break;
            }

            // if invalid index
            while (index < 0 || index >= repo.applianceRepoSize()) {
                System.out.println("Error: invalid input. Please enter a valid index:");
                givenIndex = scanner.nextLine();
                index = Integer.parseInt(givenIndex);
            }

            //quantity
            System.out.println("Enter the quantity of this appliance to add: ");
            String quantityInput = scanner.nextLine();
            int quantity = Integer.parseInt(quantityInput);

            //if invalid quantity
            while (quantity < 0) {
                System.out.println("Error: invalid quantity. Please enter a number greater than or equal to 0: ");
                quantityInput = scanner.nextLine();
                quantity = Integer.parseInt(quantityInput);
            }

            //add appliance to the household using the household manager
            Appliance newAppliance = repo.getApplianceByIndex(index);
            manager.addAppliance(newAppliance, quantity);
        }

        //Display current household
        System.out.println("Here is your current household: ");
        for(Map.Entry<Appliance, Integer> entry : newHousehold){
            Appliance appliance = entry.getKey();
            int quantity = entry.getValue();
            System.out.println(appliance +  " , Quantity: " + quantity);
        }

        //Calculate carbon footprint



        //Generate report + recommendations


        //User explores what-if scenarios
        //when explroign what-if scenarios, what-if scenario generates a copy of current household that user modifies
        //if user is done modifying what-if, generate CF here
        //display CF here too
        //user can keep going back and creating new what-if scenarios until done
    }
}