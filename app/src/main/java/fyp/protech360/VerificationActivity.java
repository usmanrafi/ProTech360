package fyp.protech360;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class VerificationActivity extends Activity {

    android.app.FragmentManager fragmentManager = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);


        if (savedInstanceState == null)
        {
            fragmentManager.beginTransaction().replace(R.id.content_frame_verification,new Login()).commit();
        }

    }

    public void login(View view) {
        Intent intent = new Intent(getApplicationContext(),Homepage.class);
        startActivity(intent);
        finish();
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
}
