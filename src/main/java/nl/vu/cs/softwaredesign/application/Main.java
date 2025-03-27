package nl.vu.cs.softwaredesign.application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import nl.vu.cs.softwaredesign.appliances.*;
import nl.vu.cs.softwaredesign.cfcalculator.CFCalculator;
import nl.vu.cs.softwaredesign.households.*;
import nl.vu.cs.softwaredesign.recommendations.RecommendationsGenerator;
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
        String inputCarbonIntensity = scanner.nextLine();
        int carbonIntensity = Integer.parseInt(inputCarbonIntensity); // THIS COULD THROW AN EXCEPTION

        System.out.println("Enter START time for window being considered. Format: yyyy-MM-dd HH:mm ");
        String inputStartTime = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startTime = LocalDateTime.parse(inputStartTime, formatter);

        System.out.println("Enter END time for window being considered. Format: yyyy-MM-dd HH:mm ");
        String inputEndTime = scanner.nextLine();
        LocalDateTime endTime = LocalDateTime.parse(inputEndTime, formatter);

        Household household = new Household(householdName, householdRegion, carbonIntensity, startTime, endTime);
        HouseholdManager manager = new HouseholdManager(household);

        //User adds appliances to household
//        System.out.println("Possible Appliances: ");
//        int applianceIndex = 1;
//        for(Appliance app : repo){ //put this into AppliancesRepo
//            System.out.println(applianceIndex + ". " + app.getApplianceName());
//            applianceIndex++;
//        }
        displayAppliances(repo);

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
            //addAppliance already handles if that appliance has already been added to the household
            //if appliance already exists in household, it just increases the quantity by the specified amount
            Appliance newAppliance = repo.getApplianceByIndex(index-1);
            manager.addAppliance(newAppliance, quantity);

            //if appliance is used hourly, ask what hours
            if (newAppliance.getUsageMode() == Appliance.UsageMode.ONE_OFF) {
                System.out.println("This appliance is used during a specific time range during the day.");

                System.out.println("What hour from 0 to 24 does this appliance start being in use?");
                String applianceStart = scanner.nextLine();
                int applianceStartHour = Integer.parseInt(applianceStart);
                newAppliance.setStartTime(applianceStartHour);

                System.out.println("What hour from 0 to 24 does the appliance stop being in use?");
                String applianceEnd = scanner.nextLine();
                int applianceEndHour = Integer.parseInt(applianceStart);
                newAppliance.setEndTime(applianceEndHour);
            }
        }

        //Display current household
        System.out.println("Here is your current household: ");
        for(Map.Entry<Appliance, Integer> entry : newHousehold){
            Appliance appliance = entry.getKey();
            int quantity = entry.getValue();
            System.out.println(appliance +  " , Quantity: " + quantity);
        }

        //Calculate carbon footprint
        CFCalculator calculator = new CFCalculator(household, manager);
        double currHouseholdCF = calculator.calculateCF();
        System.out.println("Carbon footprint for " + household.getName() + ": " + currHouseholdCF);

        //Generate report
        RecommendationsGenerator recommendationsGenerator = new RecommendationsGenerator(household);
        List<Map.Entry<Appliance, Double>> top5 = recommendationsGenerator.getTop5Appliances();
        System.out.println("Top 5 appliances with the highest carbon footprint: ");
        for (Map.Entry<Appliance, Double> entry: top5) {
            System.out.println(entry.getKey().getApplianceName() + "-" + entry.getValue() + "kg CO2");
        }
        System.out.println("Consider reducing or removing these appliances due to high carbon footprint.");


        //Generate recommendations

        Appliance worstAlwaysOn = recommendationsGenerator.worstAlwaysOn();
        if (worstAlwaysOn != null) {
            System.out.println("The worst always on appliance is " + worstAlwaysOn.getApplianceName() + ".");
            System.out.println("Consider shutting down or reducing the usage of this always-on appliance.");
        }
        else {
            System.out.println("No always-on appliances with high carbon footprints found.");
        }

        List<Appliance> highEMAppliances = recommendationsGenerator.highEM();
        if (!highEMAppliances.isEmpty()) {
            System.out.println("The following appliances have embodied emissions (EM) of over 600 kg CO2.");
            for(Appliance appliance : highEMAppliances) {
                System.out.println(appliance.getApplianceName() + " - EM: " + appliance.getEmbodiedEmissions() + " kg CO2");
            }
            System.out.println("Consider replacing these appliances with lower EM appliances.");
        }
        else {
            System.out.println("No appliances with high embodied emissions over 600 kg CO2 found.");
        }

        System.out.println("Following these recommendations can help you lower your household's carbon footpirnt.");
        System.out.println("Would you like to explore these recommendations in a what-if scenario? Type Y or N.");

        char choice;
        while(true){
            String whatIfDecisionInput = scanner.nextLine();
            String whatIfDecision = whatIfDecisionInput.trim().toUpperCase();
            if (whatIfDecision.equals("Y") || whatIfDecision.equals("N")) {
                choice = whatIfDecision.charAt(0);
                break;
            }
            else{
                System.out.println("Invalid input. Please enter Y or N.");
            }
        }

        if (choice == 'Y') { //User wants to explore what-if scenario
            //User explores what-if scenarios
            WhatIfExplorer whatIf = new WhatIfExplorer(household);
            CFCalculator newCalculator = new CFCalculator(whatIf.useNewHousehold(), manager);
            boolean exploring = true;
            while(exploring) {
                System.out.println("-----------------------------------------------");
                System.out.println("What would you like to do? ");
                System.out.println("1. Add an appliance");
                System.out.println("2. Remove an appliance");
                System.out.println("3. View current appliances in the household");
                System.out.println("4. Edit household start time");
                System.out.println("5. Edit household end time");
                System.out.println("6. Edit household carbon intensity");
                System.out.println("7. View old household carbon footprint");
                System.out.println("8. Update new household carbon footprint");
                System.out.println("9. Save old household");
                System.out.println("10. Save new household");
                System.out.println("Enter your choice: ");
                System.out.println("-----------------------------------------------");
                String line = scanner.nextLine();
                int pick = Integer.parseInt(line);
                switch(pick) {
                    case 1:
                        displayAppliances(repo);
                        System.out.println("Enter the index of the appliance to add");
                        String chosenApplianceIndex = scanner.nextLine();
                        int index = Integer.parseInt(chosenApplianceIndex);
                        while(!whatIf.validPick(repo, index)) {
                            System.out.println("Invalid index, please try again");
                            chosenApplianceIndex = scanner.nextLine();
                            index = Integer.parseInt(chosenApplianceIndex);
                        }
                        Appliance chosenAppliance = repo.getApplianceByIndex(index);
                        System.out.println("How many of this appliance would you like to add");
                        String chosenQuantity = scanner.nextLine();
                        int quantity = Integer.parseInt(chosenQuantity);
                        while(quantity < 0) {
                            System.out.println("Invalid quantity, please try again");
                            chosenQuantity = scanner.nextLine();
                            quantity = Integer.parseInt(chosenQuantity);
                        }
                        whatIf.editHouseholdAddition(chosenAppliance, quantity);
                        break;
                    case 2:
                        displayAppliances(repo);
                        System.out.println("Enter the index of the appliance to remove");
                        String removedApplianceIndex = scanner.nextLine();
                        int removedIndex = Integer.parseInt(removedApplianceIndex);
                        while(!whatIf.validPick(repo, removedIndex)) {
                            System.out.println("Invalid index, please try again");
                            removedApplianceIndex = scanner.nextLine();
                            removedIndex = Integer.parseInt(removedApplianceIndex);
                        }
                        Appliance removedAppliance = repo.getApplianceByIndex(removedIndex);
                        whatIf.editHouseholdDeletion(removedAppliance);
                        break;
                    case 3:
                        displayHousehold(whatIf.useNewHousehold());
                        break;
                    case 4: //edit start time
                        System.out.println("Enter new START time for window being considered. Format: yyyy-MM-dd HH:mm ");
                        String newInputStartTime = scanner.nextLine();
                        LocalDateTime newStartTime = LocalDateTime.parse(newInputStartTime, formatter);
                        whatIf.editHouseholdStartTime(newStartTime);
                        break;
                    case 5: //edit end time
                        System.out.println("Enter new END time for window being considered. Format: yyyy-MM-dd HH:mm ");
                        String newInputEndTime = scanner.nextLine();
                        LocalDateTime newEndTime = LocalDateTime.parse(newInputEndTime, formatter);
                        whatIf.editHouseholdEndTime(newEndTime); //verify that start/end times are valid?
                        break;
                    case 6://edit carbon intensity
                        System.out.println("Enter new carbon intensity for region: ");
                        String newInputCarbonIntensity = scanner.nextLine();
                        int newCarbonIntensity = Integer.parseInt(newInputCarbonIntensity);
                        whatIf.editHouseholdCarbonIntensity(newCarbonIntensity);
                        break;
                    case 7:
                        double oldHouseholdCF = calculator.calculateCF();
                        System.out.println("Carbon footprint for old household : " + currHouseholdCF);
                        break;
                    case 8:
                        //
                        double newHouseholdCF = newCalculator.calculateCF();
                        System.out.println("Carbon footprint for new household : " + newHouseholdCF);
                        break;
                    case 9:
                        household = whatIf.useOldHousehold();
                        exploring = false;
                        break;
                    case 10:
                        household = whatIf.useNewHousehold();
                        exploring = false;
                        break;
                }
            }

        }
        else{
            System.out.println("Thank you for using our carbon footprint calculator!");
        }



        //when exploring what-if scenarios, what-if scenario generates a copy of current household that user modifies

        //if user is done modifying what-if, generate CF here
        //display CF here too
        //user can keep going back and creating new what-if scenarios until done
    }
}