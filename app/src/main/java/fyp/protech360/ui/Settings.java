package fyp.protech360.ui;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.microedition.khronos.opengles.GL;

import de.hdodenhof.circleimageview.CircleImageView;

import fyp.protech360.R;
import fyp.protech360.dal.DatabaseHelper;
import fyp.protech360.utils.Global;
import fyp.protech360.utils.ImageEncoderDecoder;

import static android.app.Activity.RESULT_OK;

public class Settings extends Fragment {
    View myView;

    EditText name;
    CircleImageView photo;
    ImageView imageButton;
    boolean editNameDisabled;
    Bitmap bmp;
    String encoded;

    Switch doNotTrack;
    Switch doNotDisturb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.settings, container, false);
        ((Homepage) getActivity()).setActionBarTitle("Settings");

        editNameDisabled = true;
        name = myView.findViewById(R.id.editName);
        photo = (CircleImageView) myView.findViewById(R.id.editImage);
        imageButton = myView.findViewById(R.id.editNameButton);

        name.setText(Global.currentUser.getName());
        Bitmap decodedByte = ImageEncoderDecoder.decodeImage(Global.currentUser.getImage());
        photo.setImageBitmap(decodedByte);

        doNotDisturb = myView.findViewById(R.id.switch_do_not_disturb);
        doNotTrack = myView.findViewById(R.id.switch_do_not_track);

        doNotTrack.setChecked(Global.currentUser.isTracking());

        doNotTrack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    if(!Global.currentUser.isTracking()) {
                        Global.currentUser.setTracking(true);
                        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users").child(Global.currentUser.getUuid()).child("tracking");
                        db.setValue(Global.currentUser.isTracking());
                        Global.setLocationIntent(getActivity());
                        Global.LocationIntent.putExtra("user_name", Global.currentUser.getUuid());
                        getActivity().startService(Global.LocationIntent);
                    }
                }
                else{

                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                    alert.setTitle("Disable Tracking");
                    alert.setMessage("Are you sure you want to disable tracking?");

                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            getActivity().stopService(Global.LocationIntent);
                            Global.currentUser.setTracking(false);
                            DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users").child(Global.currentUser.getUuid()).child("tracking");
                            db.setValue(Global.currentUser.isTracking());
                        }
                    });

                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            doNotTrack.setChecked(true);
                            Toast.makeText(getActivity(),"Cancelled",Toast.LENGTH_SHORT).show();
                        }
                    });

                    alert.show();

                }
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editNameDisabled) {
                    name.setEnabled(true);
                    editNameDisabled = false;
                } else {
                    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Users").child(Global.currentUser.getUuid()).child("name");
                    dbref.setValue(name.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Global.currentUser.setName(name.getText().toString());
                            DatabaseHelper dbHelper = DatabaseHelper.getInstance(getActivity());
                            dbHelper.insertUserDetails(Global.currentUser.getUuid(), Global.currentUser.getName(),Global.currentUser.getEmail(),Global.currentUser.getImage());
                        }
                    });
                    name.setEnabled(false);
                    editNameDisabled = true;
                }
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 3);
            }
        });


        return myView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && resultCode == RESULT_OK && null != data) {
            bmp = ImageEncoderDecoder.getImageFromGallery(data,this.getActivity());
            photo.setImageBitmap(bmp);
            encoded = ImageEncoderDecoder.encodeImage(bmp);
            Global.currentUser.setImage(encoded);
            DatabaseHelper dbHelper = DatabaseHelper.getInstance(getActivity());
            dbHelper.insertUserDetails(Global.currentUser.getUuid(),Global.currentUser.getName(),Global.currentUser.getEmail(),Global.currentUser.getImage());
            DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Users").child(Global.currentUser.getUuid()).child("image");
            dbref.setValue(encoded).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });

        }
    }

}
