package fyp.protech360.classes;

import android.telephony.SmsManager;

import java.io.Serializable;

public class EmergencyDetails implements Serializable{
    private String message;
    private String num1;
    private String num2;
    private String num3;

    public EmergencyDetails(){
        this.message = "ALERT!!! I am in trouble, please help me!";
        this.num1 = null;
        this.num2 = null;
        this.num3 = null;
    }

    public EmergencyDetails(String msg, String n1, String n2, String n3){
        this.message = msg;
        this.num1 = n1;
        this.num2 = n2;
        this.num3 = n3;
    }

    public String getMessage(){return this.message;}

    public String getNum1() {
        return num1;
    }

    public String getNum2() {
        return num2;
    }

    public String getNum3() {
        return num3;
    }

    public void setMessage(String msg){
        this.message = msg;
    }

    public boolean validNumbers(){
        if(this.num1 == null && this.num2 == null && this.num3 == null)
            return false;
        else {
            try {
                if (this.num1.length() != 13
                        && this.num2.length() != 13
                        && this.num3.length() != 13)
                    return false;
            }catch (NullPointerException e){
                return false;
            }
        }

        return true;
    }

    public void setNum1(String num1) {
        this.num1 = num1;
    }

    public void setNum2(String num2) {
        this.num2 = num2;
    }

    public void setNum3(String num3) {
        this.num3 = num3;
    }

    /*public void setNumbers(String[] nums){
        this.num1 = nums[0];
        this.num2 = nums[1];
        this.num3 = nums[2];
    }*/

    public void sendPanicAlert(String latitude, String longitude){

        String Url = "http://www.google.com/maps/place/" + latitude + "," + longitude;
        String messageToSend = this.message + "\n" +
                "My current location is: " + Url + ".\nPlease hurry up!\n\nSent via ProTech360";


        if(num1 != null)
            SmsManager.getDefault().sendTextMessage(num1, null, messageToSend, null, null);

        if(num2 != null)
            SmsManager.getDefault().sendTextMessage(num2, null, messageToSend, null, null);

        if(num3 != null)
            SmsManager.getDefault().sendTextMessage(num3, null, messageToSend, null, null);
    }
}
