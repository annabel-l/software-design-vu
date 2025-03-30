package nl.vu.cs.softwaredesign.households;

import java.time.LocalDateTime;
import java.util.*;
import nl.vu.cs.softwaredesign.appliances.*;

public class Household implements Iterable<Map.Entry<Appliance, Integer>> {
    private String name;
    private String region;
    private int carbonIntensity;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Map<Appliance, Integer> appliances; // key: appliance, value: quantity
    private Map<Appliance, Double> appliancesAndCF; // key: appliance, value: CF

    public Household(String name, String region, int carbonIntensity, LocalDateTime startTime, LocalDateTime endTime) {
        this.name = name;
        this.region = region;
        this.appliances = new HashMap<>();
        this.appliancesAndCF = new HashMap<>();
        this.carbonIntensity = carbonIntensity;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Household(String name, String region, int carbonIntensity, Map<Appliance, Integer> appliances, Map<Appliance, Double> appliancesAndCF, LocalDateTime startTime, LocalDateTime endTime) {
        this.name = name;
        this.region = region;
        this.appliances = new HashMap<>(appliances);
        this.appliancesAndCF = new HashMap<>(appliancesAndCF);
        this.carbonIntensity = carbonIntensity;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Map<Appliance, Integer> getAppliances() {
        return this.appliances;
    }

    public Map<Appliance, Double> getAppliancesAndCF() { return this.appliancesAndCF; }

    @Override
    public Iterator<Map.Entry<Appliance, Integer>> iterator() {
        return appliances.entrySet().iterator();
    }

    public Iterator<Map.Entry<Appliance, Double>> carbonFootprintIterator() {
        return appliancesAndCF.entrySet().iterator();
    }

    public String getName() { return this.name; }

    public String getRegion() { return this.region; }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public int getCarbonIntensity() {
        return this.carbonIntensity;
    }

    public void setCarbonIntensity(int carbonIntensity) {
        this.carbonIntensity = carbonIntensity;
    }

}
