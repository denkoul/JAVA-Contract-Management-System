public class MoPhone extends Service {
    private int free_sms;
    private double free_min, sms_charge, min_charge;
    //constructor
    public MoPhone (int type, double cut, String name, double fixed, int free_sms, double sms_charge, double free_min, double min_charge) {
        super(type, cut, name, fixed);
        this.free_sms = free_sms;
        this.min_charge = min_charge;
        this.free_min = free_min;
        this.sms_charge = sms_charge;
    }

    public int getFree_sms() {
        return free_sms;
    }

    public double getFree_min() {
        return free_min;
    }

    public double getData() {return 0;}

    public double getCharge() {
        return 0;
    }

    public double getSms_charge() {
        return sms_charge;
    }

    public double getMin_charge() {
        return min_charge;
    }

    public double getCardRest() { return 0;}

    public String toString(){
		return super.toString() + "\nNumber of free SMS: " + this.free_sms + "\nNumber of free minutes: " + this.free_min + "\nCharge per additional SMS: " + sms_charge + "\nCost per additional minute: " + min_charge;
	}

}
