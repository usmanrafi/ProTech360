package fyp.protech360.ui;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import fyp.protech360.R;

public class Login extends Fragment {

    View myView ;
    android.app.FragmentManager fragmentManager = getFragmentManager();


    private EditText mEmail;
    private EditText mPassword;
    private Button mButton;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Usman_Login","onCreateView of Login.java");

        myView = inflater.inflate(R.layout.activity_login,container,false);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if(mFirebaseUser != null){
            startActivity(new Intent(getActivity().getApplicationContext(), Homepage.class));
            getActivity().finish();
        }



        mEmail = myView.findViewById(R.id.loginEmail);
        mPassword = myView.findViewById(R.id.loginPassword);
        mButton = myView.findViewById(R.id.loginBtn);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String pass = mPassword.getText().toString().trim();

                if(!email.isEmpty() && !pass.isEmpty())
                    mFirebaseAuth.signInWithEmailAndPassword(email, pass);

                mFirebaseUser = mFirebaseAuth.getCurrentUser();


                if (mFirebaseUser != null) {
                    if (mFirebaseUser.isEmailVerified()) {
                        Intent i = new Intent(getActivity(), Homepage.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

//               TODO: getLoggedInUser();
                        startActivity(i);
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "Please verify Email", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(getActivity(), "Error in signing in", Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(getActivity(),Homepage.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        return myView;
    }

}
