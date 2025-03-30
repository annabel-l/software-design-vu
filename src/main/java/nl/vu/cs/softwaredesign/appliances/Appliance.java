package nl.vu.cs.softwaredesign.appliances;

public class Appliance {
    public enum UsageMode {
        ALWAYS_ON, ONE_OFF, DAYTIME
    }

    private String applianceName;
    private int avgPowerConsumption;
    private UsageMode usageMode;
    private String energyLabel;
    private int embodiedEmissions;
    private int startTime; //daily start hour from 0-24
    private int endTime; //daily end hour from 0-24

    public Appliance(String applianceName, int avgPowerConsumption, UsageMode usageMode, String energyLabel, int embodiedEmissions) {
        this.applianceName = applianceName;
        this.avgPowerConsumption = avgPowerConsumption;
        this.usageMode = usageMode;
        this.energyLabel = energyLabel;
        this.embodiedEmissions = embodiedEmissions;

    }

    public String getApplianceName() {
        return applianceName;
    }

    public int getAvgPowerConsumption() {
        return avgPowerConsumption;
    }

    public void setAvgPowerConsumption(int avgPowerConsumption) {
        this.avgPowerConsumption = avgPowerConsumption;
    }

    public UsageMode getUsageMode() {
        return usageMode;
    }

    public String getEnergyLabel() {
        return energyLabel;
    }

    public int getEmbodiedEmissions() {
        return embodiedEmissions;
    }

    public int getStartTime() { return startTime; }

    public void setStartTime(int startTime) { this.startTime = startTime; }

    public int getEndTime() { return endTime; }

    public void setEndTime(int endTime) { this.endTime = endTime; }
}
