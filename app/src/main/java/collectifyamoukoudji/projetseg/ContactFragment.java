package collectifyamoukoudji.projetseg;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ContactFragment extends Fragment{

    View myView;
    private CheckBox[][] checkBoxes;
    private ArrayList<ArrayList<Boolean>> plageHorraire;
    private Button  BtnEnregistrerPlage;
    private String iduser;
    private DatabaseReference databaseUser;
    private Users cuser;
    private Organisation corganisation;
//    private TextView days0, days1, days2, days3, days4, days5, days6;
    private ArrayList<String> days;
    private ArrayList<String> times;
    private ArrayList<Service> cservices;
    private ArrayList<String>  dic;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.contact_layout, container, false);
        checkBoxes = new CheckBox[12][7];
        plageHorraire = new ArrayList<>();
        days = new ArrayList<>();
        times = new ArrayList<>();
        cservices = new ArrayList<>();

        dic = new ArrayList<>();

        days.add("LUN");
        days.add("MAR");
        days.add("MER");
        days.add("JED");
        days.add("SAM");
        days.add("DIM");

        times.add("8 - 9h");
        times.add("9 - 10h");
        times.add("10 - 11h");
        times.add("11 - 12h");
        times.add("12 - 13h");
        times.add("13 - 14h");
        times.add("14 - 15h");
        times.add("15 - 16h");
        times.add("16 - 17h");
        times.add("17 - 18h");
        times.add("18 - 19h");
        times.add("19 - 20h");



        setupUI();



        Bundle bundle = this.getArguments();
        if (bundle != null) {
            iduser = bundle.getString("iduser");




            databaseUser = FirebaseDatabase.getInstance().getReference("Users");



            databaseUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    if(iduser != null){
                        Users user  = dataSnapshot.child(iduser).getValue(Users.class);
                        cuser = new Users(user.getId(), user.get_firstname(), user.get_lastname(), user.get_email(), user.get_type(), user.get_currentOrganisation());
                        corganisation = user.get_currentOrganisation();
                        cservices = corganisation.get_services();
                        Log.d("DEBUG", "Value is: " + cuser);


                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Failed to read value
                    toastMessage("Failed to alter database.");
                    Log.w("DEBUG", "Failed to read value.", databaseError.toException());
                }
            });

        }

        loadAdd();

        BtnEnregistrerPlage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!cservices.isEmpty()){
                    addAvailability();
                }else{
                    toastMessage("Ajoutez des services a votre organisation");
                }
            }

        });

        return myView;
    }


    //DONE
    private void addAvailability() {

        for (int i = 0; i < 12 ; i++) {
            ArrayList<Boolean> tmp = new ArrayList<>();
            for (int j = 0; j < 7; j++) {
//                if(checkBoxes[i][j].isChecked()) {
//                    dic.add(days.get(j)+" - "+ times.get(i));
//                }
                tmp.add(checkBoxes[i][j].isChecked());

            }
            plageHorraire.add(tmp);
        }

        addOrganisation();

    }

    private void setupUI() {

//        days0 = (TextView) myView.findViewById(R.id.textViewDay0);
//        days1 = (TextView) myView.findViewById(R.id.textViewDay1);
//        days2 = (TextView) myView.findViewById(R.id.textViewDay2);
//        days3 = (TextView) myView.findViewById(R.id.textViewDay3);
//        days4 = (TextView) myView.findViewById(R.id.textViewDay4);
//        days5 = (TextView) myView.findViewById(R.id.textViewDay5);
//        days6 = (TextView) myView.findViewById(R.id.textViewDay6);

        BtnEnregistrerPlage = (Button) myView.findViewById(R.id.buttonEnregistrerPlage);
        for (int i = 0; i < 12 ; i++) {
            for (int j = 0; j < 7; j++) {
                String buttonID = "checkBox" +(7 * i + j+1);
                int resID = getResources().getIdentifier(buttonID, "id",getContext().getPackageName());
                checkBoxes[i][j] = (CheckBox) myView.findViewById(resID);
            }
        }
    }

    private void addOrganisation() {
        //getting the value



        Query nameQuery = FirebaseDatabase.getInstance().getReference().child("Users").child("_currentOrganisation").orderByChild("_organisationHorraire");
        nameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0){
//                    flag = "";
                    toastMessage("Not able to add");

                }else{
                    //getting a unique id using push().getKey() method
                    //it will create a unique id and will use it as the Primary Key for our Product
                    String id = databaseUser.push().getKey();


                    //creating a Product
                    Horraire newh = new Horraire(id, true, plageHorraire);

                    corganisation.set_organisationHorraire(newh);

                    cuser.set_currentOrganisation(corganisation);

                    //Saving the Product
                    databaseUser.child(iduser).setValue(cuser);

                    toastMessage("Information up to date");

                    plageHorraire.clear();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void loadAdd() {


        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Users user = dataSnapshot.child(iduser).getValue(Users.class);

                cuser = new Users(user.getId(), user.get_firstname(), user.get_lastname(), user.get_email(), user.get_type(), user.get_currentOrganisation());
                corganisation = user.get_currentOrganisation();

                plageHorraire = corganisation.get_organisationHorraire().get_array();

                    for (int i = 0; i < 12 ; i++) {
                        for (int j = 0; j < 7; j++) {
                            checkBoxes[i][j].setChecked(plageHorraire.get(i).get(j));
                        }
                    }

                    plageHorraire.clear();



                Log.d("DEBUG", "Value is: " + cuser);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value

            }
        });



    }

    private void toastMessage (String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}