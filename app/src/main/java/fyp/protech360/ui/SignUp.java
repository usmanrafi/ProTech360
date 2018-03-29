package fyp.protech360.ui;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import fyp.protech360.R;
import fyp.protech360.classes.User;
import fyp.protech360.utils.Global;

import static android.app.Activity.RESULT_OK;


public class SignUp extends Fragment {
    View myView;
    android.app.FragmentManager fragmentManager = getFragmentManager();
    private CircleImageView photo;
    private Bitmap bmp;
    private String encoded;
    private TextInputLayout mName;
    private TextInputLayout mNumber;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private TextInputLayout mConfirmPassword;

    private Button mButton;

    private FirebaseAuth mFirebaseAuth;

    public boolean isValidEmail(TextInputLayout email){
        String text = email.getEditText().getText().toString();

        if(text.isEmpty()){
            email.setError("Field required");
            return false;
        }else if(text.matches(Patterns.EMAIL_ADDRESS.pattern()))
            return true;

        email.setError("Invalid Email Address");
        return false;
    }

    public boolean isValidNumber(TextInputLayout tilNumber){
        String text =  tilNumber.getEditText().getText().toString();

        if(text.isEmpty()){
            tilNumber.setError("Field required");
            return false;
        }
        else if(text.length() != 13){
            tilNumber.setError("Number should be of the format +92xxxxxxxxxx");
            return false;
        }

        for (int i = 1; i < 13 ; ++i) {

            if (text.charAt(i) < '0' || text.charAt(i) > '9'){
                tilNumber.setError("Invalid Number");
                return false;
            }
        }

        return true;
    }

    private boolean areValidPasswords(TextInputLayout tilPassword, TextInputLayout tilConfirmPassword) {
        String password = tilPassword.getEditText().getText().toString();
        String confirmPassword = tilConfirmPassword.getEditText().getText().toString();

        boolean isValid = true;
        if (password.isEmpty()) {
            tilPassword.setError("Field required");
            isValid = false;
        }
        if (confirmPassword.isEmpty()){
            tilConfirmPassword.setError("Field required");
            isValid = false;
        }
        if (password.length() < 5) {
            tilPassword.setError("Min 5 characters required");
            isValid = false;
        }
        if (confirmPassword.length() < 5){
            tilConfirmPassword.setError("Min 5 characters required");
            isValid = false;
        }

        if(!password.equals(confirmPassword)){
            tilPassword.setError("Password don't match");
            tilConfirmPassword.setError("Password don't match");
            isValid = false;
        }

        return isValid;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.signup, container, false);

        mFirebaseAuth = FirebaseAuth.getInstance();

        photo = (CircleImageView) myView.findViewById(R.id.userImage);

        mName = myView.findViewById(R.id.til_name);
        mNumber = myView.findViewById(R.id.til_number);
        mEmail = myView.findViewById(R.id.til_email);
        mPassword = myView.findViewById(R.id.til_password);
        mConfirmPassword = myView.findViewById(R.id.til_confirm_password);

        mButton = myView.findViewById(R.id.signupBtn);

        photo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 3);
            }
        });


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();

            }
        });
        return myView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            bmp = BitmapFactory.decodeFile(picturePath);
            bmp = Bitmap.createScaledBitmap(bmp, 200, 200, false);
            photo.setImageBitmap(bmp);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        }
    }

    public void GoToLogin(View view) {
        startActivity(new Intent(getActivity(),VerificationActivity.class));
        getActivity().finish();
    }

    public void register() {
        boolean isValidInfo = true;

        if(mName.getEditText().getText().toString().isEmpty()){
            mName.setError("Field required");
            isValidInfo = false;
        }

        if (isValidEmail(mEmail) && areValidPasswords(mPassword, mConfirmPassword) 
            && isValidNumber(mNumber) && isValidInfo) {


            final ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Registering User...");
            dialog.show();

            mFirebaseAuth.createUserWithEmailAndPassword(mEmail.getEditText().getText().toString().trim(),
                    mPassword.getEditText().getText().toString().trim())
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                mFirebaseAuth.getCurrentUser().sendEmailVerification()
                                        .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getActivity(), "Email sent!", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(getActivity(), "Email not sent!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                Toast.makeText(getActivity(), "User registered! Please verify email!", Toast.LENGTH_LONG).show();

                                // TODO: Add user
                                User user = new User(mFirebaseAuth.getCurrentUser().getUid(),
                                        mName.getEditText().getText().toString().trim(),
                                        mNumber.getEditText().getText().toString().trim(),
                                        mEmail.getEditText().getText().toString().trim(),
                                        encoded
                              );

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
                                ref.child(mFirebaseAuth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Log.i("abc","lks");
                                    }
                                });


                                Intent intent = new Intent(getActivity(), VerificationActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            } else {
                                Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            dialog.dismiss();
        }
    }
}
