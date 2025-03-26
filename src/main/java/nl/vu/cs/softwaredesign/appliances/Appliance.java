package nl.vu.cs.softwaredesign.appliances;

public class Appliance {
    public enum UsageMode {
        ALWAYS_ON, ONE_OFF, DAYTIME
    }

    private String applianceName;
    private int avgPowerConsumption; // average power consumption of the appliance per hour in Watts
    private UsageMode usageMode; //ENUM
    private String energyLabel;
    private int embodiedEmissions; // total CO2 emitted for producing the appliance
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

    public void setUsageMode(UsageMode usageMode) {
        this.usageMode = usageMode;
    }

    public String getEnergyLabel() {
        return energyLabel;
    }

    public void setEnergyLabel(String energyLabel) {
        this.energyLabel = energyLabel;
    }

    public int getEmbodiedEmissions() {
        return embodiedEmissions;
    }

    public void setEmbodiedEmissions(int embodiedEmissions) {
        this.embodiedEmissions = embodiedEmissions;
    }
}
