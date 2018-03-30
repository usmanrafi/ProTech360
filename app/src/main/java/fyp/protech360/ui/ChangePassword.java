package fyp.protech360.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import fyp.protech360.R;

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
}

