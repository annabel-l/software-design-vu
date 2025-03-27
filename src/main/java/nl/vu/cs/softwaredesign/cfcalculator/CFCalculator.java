package nl.vu.cs.softwaredesign.cfcalculator;
import java.time.Duration;
import java.util.*;

// think about imports on class diagram
import nl.vu.cs.softwaredesign.households.*;
import nl.vu.cs.softwaredesign.appliances.*;

public class CFCalculator {
    private Household household;
    private HouseholdManager manager;

    public CFCalculator(Household household, HouseholdManager manager) {
        this.household = household;
        this.manager = manager;
    }

    // consider appliances that aren't always-on

    public double calculateApplianceCF(Appliance appliance, double hours) {
        int power = appliance.getAvgPowerConsumption();
        double powerInKW = power / 1000.0;
        double energyConsumption = powerInKW * hours; // energy in kWh used by appliance between start and end time for its normal functioning
        double applianceCF = appliance.getEmbodiedEmissions() + (energyConsumption * household.getCarbonIntensity());
        manager.addApplianceCF(appliance, applianceCF);
        return applianceCF;
    }

    public double calculateCF() {

        //get duration between start and end time, convert to hours
        Duration duration = Duration.between(household.getStartTime(), household.getEndTime());
        long minutes = duration.toMinutes();
        double hours = minutes / 60.0;

        // go through each appliance, add up avgPowerConsumption for all appliances. then multiply after by the duration
        double totalCF = 0;

        for(Map.Entry<Appliance, Integer> entry : household.getAppliances().entrySet()) {
            Appliance app = entry.getKey();
            int quantity = entry.getValue(); // number of this appliance in the household
            double applianceCF = this.calculateApplianceCF(app, hours) * quantity;
            totalCF += applianceCF;
        }

        return totalCF;
    }
}
