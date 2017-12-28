package fyp.protech360;

import android.graphics.Bitmap;

/**
 * Created by Aliyan on 12/28/2017.
 */

public class User {

    String name;
    String PhoneNumber;
    String Email;
    String password;
    Bitmap image;

    User(String name, String PhoneNumber, String Email, String password, Bitmap image)
    {
        this.name = name;
        this.PhoneNumber = PhoneNumber;
        this.Email = Email;
        this.password = password;
        this.image = image;
    }

    String getName()
    {
        return name;
    }

    Bitmap getImage()
    {
        return image;
    }
}
