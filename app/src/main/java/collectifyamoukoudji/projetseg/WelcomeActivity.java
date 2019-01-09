package collectifyamoukoudji.projetseg;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class WelcomeActivity extends AppCompatActivity {
    //Instance variables ************************************************

    /**
     * Contains a TextView type of field
     * that is assigned for the title of the page.
     */
    private TextView welcome;
    /**
     * Contains a TextView type of field
     * that is assigned for the info of the user.
     */
    private TextView info;
    /**
     * Contains a String value
     * that is assigned for the user id.
     */
    private String userID;
    private String iduser;

    /**
     * Contains a FirebaseAuth
     * that is assigned for managing users of the app.
     */
    private FirebaseAuth firebaseAuth;
    /**
     * Contains a FirebaseAuth.AuthStateListener
     * that is assigned for managing users of the app.
     */
    private FirebaseAuth.AuthStateListener mAuthListener;
    /**
     * Contains a DatabaseReference
     * that is assigned for managing the Firebase Realtime database.
     */
    private DatabaseReference databaseUsers;
    /**
     * Contains a DatabaseReference
     * that is assigned for containing the list of user of the database.
     */
    private List<Users> users;
    private ListView listViewUser;
    /**
     * Contains a Users object
     * that is assigned for the user authentified.
     */
    private Users CurrentUSer;
    private Users cuser;
    /**
     * Contains a String
     * that is assigned for Debug purpose.
     */
    private static final String TAG = "WELCOME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);



        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth = firebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            userID = user.getUid();
        }

        users = new ArrayList<>();

        setupUI();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//                    toastMessage("Successfully signed in with: " + user.getEmail());
                    userID = user.getUid();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
//                    toastMessage("Successfully signed out.");
                }
                // ...
            }
        };

        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                iduser = getIntent().getStringExtra("iduser");

                if (userID != null) {
                    cuser = dataSnapshot.child(userID).getValue(Users.class);
                    CurrentUSer = dataSnapshot.child(userID).getValue(Users.class);
                }else {
                    cuser = dataSnapshot.child(iduser).getValue(Users.class);
                    CurrentUSer = dataSnapshot.child(iduser).getValue(Users.class);
                }
                info.setText("Salut! " +cuser.get_email()+", vous etes authentifié en tant que "+cuser.get_type());
                info.setVisibility(View.VISIBLE);
                users.clear();
                for(DataSnapshot postDataSnapshot : dataSnapshot.getChildren()){
                    //getting users
                    cuser = postDataSnapshot.getValue(Users.class);
                    users.add(cuser);
                }
                if (CurrentUSer.get_type().equals("Administrateur")) {
                    //creatig adapter
                    UsersList userAdapter = new UsersList(WelcomeActivity.this, users);
                    //attaching adapter to Listview
                    listViewUser.setAdapter(userAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
    /**
     * Setting up the authentification listener of database.
     *
     */
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);

    }
    /**
     * Remove the authentification listener of database.
     *
     */
    @Override
    public void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(mAuthListener);
        finish();
    }
    /**
     * Setting up all the fields of the interface.
     *
     */
    public void setupUI(){
        welcome = (TextView)findViewById(R.id.Welcome);
        info =  (TextView)findViewById(R.id.textInfo);
        listViewUser = (ListView)findViewById(R.id.listViewUsers);

    }
    /**
     * Displaying toast message to the user.
     *
     */
    private void toastMessage (String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
