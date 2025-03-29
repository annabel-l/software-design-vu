package nl.vu.cs.softwaredesign.appliances;

import java.util.List;
import java.util.Iterator;

//public class ApplianceRepo implements Iterable<Appliance>{
//    private List<Appliance> allAppliances;
//    public ApplianceRepo(List<Appliance> allAppliances) {
//        this.allAppliances = allAppliances;
//    }
//
//    public List<Appliance> getAllAppliances() {
//        return allAppliances;
//    }
//
//    @Override
//    public Iterator<Appliance> iterator() {
//        return allAppliances.iterator();
//    }
//
//    public int applianceRepoSize(){
//        return allAppliances.size();
//    }
//
//    public Appliance getApplianceByIndex(int index){
//        return allAppliances.get(index);
//    }
//}

public class ApplianceRepo implements Iterable<Appliance> {
    private List<Appliance> allAppliances;
    private static ApplianceRepo instance;
    private ApplianceRepo(List<Appliance> allAppliances) {
        this.allAppliances = allAppliances;
    }

    public static ApplianceRepo getInstance(List<Appliance> appliances) {
        if(instance == null) {
            return new ApplianceRepo(appliances);
        }
        return instance;

    }

    public List<Appliance> getAllAppliances() {
        return allAppliances;
    }

    @Override
    public Iterator<Appliance> iterator() {
        return allAppliances.iterator();
    }

    public int applianceRepoSize(){
        return allAppliances.size();
    }

    public Appliance getApplianceByIndex(int index){
        return allAppliances.get(index);
    }
}
