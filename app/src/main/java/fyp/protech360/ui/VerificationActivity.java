package fyp.protech360.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import fyp.protech360.R;

public class VerificationActivity extends Activity {

    android.app.FragmentManager fragmentManager = getFragmentManager();
    final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    final int MY_PERMISSIONS_REQUEST_SEND_SMS = 2;
    final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS = 3;
    final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 4;
    final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        permissions();


        if (savedInstanceState == null)
        {
            fragmentManager.beginTransaction().replace(R.id.content_frame_verification,new Login()).commit();
        }

    }

    public void forgotPassword(View view) {
        fragmentManager.beginTransaction().replace(R.id.content_frame_verification,new ForgotPassword()).commit();
    }

    public void signup(View view) {
        fragmentManager.beginTransaction().replace(R.id.content_frame_verification,new SignUp()).commit();
    }

    public void GoToLogin(View view) {
        fragmentManager.beginTransaction().replace(R.id.content_frame_verification,new Login()).commit();
    }

    public void forgotPasswordStepOne(View view) {
        fragmentManager.beginTransaction().replace(R.id.content_frame_verification,new ForgotPassword2()).commit();
    }

    public void forgotPasswordStepTwo(View view) {
        fragmentManager.beginTransaction().replace(R.id.content_frame_verification,new ResetPassword()).commit();
    }

    public void resetPassword(View view) {
        Toast.makeText(getApplicationContext(),"Password Reset Successful",Toast.LENGTH_SHORT).show();
        fragmentManager.beginTransaction().replace(R.id.content_frame_verification,new Login()).commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    permissions();
                }
                else
                {

                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    permissions();
                }
                else
                {

                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    permissions();
                }
                else
                {

                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    permissions();
                }
                else
                {

                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_RECEIVE_SMS:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    permissions();
                }
                else
                {

                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_SEND_SMS:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    permissions();
                }
                else
                {

                }
                return;
            }
        }


    }

    void permissions()
    {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
        }

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.SEND_SMS},MY_PERMISSIONS_REQUEST_SEND_SMS);
        }

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.RECEIVE_SMS},MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
        }

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }


    }
}
