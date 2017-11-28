package fyp.protech360;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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

    }

    public void signup(View view) {
        fragmentManager.beginTransaction().replace(R.id.content_frame_verification,new SignUp()).commit();
    }

    public void GoToLogin(View view) {
        fragmentManager.beginTransaction().replace(R.id.content_frame_verification,new Login()).commit();

    }
}
