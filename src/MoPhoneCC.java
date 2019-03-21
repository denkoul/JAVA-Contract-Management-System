public class MoPhoneCC extends MoPhone {
    private double rest;
    public MoPhoneCC (int type, double cut, String name, double fixed, int free_sms, double sms_charge, double free_min, double min_charge, double rest){
        super(type, cut, name, fixed, free_sms, sms_charge, free_min, min_charge);
        this.rest = rest;
    }

    public double getCardRest() {
        return rest;
    }


    public String toString() {
	return super.toString() +"\nAvailable Balance: " + rest;
	}
}
