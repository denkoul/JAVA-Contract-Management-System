public class Contract {
    private int id, sms_consumed = 0, type;
    private double  home_call_duration = 0, mobile_call_duration = 0, mb_comsumed = 0;
    private String service_name, name, phone, date, way_of_payment;

    public Contract(int type, int id, String service_name, String name, String phone, String date, String way_of_payment) {
        this.type = type;
        this.id = id;
        this.service_name = service_name;
        this.name = name;
        this.phone = phone;
        this.date = date;
        this.way_of_payment = way_of_payment;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSms_consumed(int sms_consumed) {
        this.sms_consumed = sms_consumed;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setWay_of_payment(String way_of_payment) {
        this.way_of_payment = way_of_payment;
    }

    public void setSMS(int sms_consumed){
        this.sms_consumed = this.sms_consumed +  sms_consumed;
    }

    public void setHome_call_duration(double home_call_duration) {
        this.home_call_duration = this.home_call_duration + home_call_duration;
    }

    public void setMobile_call_duration(double mobile_call_duration) {
        this.mobile_call_duration = this.mobile_call_duration + mobile_call_duration;
    }

    public void setMb_comsumed(double mb_comsumed) {
        this.mb_comsumed = this.mb_comsumed + mb_comsumed;
    }


    public int getType() {
        return type;
    }


    public int getId() {
        return id;
    }

    public String getService_name() {
        return service_name;
    }

    public int getSms_consumed() {
        return sms_consumed;
    }

    public double getHome_call_duration() {
        return home_call_duration;
    }

    public double getMobile_call_duration() {
        return mobile_call_duration;
    }

    public double getMb_comsumed() {
        return mb_comsumed;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getDate() {
        return date;
    }

    public String getWay_of_payment() {
        return way_of_payment;
    }

    public String getStringType() {
        String return1 = null;
        if (type == 1) {
            return1 = "Mobile Internet";
        } else if (type == 2) {
            return1 = "Contract";
        } else {
            return1 = "Card Contract";
        }
    return return1;
    }



    public String toString() {
        return " Contract ID: " + id +
                "\n Type: " + getStringType() +
                "\n Service name: " + service_name +
                "\n Name: " + name +
                "\n Phone number: " + phone +
                "\n Registration date: " + date +
                "\n Payment method: " + way_of_payment;
    }
}
