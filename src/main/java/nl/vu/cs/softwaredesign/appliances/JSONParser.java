package nl.vu.cs.softwaredesign.appliances;

import java.io.File;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONParser {
    public ApplianceRepo jsonParser(String filePath) {
        List<Appliance> allAppliances = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootArray = mapper.readTree(new File(filePath));

            // go through every object in the JSON array
            for (JsonNode appliance : rootArray) {

                JsonNode nameNode = appliance.get("name");
                JsonNode powerNode = appliance.get("avgPowerConsumption");
                JsonNode labelNode = appliance.get("energyLabel");
                JsonNode modeNode = appliance.get("usageMode");
                JsonNode embodiedEmissionsNode = appliance.get("embodiedEmissions");

                // check for missing fields or wrong types
                if (nameNode == null || !nameNode.isTextual() ||
                        powerNode == null || !powerNode.isInt() ||
                        labelNode == null || !labelNode.isTextual() ||
                        modeNode == null || !modeNode.isTextual() ||
                        embodiedEmissionsNode == null || !embodiedEmissionsNode.isInt()) {
                    System.out.println("Invalid appliance format. Quitting.");
                    System.exit(1);
                }

                String name = nameNode.asText();
                int power = powerNode.asInt();
                String energyLabel = labelNode.asText();
                Appliance.UsageMode usageMode;
                try {
                    usageMode = Appliance.UsageMode.valueOf(modeNode.asText().toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid usage mode: " + modeNode.asText() + ". Quitting.");
                    System.exit(1);
                    return null; //bad practice?
                }

                int embodiedEmissions = embodiedEmissionsNode.asInt();

                Appliance newApp = new Appliance(name, power, usageMode, energyLabel, embodiedEmissions);
                allAppliances.add(newApp);
            }

        } catch(Exception e) {
            // for bigger issues like if the file is unreadable
            System.out.println("Error parsing JSON file");
            System.exit(1); // quits program, returns 1 to the OS
        }

        // Singleton method ensures only one instance of ApplianceRepo
        return ApplianceRepo.getInstance(allAppliances);
    }
}
