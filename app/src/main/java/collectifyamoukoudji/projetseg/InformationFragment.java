package collectifyamoukoudji.projetseg;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InformationFragment extends Fragment{

    View myView;
    private EditText numStreet;
    private EditText streetName;
    private EditText codePostal;
    private EditText ville;
    private EditText pays;

    private EditText phone;
    private EditText mail;
    private EditText web;
    private Button enregistrer;
    private String iduser;
    private Users cuser;
    private Organisation corg;
    private Address cadd;
    private DatabaseReference databaseUser;
    private ArrayList<ArrayList<Boolean>> arr;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.information_layout, container, false);



        setupUI();

        loadAdd();

        enregistrer.setOnClickListener(enregisterListener);

        return myView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                         corg = user.get_currentOrganisation();
                         Log.d("DEBUG", "Value is: " + corg.toString());



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




    }

    public void setupUI(){

        numStreet = (EditText) myView.findViewById(R.id.editTextNumber);
        streetName = (EditText) myView.findViewById(R.id.editTextStreetName);
        codePostal = (EditText) myView.findViewById(R.id.editTextCP);
        ville = (EditText) myView.findViewById(R.id.editTextCity);
        pays = (EditText) myView.findViewById(R.id.editTextCountry);
        phone = (EditText) myView.findViewById(R.id.editTextPhone) ;
        mail = (EditText) myView.findViewById(R.id.editTextMail);
        web = (EditText) myView.findViewById(R.id.editTextWebsite);
        web.setText(Html.fromHtml(web.toString()));
        web.setMovementMethod(LinkMovementMethod.getInstance());
        enregistrer = (Button) myView.findViewById(R.id.btnEnregistrer);

    }

    private void toastMessage (String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public View.OnClickListener enregisterListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addAddress();
        }
    };

    private void loadAdd() {


        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Users user = dataSnapshot.child(iduser).getValue(Users.class);

                cuser = new Users(user.getId(), user.get_firstname(), user.get_lastname(), user.get_email(), user.get_type(), user.get_currentOrganisation());
                cadd = user.get_currentOrganisation().get_organisationAddress();

                numStreet.setText(cadd.get_num());
                streetName.setText(cadd.get_sname());
                codePostal.setText(cadd.get_pcode());
                ville.setText(cadd.get_city());
                pays.setText(cadd.get_country());
                phone.setText(cadd.get_phonenum());
                mail.setText(cadd.get_shopemail());
                web.setText(cadd.get_website());

                Log.d("DEBUG", "Value is: " + cuser);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value

            }
        });



    }

    private void addAddress() {
        //getting the value
        String codepostal = codePostal.getText().toString();



        Query nameQuery = FirebaseDatabase.getInstance().getReference().child("Users").child("Address").orderByChild("_codepostal").equalTo(codepostal);
        nameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0){
//                    flag = "";
                    toastMessage("Postal code already exist");

                }else{
//                    flag = "Ok";
                    String num = numStreet.getText().toString();
                    String streetname = streetName.getText().toString();
                    String codepostal = codePostal.getText().toString();
                    String city = ville.getText().toString();
                    String country = pays.getText().toString();
                    String phonenum = phone.getText().toString();
                    String email = mail.getText().toString();
                    String website = web.getText().toString();
                    //checking if the value is provided
                    if (!TextUtils.isEmpty(num) && !TextUtils.isEmpty(streetname) && !TextUtils.isEmpty(codepostal) && !TextUtils.isEmpty(city) && !TextUtils.isEmpty(country) && !TextUtils.isEmpty(phonenum) && !TextUtils.isEmpty(email) && isEmailAdress(email)){

                        //getting a unique id using push().getKey() method
                        //it will create a unique id and will use it as the Primary Key for our Product
                        String id = databaseUser.push().getKey();


                        //creating a Product
                        Address address = new Address(id,num,streetname,codepostal,city,country, phonenum, email, website);

                        corg.set_organisationAddress(address);
                        cuser.set_currentOrganisation(corg);


                        //Saving the Product
                        databaseUser.child(iduser).setValue(cuser);


                        //displaying a success toast
                        toastMessage("Address added");
                    }else{
                        //if th value is not given displaying a toast
                        toastMessage("Please all required fields and a valid email adress");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    /**
     * Verifies the format of the user email address.
     *
     *
     * @param email The user email address.
     * @return An boolean if the user information are valid or not.
     */
    public static boolean isEmailAdress(String email){
        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
        Matcher m = p.matcher(email.toUpperCase());
        return m.matches();
    }

}
