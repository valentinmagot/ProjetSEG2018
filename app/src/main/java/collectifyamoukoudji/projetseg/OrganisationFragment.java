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
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class OrganisationFragment extends Fragment{

    View myView;

    private EditText organisationName;
    private Switch organistionSwitch;
    private EditText organisationDescription;
    private Button btnEnregister;

    private ArrayList<String> ServiceOffert;
    private ArrayList<Service> ser;
    private ArrayAdapter<String> spinnerArrayAdapter;
    private ArrayAdapter<String> ListArrayAdapter;
    private DatabaseReference databaseService;
    private Button btnAjouter;
    private ListView listServices;
    private Spinner spinner;
    private String iduser;
    private DatabaseReference databaseUser;
    private DatabaseReference updateReference;
    private DatabaseReference databaseOrg;
    private Users cuser;
    private Organisation corganisation;
    private Address cadd;
    private Horraire ch;
    private ArrayList<Reservation> resa;
    private ArrayList<Double> rate;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.organisation_layout, container, false);

        ser = new ArrayList<>();

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
                        cadd = corganisation.get_organisationAddress();
                        ch = corganisation.get_organisationHorraire();
                        rate = corganisation.get_rating();
                        resa = corganisation.getReservations();
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
        //fill the spinner with the Db
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);
        listServices.setAdapter(ListArrayAdapter);
        //load services from DB
        loadEntries();
        loadOrg();
        //add services to th list of services
        btnAjouter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String services = (String)spinner.getSelectedItem();
                ServiceOffert.add(services);
                spinnerArrayAdapter.remove(services);
                ListArrayAdapter.notifyDataSetChanged();
                spinnerArrayAdapter.notifyDataSetChanged();
                addSer();
                toastMessage("N'oubliez pas de sauvegarder vos modifications");

            }
        });

        btnEnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrganisation();
            }
        });

        listServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                spinnerArrayAdapter.add(ServiceOffert.get(i));
                spinnerArrayAdapter.notifyDataSetChanged();
                ServiceOffert.remove(i);
                ListArrayAdapter.notifyDataSetChanged();
                addSer();
                toastMessage("N'oubliez pas de sauvegarder vos modifications");
                return true;
            }
        });

        return myView;
    }


    private void setupUI() {

        organisationName = (EditText) myView.findViewById(R.id.editTextNomOrgaisation);
        organistionSwitch = (Switch) myView.findViewById(R.id.switchLicensed);
        organisationDescription = (EditText) myView.findViewById(R.id.editTextDescriptOrgaisation);
        btnEnregister = (Button) myView.findViewById(R.id.buttonAjouterOrg);
        ServiceOffert = new ArrayList<>();
        databaseService = FirebaseDatabase.getInstance().getReference("Services");
        btnAjouter = (Button) myView.findViewById(R.id.buttonAddService);
        listServices = (ListView)myView.findViewById(R.id.listViewSvcOff);
        spinner = (Spinner) myView.findViewById(R.id.ServicesOffert);
        spinnerArrayAdapter = new ArrayAdapter<String>(this.getActivity(),R.layout.spinner_item);
        ListArrayAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, ServiceOffert);


    }

    private void loadEntries() {

        databaseService.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postsnapshot : dataSnapshot.getChildren()){
                    Service value = postsnapshot.getValue(Service.class);

                    if (!(ServiceOffert.contains(value.getServiceName()))){
                        spinnerArrayAdapter.add(value.getServiceName());
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value

            }
        });
    }


    private void loadOrg() {


        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Users user = dataSnapshot.child(iduser).getValue(Users.class);

                    cuser = new Users(user.getId(), user.get_firstname(), user.get_lastname(), user.get_email(), user.get_type(), user.get_currentOrganisation());
                    corganisation = user.get_currentOrganisation();
                    ch = user.get_currentOrganisation().get_organisationHorraire();


                organisationName.setText(corganisation.get_organisationName());
                organisationDescription.setText(corganisation.get_organisationDescription());
                organistionSwitch.setChecked(corganisation.get_isLiscenced());
                ArrayList<String> s = new ArrayList<>();

                for (int i = 0; i < corganisation.get_services().size(); i++){
                    s.add(corganisation.get_services().get(i).getServiceName());
                }

                ServiceOffert.clear();
                addSer();

                for (int i =0; i < s.size(); i++){
                    ServiceOffert.add(s.get(i));
                    addSer();
                    spinnerArrayAdapter.remove(ServiceOffert.get(i));
                    spinnerArrayAdapter.notifyDataSetChanged();
                }
                s.clear();
                ListArrayAdapter.notifyDataSetChanged();



                Log.d("DEBUG", "Value is: " + cuser);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value

            }
        });



    }

    private void addSer(){

        Query nameQuery = FirebaseDatabase.getInstance().getReference().child("Services").orderByChild("serviceName");
        nameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0){
//
                    ser.clear();
                    for (DataSnapshot data : dataSnapshot.getChildren()){
                        Service value = data.getValue(Service.class);

                        if (ServiceOffert.contains(value.getServiceName())){

                            ser.add(value);

                            Log.d("SER", String.valueOf(ser.size()));
                        }
                    }

                }else{
                  toastMessage("No services founded");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addOrganisation() {
        //getting the value
        String orgname = organisationName.getText().toString();



        Query nameQuery = FirebaseDatabase.getInstance().getReference().child("Users").child("_currentOrganisation").orderByChild("_organisationName").equalTo(orgname);
        nameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0){
//                    flag = "";
                    toastMessage("Organisation name already exist");

                }else{
//                    flag = "Ok";
                    String orgname = organisationName.getText().toString();
                    String orgdescription = organisationDescription.getText().toString();
                    //checking if the value is provided
                    if (!TextUtils.isEmpty(orgname) && !TextUtils.isEmpty(orgdescription) && !ServiceOffert.isEmpty()){


                        //creating a Product
                        if(cadd.get_sname() == "") {
                            cadd = new Address();
                        }else if (!(ch.is_flag())){
                            ch = new Horraire();
                        }

                        //getting a unique id using push().getKey() method
                        //it will create a unique id and will use it as the Primary Key for our Product
                        String id = databaseUser.push().getKey();



                        Organisation organisation = new Organisation(id,orgname, orgdescription, organistionSwitch.isChecked(), cadd, ser, ch, rate, resa);

                        cuser.set_currentOrganisation(organisation);

                        //Saving the Product
                        databaseUser.child(iduser).setValue(cuser);

////                        //setting edittext to blank again
//                         numStreet.setText("");
//                        streetName.setText("");
//                        codePostal.setText("");
//                        ville.setText("");
//                        pays.setText("");

                        //displaying a success toast
                        toastMessage("Organisation added");
                    }else{
                        //if th value is not given displaying a toast
                        toastMessage("Please eall required fields");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void toastMessage (String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }



}

