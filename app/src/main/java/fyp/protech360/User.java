package fyp.protech360;

import android.graphics.Bitmap;


public class User {

    private String name;
    private String PhoneNumber;
    private String Email;
    private String password;
    private Bitmap image;

    private EmergencyDetails emergencyDetails;

    User(String name, String PhoneNumber, String Email, Bitmap image)
    {
        this.name = name;
        this.PhoneNumber = PhoneNumber;
        this.Email = Email;
        this.image = image;
    }

    User(String name, String PhoneNumber, String Email, Bitmap image, EmergencyDetails det)
    {
        this.name = name;
        this.PhoneNumber = PhoneNumber;
        this.Email = Email;
        this.image = image;
        this.emergencyDetails = det;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Bitmap getImage(){
        return this.image;
    }

    public EmergencyDetails getEmergencyDetails(){return this.emergencyDetails;}

    public void setEmergencyDetails(EmergencyDetails det){
        this.emergencyDetails = det;
    }
}
