package fyp.protech360.classes;

import android.graphics.Bitmap;


public class User {

    private String name;
    private String PhoneNumber;
    private String Email;
    private Bitmap image;

    private EmergencyDetails emergencyDetails;

    public User(){}

    public User(String name, String PhoneNumber, String Email, Bitmap image)
    {
        this.name = name;
        this.PhoneNumber = PhoneNumber;
        this.Email = Email;
        this.image = image;
    }

    public User(String name, String PhoneNumber, String Email, Bitmap image, EmergencyDetails det)
    {
        this.name = name;
        this.PhoneNumber = PhoneNumber;
        this.Email = Email;
        this.image = image;
        this.emergencyDetails = det;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;

        if(!(obj instanceof User))
            return false;

        if(((User) obj).getEmail().equalsIgnoreCase(this.Email))
            return true;

        if(((User) obj).getPhoneNumber().equals(this.PhoneNumber))
            return true;

        return false;
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

    public String getEmail() {
        return Email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public EmergencyDetails getEmergencyDetails(){return this.emergencyDetails;}

    public void setEmergencyDetails(EmergencyDetails det){
        this.emergencyDetails = det;
    }
}
