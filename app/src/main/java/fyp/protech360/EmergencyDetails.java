package fyp.protech360;

import android.telephony.SmsManager;

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

    public void setMessage(String msg){
        this.message = msg;
    }

    public void sendPanicAlert(String latitude, String longitude){

        String Url = "http://www.google.com/maps/place/" + latitude + "," + longitude;
        String messageToSend = "ALERT!!! I am in trouble, please help me.\n" +
                "My current location is: " + Url + ".\nPlease hurry up!\n\nSent via ProTech360";

        SmsManager.getDefault().sendTextMessage(num1, null, messageToSend, null, null);
        SmsManager.getDefault().sendTextMessage(num2, null, messageToSend, null, null);
        SmsManager.getDefault().sendTextMessage(num3, null, messageToSend, null, null);
    }
}
