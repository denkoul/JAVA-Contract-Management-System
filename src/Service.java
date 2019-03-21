public abstract class Service{


    private int type;
    private String name;
    private double fixed;
    private double cut;
    private double percentage_cut;
    //constructor
    public Service(int type, double cut, String name, double fixed) {
        this.cut = cut;
        this.type = type;
        this.name = name;
        this.fixed = fixed;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public double getFixed() {
        return fixed;
    }


    public double getCut() {
        return cut;
    }

    public abstract double getData();
    public abstract double getCharge();
    public abstract double getFree_min();
    public abstract double getSms_charge();
    public abstract double getMin_charge();
    public abstract double getCardRest();
    public abstract int getFree_sms();

    public void setType(int type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFixed(double fixed) {
        this.fixed = fixed;
    }

    public void setCut(double cut) {
        this.cut = cut;
    }

    public void setPercentage_cut(double percentage_cut) {
        this.percentage_cut = percentage_cut;
    }

    public String toString(){
        percentage_cut = this.cut*100;
		return "Service name: " + this.name + "\nMonthly fixed charge: " + this.fixed + "\nCut: " + percentage_cut + "%";
    };

}
