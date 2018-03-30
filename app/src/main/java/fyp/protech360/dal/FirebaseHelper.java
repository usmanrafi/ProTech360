package fyp.protech360.dal;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fyp.protech360.classes.User;
import fyp.protech360.utils.Global;

public class FirebaseHelper {

    public static void loadUsers(){

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Connections")
                .child(Global.currentUser.getUuid());

        final ArrayList<String> uids = new ArrayList<>();

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    uids.add(ds.getKey());
                }

                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");
                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                            User user = ds.getValue(User.class);
                            if(uids.indexOf(user.getUuid()) != -1)
                                Global.currentUser.addConnection(user);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
