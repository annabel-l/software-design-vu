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

    public void editHouseholdAddition(Appliance appliance, int quantity) {
        this.manager.addAppliance(appliance, quantity);
    }
}
