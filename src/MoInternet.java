public class MoInternet extends Service {
    double data;
    double charge;
    //constructor
    public MoInternet(int type, double cut, String name, double fixed,  double data, double charge) {
        super(type, cut, name, fixed);
        this.data = data;
        this.charge = charge;
    }

    public double getData() {
        return data;
    }

    public double getCharge() {
        return charge;
    }

    public double getFree_min() {return 0;};
    public double getSms_charge() {return 0;};
    public double getMin_charge() {return 0;};
    public double getCardRest() {return 0;};
    public int getFree_sms() {return 0;};


    public String toString(){
		return super.toString() + "\nNumber of free MB: " + this.data + "\nCharge per additional MB:" + this.charge;
	}
}
