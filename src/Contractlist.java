
import com.sun.javafx.geom.transform.BaseTransform;

import javax.swing.*;
import java.util.*;
import java.io.*;
public class Contractlist {
    private ArrayList <Contract> con = new ArrayList <Contract> ();

    public void addContract(Contract s){
        con.add(s);
    }

    public void listContracts() {
        for(int i = 0; i < con.size(); i++) {
            System.out.println(con.get(i));
        }
    }

    public void ListbyType(int type){
        for(int i = 0; i < con.size(); i++) {
            if (type == con.get(i).getType()) {
                System.out.println(con.get(i));
            }
        }
    }

    public int getType(int id) {
        int type = 1;
        for(int i = 0; i < con.size(); i++) {
            if (id == con.get(i).getId()) {
                type = con.get(i).getType();
            }
        }
        return type;
    }

    public String getServiceName(int id) {
        String name = "Econ Plus!";
        for(int i = 0; i < con.size(); i++) {
            if (id == con.get(i).getId()) {
                name = con.get(i).getService_name();
            }
        }

        return name;
    }

    public void setSMSConsumed (int id, int sms){
        for(int i = 0; i < con.size(); i++) {
            if (id == con.get(i).getId()) {
                con.get(i).setSMS(sms);
            }
        }
    }

    public int getSMSConsumed(int id){
        int sms = 0;
        for (int i=0; i<con.size(); i++) {
            if (id == con.get(i).getId()){
                sms = con.get(i).getSms_consumed();
            }
        }
        return sms;
    }

    public void clear() {
        con.clear();
    }

    public void setHomeCallDuration (int id, double dur){
        for(int i = 0; i < con.size(); i++) {
            if (id == con.get(i).getId()) {
                con.get(i).setHome_call_duration(dur);
            }
        }
    }

    public double getHomeCallDuration (int id){
        double dur = 0;
        for(int i = 0; i < con.size(); i++) {
            if (id == con.get(i).getId()) {
                dur = con.get(i).getHome_call_duration();
            }
        }
        return  dur;
    }

    public void setMobileCallDuration (int id, double dur){
        for(int i = 0; i < con.size(); i++) {
            if (id == con.get(i).getId()) {
                con.get(i).setMobile_call_duration(dur);
            }

        }
    }

    public double getMobileCallDuration (int id){
        double dur = 0;
        for(int i = 0; i < con.size(); i++) {

            if (id == con.get(i).getId()) {
                dur = con.get(i).getMobile_call_duration();
            }

        }
        return dur;
    }

    public void setMBConsumed (int id, double mb){
        for(int i = 0; i < con.size(); i++) {
            if (id == con.get(i).getId()) {
                con.get(i).setMb_comsumed(mb);
            }
        }
    }

    public double getMBConsumed (int id){
        double mb = 0;
        for(int i = 0; i < con.size(); i++) {
            if (id == con.get(i).getId()) {
                mb = con.get(i).getMb_comsumed();
            }
        }
        return mb;
    }
	
	public int getLastID() {
		return con.get(con.size()-1).getId();
	}
	
	public long getLastNumber() {
		return Long.parseLong(con.get(con.size()-1).getPhone());
	}

    public void savecontracts(String path) {

        String filename = path;
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter(filename);
            bw = new BufferedWriter(fw);
            bw.write("CONTRACT_LIST" + "\n" + "{" + "\n");

            for (int i=0; i<con.size(); i++) {

                bw.write("    CONTRACT" +
                        "\n" +"    {" +
                        "\n" + "        TYPE " + con.get(i).getType() +
                        "\n" + "        ID " + con.get(i).getId() +
                        "\n" + "        SERVICE_NAME " + con.get(i).getService_name() +
                        "\n" + "        NAME " + con.get(i).getName() +
                        "\n" + "        NUMBER " + con.get(i).getPhone() +
                        "\n" + "        DATE " + con.get(i).getDate() +
                        "\n" + "        WOP " + con.get(i).getWay_of_payment() +
                        "\n" + "        DATA_CONSUMED " + con.get(i).getMb_comsumed() +
                        "\n" + "        SMS_CONSUMED " + con.get(i).getSms_consumed() +
                        "\n" + "        TO_HOME_MINUTES_CONSUMED " + con.get(i).getHome_call_duration() +
                        "\n" + "        TO_MOBILE_MINUTES_CONSUMED " + con.get(i).getMobile_call_duration() +
                        "\n" + "    }" +
                        "\n");

            }
            bw.write("}");
            bw.flush();
            bw.close();

        } catch (IOException e) {

            System.err.println("Error writing file!");

        }





    }

    public String IDtoString (int id) {
        String return_statement = null;

        for (int i=0; i<con.size(); i++) {
            if (id == con.get(i).getId()) {
            return_statement = con.get(i).toString();
            }
        }

        return return_statement;
    }

    public String IDtoName (int id) {
        String return_statement = null;

        for (int i=0; i<con.size(); i++) {
            if (id == con.get(i).getId()) {
                return_statement = con.get(i).getName();
            }
        }

        return return_statement;
    }

    public DefaultListModel AddToListByType(int type) {
        DefaultListModel listModel = new DefaultListModel();
        for (int i = 0; i<con.size(); i++) {
            if (type == con.get(i).getType()) {
                listModel.addElement(con.get(i).getId());
            }
        }
        return listModel;
    }

    public DefaultListModel RestoreList() {
        DefaultListModel listModel = new DefaultListModel();
        for (int i = 0; i<con.size(); i++) {
            listModel.addElement(con.get(i).getId());
        }
        return listModel;
    }

}