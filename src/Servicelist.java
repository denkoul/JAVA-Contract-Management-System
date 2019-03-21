import javax.swing.*;
import java.util.*;
public class Servicelist {
    private ArrayList <Service> services = new ArrayList <Service> ();
    public void addService(Service s){
            services.add(s);
        }
    public void listServices() {
        for(int i = 0; i < services.size(); i++) {
            System.out.println(" " + (i+1) + " - " + services.get(i));
        }

    }
    public int getSize(){
        return services.size();
    }

    public String getName(int j) {
        String return_name = null;
        for (int i=0; i<services.size(); i++) {
            if (i == j) {
                return_name = services.get(i).getName();
            }
        }
        return return_name;
    }

    public double getFixed(String service_name){
        double mb =0;
        for (int i=0; i<services.size(); i++) {
            if (service_name.equals(services.get(i).getName())){
                mb = services.get(i).getFixed();
            }
        }
        return mb;
    }

    public double getCut(String service_name){
        double cut =0;
        for (int i=0; i<services.size(); i++) {
            if (service_name.equals(services.get(i).getName())){
                cut = services.get(i).getCut();
            }
        }
        return cut;
    }

    public String TypeToName (int type) {
        if (type == 1) {
            return  "Mobile Internet";
        } else if (type == 2) {
            return "Contract";
        } else {
            return "Card Contract";
        }
    }

    public DefaultListModel ListByType(int type) {
        DefaultListModel listModel = new DefaultListModel();
        for (int i = 0; i < services.size(); i++) {
            if (type == services.get(i).getType()) {
                listModel.addElement(services.get(i).getName());
            }
        }
        return listModel;
    }

    public String NameToString(String service_name) {
        String return_name = null;
        for (int i = 0; i<services.size(); i++) {
            if (service_name.equals(services.get(i).getName())) {
                return_name = services.get(i).toString();
            }
        }
        return return_name;
    }


    public void clear() {
        services.clear();
    }


    public double getFreeMB(String service_name){
        double mb =0;
        for (int i=0; i<services.size(); i++){
            if (service_name.equals(services.get(i).getName())) {
                mb = services.get(i).getData();
            }
        }
    return mb;
    }

    public double getChargePerMB(String service_name){
        double charge =0;
        for (int i=0; i<services.size(); i++){
            if (service_name.equals(services.get(i).getName())) {
                charge = services.get(i).getCharge();
            }
        }
        return charge;
    }

    public double getFreeMin(String service_name){
        double mb =0;
        for (int i=0; i<services.size(); i++){
            if (service_name.equals(services.get(i).getName())) {
                mb = services.get(i).getFree_min();
            }
        }
        return mb;
    }

    public double getChargePerSMS(String service_name){
        double mb =0;
        for (int i=0; i<services.size(); i++){
            if (service_name.equals(services.get(i).getName())) {
                mb = services.get(i).getSms_charge();
            }
        }
        return mb;
    }

    public double getChargePerMin(String service_name){
        double mb =0;
        for (int i=0; i<services.size(); i++){
            if (service_name.equals(services.get(i).getName())) {
                mb = services.get(i).getMin_charge();
            }
        }
        return mb;
    }

    public double getCardRest(String service_name){
        double rest =0;
        for (int i=0; i<services.size(); i++){
            if (service_name.equals(services.get(i).getName())) {
                rest = services.get(i).getCardRest();
            }
        }
        return rest;
    }

    public int ServiceToType (String service_name) {
        int type = 1;
        for (int i = 0; i<services.size(); i++) {
            if (service_name.equals(services.get(i).getName())) {
                type = services.get(i).getType();
            }
        }
        return type;
    }

    public int getFreeSMS(String service_name){
        int mb =0;
        for (int i=0; i<services.size(); i++){
            if (service_name.equals(services.get(i).getName())) {
                mb = services.get(i).getFree_sms();
            }
        }
        return mb;
    }
}
