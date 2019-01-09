package collectifyamoukoudji.projetseg;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClientResaFragment extends Fragment{

    View myView;
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



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_view_res, container, false);

        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");

        res = new ArrayList<>();

        setupUI();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            iduser = bundle.getString("iduser");
        }


        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                res.clear();

                for(DataSnapshot postsnapshot : dataSnapshot.getChildren()){
                    cuser = postsnapshot.getValue(Users.class);

                    if( cuser != null && cuser.get_type().equals("Fournisseur de services")){
                        ArrayList<Reservation> resa = cuser.get_currentOrganisation().getReservations();

                        if(resa != null){
                            for(Reservation r : resa){
                                res.add(r);
                            }
                        }else{
                            toastMessage("Aucune reservations!");
                        }
                    }
                }
                //getting users
                //creatig adapter
                ResaList userAdapter = new ResaList(getActivity(), res);
                //attaching adapter to Listview
                listViewResa.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        return myView;
    }

    /**
     * Setting up all the fields of the interface.
     */
    public void setupUI() {
        Resa = (TextView) myView.findViewById(R.id.Reservations);
        listViewResa = (ListView) myView.findViewById(R.id.listViewResa);

    }

    /**
     * Displaying toast message to the user.
     */
    private void toastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


}