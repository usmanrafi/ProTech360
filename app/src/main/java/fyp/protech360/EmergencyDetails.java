package fyp.protech360;

public class EmergencyDetails {
    private String message;
    private String num1;
    private String num2;
    private String num3;

    EmergencyDetails(String msg, String n1, String n2, String n3){
        this.message = message;
        this.num1 = n1;
        this.num2 = n2;
        this.num3 = n3;
    }

    public String getMessage(){return this.message;}

    public String[] getNumbers(){
        String[] nums = {this.num1, this.num2, this.num3};
        return nums;
    }
}
