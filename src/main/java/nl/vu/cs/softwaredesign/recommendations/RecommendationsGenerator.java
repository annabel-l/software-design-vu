package nl.vu.cs.softwaredesign.recommendations;

import nl.vu.cs.softwaredesign.households.*;
import nl.vu.cs.softwaredesign.appliances.*;
import java.util.*;

public class RecommendationsGenerator {
    private Household household;

    public RecommendationsGenerator(Household household) {
        this.household = household;
    }

    public List<Map.Entry<Appliance, Double>> getTop5Appliances() {
        PriorityQueue<Map.Entry<Appliance, Double>> maxHeap = new PriorityQueue<>((a, b) -> Double.compare(b.getValue(), a.getValue()));

        Iterator<Map.Entry<Appliance, Double>> cfIterator = household.carbonFootprintIterator();
        while (cfIterator.hasNext()) {
            maxHeap.add(cfIterator.next());
        }

        List<Map.Entry<Appliance, Double>> top5 = new ArrayList<>();
        for (int i = 0; i < 5 && !maxHeap.isEmpty(); i++) {
            top5.add(maxHeap.poll());
        }
        return top5;
    }


    // Rules:
// give the top 5 worst CF appliances --> consider removing some of these appliances or reduce quantity
// recommend shutting down the worst appliance that is always-on or turning down modes
// recommend removing appliances that have high EM(x) > 600 kg CO2

    public Appliance worstAlwaysOn() {
        double highestCF = 0.0;
        Appliance worst = null;
        for (Map.Entry<Appliance, Double> entry : household.getAppliancesAndCF().entrySet()) {
            Appliance appliance = entry.getKey();
            if (appliance.getUsageMode() == Appliance.UsageMode.ALWAYS_ON && entry.getValue() > highestCF) {
                worst = appliance;
                highestCF = entry.getValue();
            }
        }
        return worst;
    }

    public List<Appliance> highEM() {
        List<Appliance> highEMAppliances = new ArrayList<>();
        for (Appliance appliance : household.getAppliancesAndCF().keySet()) {
            if (appliance.getEmbodiedEmissions() > 50) {
                highEMAppliances.add(appliance);
            }
        }
        return highEMAppliances;
    }
}
