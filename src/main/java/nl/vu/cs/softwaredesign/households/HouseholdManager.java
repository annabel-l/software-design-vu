package nl.vu.cs.softwaredesign.households;

import nl.vu.cs.softwaredesign.appliances.*;
import java.time.LocalDateTime;
import java.util.*;

public class HouseholdManager {
    private Household household;

    public HouseholdManager(Household household) {
        this.household = household;
    }
    public void addAppliance(Appliance appliance, int quantity){
        Map<Appliance, Integer> appliancesAndQuantities = household.getAppliances();
        if(appliancesAndQuantities.containsKey(appliance)) { //already exists in household
            household.getAppliances().put(appliance, appliancesAndQuantities.get(appliance) + quantity);
        }
        else{
            appliancesAndQuantities.put(appliance, quantity);
        }
    }

    public void removeAppliance(Appliance appliance) {
        household.getAppliances().remove(appliance);
        household.getAppliancesAndCF().remove(appliance);
    }

    public void changeStartTime(LocalDateTime time){
        household.setStartTime(time);
    }

    public void changeEndTime(LocalDateTime time){
        household.setEndTime(time);
    }

    public void changeCarbonIntensity(int carbonIntensity){
        household.setCarbonIntensity(carbonIntensity);
    }

    public void addApplianceCF(Appliance appliance, double cf){
        household.getAppliancesAndCF().put(appliance, cf);
    }

}
