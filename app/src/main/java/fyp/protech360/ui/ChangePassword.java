package fyp.protech360.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fyp.protech360.R;
import fyp.protech360.utils.Global;

public class ChangePassword extends Fragment {
    View myView;
    FirebaseUser user;
    EditText oldPassword;
    EditText newPassword;
    Button changePasswordButton;
    String oldpass;
    String newPass;
    FragmentManager fm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.changepassword,container,false);
        ((Homepage) getActivity()).setActionBarTitle("Change Password");
        oldPassword = (EditText) myView.findViewById(R.id.CurrentPassword);
        newPassword = (EditText) myView.findViewById(R.id.ChangePassword);
        changePasswordButton = (Button) myView.findViewById(R.id.ChangePasswordBtn);
        fm = ((Homepage) getActivity()).fragmentManager;

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldpass = oldPassword.getText().toString();
                newPass = newPassword.getText().toString();
                changePassword();
            }
        });

        return myView;
    }

    public void changePassword(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        final String email = user.getEmail();
        AuthCredential credential = EmailAuthProvider.getCredential(email,oldpass);

        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(!task.isSuccessful()){

                            }else {
                                Toast.makeText(getActivity(),"Password Updated Successfully",Toast.LENGTH_LONG).show();
                                fm.beginTransaction().replace(R.id.content_frame,new Settings()).commit();
                            }
                        }
                    });
                }else {

                }
            }
        });
    }

    public void forUsman(){
        boolean Requestchoice = true;

        if(Requestchoice){

            //ASSUMING THAT WE HAVE A REQUEST OBJECT

            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Connections").child(Global.currentUser.getUuid()).child("INSERT requestUID from Request class here");
            dbRef.setValue("Insert Request Object here").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if("requesttype == 1" == "which means if two-way"){
                        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Connections").child("INSERT requestUID from Request class here").child(Global.currentUser.getUuid());
                        dbRef.setValue("Insert Request Object here (modified) where requestID should be the current user now").addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("Sajjad_Ali","It's two-way baby");
                            }
                        });

                    }
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Requests").child(Global.currentUser.getUuid()).child("INSERT requestUID from Request class here");
                    db.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                           Log.d("Sajjad_Ali","It is done");
                        }
                    });
                }
            });
        }
        else{
            Log.d("Sajjad_Ali","Rejected");
        }
    }
}

