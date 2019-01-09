package collectifyamoukoudji.projetseg;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AfficherFourActivity extends AppCompatActivity {
    private TextView textViewAdresse;
    private TextView textViewTelephone;
    private TextView textViewCourriel;
    private TextView textViewDescription;
    private TextView textViewSiteWeb;
    private TextView textViewOrgName;
    private Button btnRate;
    private Button btnReserver;
    private DatabaseReference databaseUsers;
    private DatabaseReference rateReference;
    private String iduser;
    private Users cuser;
    private Organisation org;
    private double rate;
    private ArrayList<Double> previousrate;
    private  String c;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficherfour);
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
        iduser = getIntent().getStringExtra("idUser");
        c=getIntent().getStringExtra("iduser");
//        toastMessage(iduser);
        setupUI();
        getFournisseur();

        setupUI();
        //fillUI();


        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRateDialog(textViewOrgName.getText().toString());
            }
        });
        btnReserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reserverService();
            }
        });
    }

    private void reserverService() {
        Intent intent = new Intent(this, ReservationActivity.class);
        intent.putExtra("client", c);
        intent.putExtra("fournisseur", iduser);
        //toastMessage(iduser + "    lolgtdhgtdthdhdchfgcghcghxsezdadas1    "+c);
        startActivity(intent);

    }

    private void showRateDialog(final String orgName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.rating_dialog, null);
        dialogBuilder.setView(dialogView);

        final RatingBar ratingBar = (RatingBar) dialogView.findViewById(R.id.rating);
        final Button buttonRate = (Button) dialogView.findViewById(R.id.buttonRate);


        dialogBuilder.setTitle(orgName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //rate = rating;

                rate = Double.valueOf(rating);
            }
        });

        buttonRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//
                previousrate.add(rate);

                double sum = 0;

                for (Double r : previousrate){
                    sum+= r;
                }
//
                Double newRating = (sum/previousrate.size());

                rate(newRating);

                //toastMessage("Stars : " + rate);
                //toastMessage("newRating : " + newRating);



            }
        });

    }

    private void rate(Double newRating){


        rateReference =FirebaseDatabase.getInstance().getReference("Users").child(iduser).child("_currentOrganisation").child("_rating");

        rateReference.setValue(previousrate);

        toastMessage("Merci!");

    }


    private void setupUI() {
        textViewAdresse = (TextView)findViewById(R.id.textViewAdresse);
        textViewTelephone = (TextView)findViewById(R.id.textViewTelephone);
        textViewCourriel = (TextView)findViewById(R.id.textViewCourreil);
        textViewDescription = (TextView)findViewById(R.id.textViewDescription);
        textViewSiteWeb = (TextView)findViewById(R.id.textViewWeb);
        textViewOrgName = (TextView)findViewById(R.id.textViewNomOrg);
        btnRate = (Button)findViewById(R.id.buttonEvaluer);
        btnReserver = (Button)findViewById(R.id.buttonReserver);

    }

    public void getFournisseur() {
        iduser = getIntent().getStringExtra("idUser");
       // toastMessage(iduser);

        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(iduser != null){
                    Users user  = dataSnapshot.child(iduser).getValue(Users.class);
                    cuser = new Users(user.getId(), user.get_firstname(), user.get_lastname(), user.get_email(), user.get_type(), user.get_currentOrganisation());
                    previousrate = cuser.get_currentOrganisation().get_rating();
                    Log.d("DEBUG", "Value is: " + cuser);

                    String num = cuser.get_currentOrganisation().get_organisationAddress().get_num();
                    String sname = cuser.get_currentOrganisation().get_organisationAddress().get_sname();
                    String city = cuser.get_currentOrganisation().get_organisationAddress().get_city();
                    String pays = cuser.get_currentOrganisation().get_organisationAddress().get_country();
                    String pCode = cuser.get_currentOrganisation().get_organisationAddress().get_pcode();
                    String tel = cuser.get_currentOrganisation().get_organisationAddress().get_phonenum();
                    String email = cuser.get_currentOrganisation().get_organisationAddress().get_shopemail();
                    String description = cuser.get_currentOrganisation().get_organisationDescription();
                    String name = cuser.get_currentOrganisation().get_organisationName();
                    String webSite = cuser.get_currentOrganisation().get_organisationAddress().get_website();
                    textViewAdresse.setText(num+" "+sname+" ,"+city+" ,"+ pays+" ,"+pCode);
                    textViewTelephone.setText(tel);
                    textViewCourriel.setText(email);
                    textViewDescription.setText(description);
                    textViewSiteWeb.setText(webSite);
                    textViewOrgName.setText(name);
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
    private void toastMessage (String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}