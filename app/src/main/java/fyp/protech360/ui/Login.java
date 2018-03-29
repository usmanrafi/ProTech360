package fyp.protech360.ui;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.microedition.khronos.opengles.GL;

import fyp.protech360.R;
import fyp.protech360.classes.EmergencyDetails;
import fyp.protech360.classes.User;
import fyp.protech360.dal.DatabaseHelper;
import fyp.protech360.utils.Global;

public class Login extends Fragment {

    View myView ;
    android.app.FragmentManager fragmentManager = getFragmentManager();


    private EditText mEmail;
    private EditText mPassword;
    private Button mButton;

    DatabaseHelper databaseHelper;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Usman_Login","onCreateView of Login.java");

        myView = inflater.inflate(R.layout.activity_login,container,false);

        databaseHelper = DatabaseHelper.getInstance(getActivity());
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if(mFirebaseUser != null){

            if(!mFirebaseUser.isEmailVerified()){
                mFirebaseAuth.signOut();
            }
            else {
                getLoggedInUser();
            }
        }

        mEmail = myView.findViewById(R.id.loginEmail);
        mPassword = myView.findViewById(R.id.loginPassword);
        mButton = myView.findViewById(R.id.loginBtn);

        myView.clearFocus();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String pass = mPassword.getText().toString().trim();

                if(!email.isEmpty() && !pass.isEmpty()) {
                    authenticate(email,pass);
                }
                else {
                    Toast.makeText(getActivity(), "Error in signing in", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return myView;
    }

    public void getLoggedInUser() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(mFirebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Global.currentUser = dataSnapshot.getValue(User.class);
                Log.d("Usman_Login", "Current User updated"+Global.currentUser.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        startActivity(new Intent(getActivity().getApplicationContext(), Homepage.class));
        getActivity().finish();

        //DatabaseHelper dbHelper = DatabaseHelper.getInstance(getActivity());
        //Global.currentUser.setEmergencyDetails(dbHelper.getEmergencyDetails());
    }

    private void authenticate(String e, String p) {
        mFirebaseAuth.signInWithEmailAndPassword(e,p)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            mFirebaseUser = mFirebaseAuth.getCurrentUser();
                            final String uid = mFirebaseUser.getUid();

                            if (mFirebaseUser != null) {
                                if (mFirebaseUser.isEmailVerified()) {
                                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users");
                                    db.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                                        @Override
                                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                            User info = dataSnapshot.child(uid).getValue(User.class);
                                            databaseHelper.insertUserDetails(info.getUuid(),info.getName(),info.getEmail(),info.getImage());
                                            Log.d("Sajjad_Ali","Database Drawer added");
                                            Global.currentUser = info;
                                            EmergencyDetails details = Global.currentUser.getEmergencyDetails();
                                            String[] arr = {details.getNum1(),details.getNum2(),details.getNum3()};
                                            databaseHelper.insertEmergencyDetails(details.getMessage(),arr[0],arr[1],arr[2]);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });


                                    Intent i = new Intent(getActivity(), Homepage.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                    getActivity().finish();

                                } else {
                                    mFirebaseAuth.signOut();
                                    Toast.makeText(getActivity(), "Please verify Email", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        else{
                            Toast.makeText(getActivity(),"Authentication failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
