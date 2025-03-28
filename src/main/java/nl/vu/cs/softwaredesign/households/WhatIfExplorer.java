package nl.vu.cs.softwaredesign.households;

import java.util.*;
import nl.vu.cs.softwaredesign.appliances.*;
import java.time.LocalDateTime;

public class WhatIfExplorer {
    private HouseholdManager manager;
    private Household oldHousehold;
    private Household newHousehold;
    public WhatIfExplorer(Household household) {
        this.newHousehold = new Household(household.getName(), household.getRegion(), household.getCarbonIntensity(), household.getAppliances(), household.getStartTime(), household.getEndTime());
        this.oldHousehold = household;
        this.manager = new HouseholdManager(newHousehold);
    }



    public void editHouseholdAddition(Appliance appliance, int quantity) {
        this.manager.addAppliance(appliance, quantity);
    }

    public void editHouseholdDeletion(Appliance appliance) {
        this.manager.removeAppliance(appliance);
    }

    public void editHouseholdStartTime(LocalDateTime startTime) {
        this.manager.changeStartTime(startTime);
    }

    public void editHouseholdEndTime(LocalDateTime endTime) {
        this.manager.changeEndTime(endTime);
    }

    public void editHouseholdCarbonIntensity(int carbonIntensity) {
        this.manager.changeCarbonIntensity(carbonIntensity);
    }

    public Household useOldHousehold() {
        return this.oldHousehold;
    }

    public Household useNewHousehold() {
        return this.newHousehold;
    }

    public boolean validPick(ApplianceRepo repo, int index) {
        return (0 <= (index - 1)) && ((index - 1) <= repo.applianceRepoSize());
    }


}
