package nl.vu.cs.softwaredesign.recommendations;

import nl.vu.cs.softwaredesign.households.*;
import nl.vu.cs.softwaredesign.appliances.*;

import java.util.PriorityQueue;

// Rules:
// give the top 5 worst CF appliances --> consider removing some of these appliances or reduce quantity
// recommend shutting down the worst appliance that is always-on or turning down modes
// recommend removing appliances that have high EM(x)
public class RecommendationsGenerator {
    private Household household;

    public RecommendationsGenerator(Household household) {
        this.household = household;
    }

    public PriorityQueue<Appliance> findTopContributingAppliances() {

    }

    public void generateRec() {

    }
}
