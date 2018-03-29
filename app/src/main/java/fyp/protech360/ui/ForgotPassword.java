package fyp.protech360.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import fyp.protech360.R;

public class ForgotPassword extends Fragment {
    View myView ;

    FirebaseAuth mFirebaseAuth;

    EditText mEmail;
    Button mButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.forgotpassword,container,false);

        mEmail = myView.findViewById(R.id.forgotEmail);
        mButton = myView.findViewById(R.id.forgotBtn);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = mEmail.getText().toString();
                if(text.matches(Patterns.EMAIL_ADDRESS.pattern())){
                    mFirebaseAuth = FirebaseAuth.getInstance();
                    mFirebaseAuth.sendPasswordResetEmail(text).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(),"Email Sent!",Toast.LENGTH_SHORT);
                        }
                    });

                    Toast.makeText(getActivity(),"An email will be sent to reset password",Toast.LENGTH_SHORT);

                    startActivity(new Intent(getActivity(), VerificationActivity.class));
                    getActivity().finish();
                }
                else
                    Toast.makeText(getActivity(),"Invalid email",Toast.LENGTH_SHORT);

            }
        });


        return myView;
    }
}
