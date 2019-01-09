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

public class ViewResActivity extends AppCompatActivity {

    private TextView Resa;

    private String userID;
    private String iduser;

    /**
     * Contains a FirebaseAuth
     * that is assigned for managing users of the app.
     */
    private FirebaseAuth firebaseAuth;
    /**
     * Contains a DatabaseReference
     * that is assigned for managing the Firebase Realtime database.
     */
    private DatabaseReference databaseUsers;
    /**
     * Contains a DatabaseReference
     * that is assigned for containing the list of user of the database.
     */
    private List<Reservation> res;
    private ListView listViewResa;
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
    private static final String TAG = "RESERVATION";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_res);


        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");

        res = new ArrayList<>();

        setupUI();


        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                iduser = getIntent().getStringExtra("iduser");

                res.clear();
                cuser = dataSnapshot.child(iduser).getValue(Users.class);
                    //getting users

                    ArrayList<Reservation> resa = cuser.get_currentOrganisation().getReservations();
                    for(Reservation r : resa){
                        res.add(r);

                }
                    //creatig adapter
                    ResaList userAdapter = new ResaList(ViewResActivity.this, res);
                    //attaching adapter to Listview
                    listViewResa.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    /**
     * Setting up all the fields of the interface.
     */
    public void setupUI() {
        Resa = (TextView) findViewById(R.id.Reservations);
        listViewResa = (ListView) findViewById(R.id.listViewResa);

    }

    /**
     * Displaying toast message to the user.
     */
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
