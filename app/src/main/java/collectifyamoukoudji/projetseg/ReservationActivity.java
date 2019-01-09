package collectifyamoukoudji.projetseg;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.app.Activity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class ReservationActivity extends AppCompatActivity {

    private CalendarView calendar;
    private ArrayList<String> heures;
    private Button enr;
    private String iduser;
    private DatabaseReference databaseUsers;
    private DatabaseReference reference;
    private ArrayList<String> selected;
    private CheckBox[] checkBoxes;
    private CheckBox b1, b2, b3, b4, b5, b6;
    private CheckBox b7, b8, b9, b10, b11, b12;
    private String cUser;
    private String fUser;
    private Users fournisseur;
    private Users client;
    private Spinner spinner;
    private ArrayAdapter<String> spinnerArrayAdapter;
    private Long date;
    private int index;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        cUser = getIntent().getStringExtra("client");
        fUser = getIntent().getStringExtra("fournisseur");
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");

        getFourClient();
        setupUI();
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);
        enr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enregistrer();
            }
        });

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){

            @Override
            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int dayOfMonth) {


                Calendar calenda = Calendar.getInstance();
                calenda.set(year, month, dayOfMonth);
                date = calenda.getTime().getTime();


                DateFormat day = new SimpleDateFormat("E");
                String dayName = day.format(date);
                int pos = getDayPosition(dayName);
                if(fournisseur !=null && client !=null) {
                    ArrayList<ArrayList<Boolean>> h = fournisseur.get_currentOrganisation().get_organisationHorraire().get_array();
                    for (int i = 0; i < 12; i++) {

                        if (!(h.get(i)).get(pos)){
                            checkBoxes[i].setEnabled(false);
                            checkBoxes[i].setChecked(false);

                        }else{
                            checkBoxes[i].setEnabled(true);
                        }
                    }
                    ArrayList<Reservation> h2 = fournisseur.get_currentOrganisation().getReservations();

                    for(Reservation res: h2) {
                        dayName = day.format(res.getrDate());
                        pos = getDayPosition(dayName);
                        day = new SimpleDateFormat("yyyy/MM/dd");
                        dayName = day.format(res.getrDate());

                        index = res.getTimeslt();

                        if(dayName.equals(day.format(date).toString())) {
//                            toastMessage(dayName+" lol "+ day.format(date));
//                            toastMessage(String.valueOf(index));
                            checkBoxes[index].setEnabled(false);
                            checkBoxes[index].setChecked(false);

                        }
                    }
                }
                //toastMessage(""+pos);
            }

        });

       /* calendar.setOnDateChangeListener(new OnDateChange() {
            @Override
            public void onClick(View v) {
                Long date = calendar.getDate();
                Date selectedDate = new Date(date);
                DateFormat day = new SimpleDateFormat("E");
                String dayName = day.format(date);
                int pos = getDayPosition(dayName);
                ArrayList<ArrayList<Boolean>> h = fournisseur.get_currentOrganisation().get_organisationHorraire().get_array();
                for (int i = 0; i < 12; i++) {
                    if (!(h.get(pos)).get(i))
                        checkBoxes[i].setEnabled(false);
                }
            }
        });*/
    }



    private void setupUI(){
        calendar = (CalendarView)findViewById(R.id.calendar);
        long currentDateTime = System.currentTimeMillis();
        calendar.setMinDate(currentDateTime);

        spinner = (Spinner) findViewById(R.id.spinnerServ);
        spinnerArrayAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item);
        enr = (Button)findViewById(R.id.btnEnregistrer);
        /*Date currentDate = new Date(currentDateTime);
        DateFormat df = new SimpleDateFormat("dd:MM:yy");*/

        //  long nbre = date.toDateTimeAtCurrentTime().getMillis();
        /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("A");
        long nombre =  Long.parseLong(date.format(formatter));
        */

        heures = new ArrayList();
        heures.add("8 - 9h");
        heures.add("9 - 10h");
        heures.add("10 - 11h");
        heures.add("11 - 12h");
        heures.add("12 - 13h");
        heures.add("13 - 14h");
        heures.add("14 - 15h");
        heures.add("15 - 16h");
        heures.add("16 - 17h");
        heures.add("17 - 18h");
        heures.add("18 - 19h");
        heures.add("19 - 20h");

        checkBoxes = new CheckBox[12];
        b1 = (CheckBox)findViewById(R.id.box1);
        checkBoxes[0] = b1;
        b2 = (CheckBox)findViewById(R.id.box2);
        checkBoxes[1] = b2;
        b3 = (CheckBox)findViewById(R.id.box3);
        checkBoxes[2] = b3;
        b4 = (CheckBox)findViewById(R.id.box4);
        checkBoxes[3] = b4;
        b5 = (CheckBox)findViewById(R.id.box5);
        checkBoxes[4] = b5;
        b6 = (CheckBox)findViewById(R.id.box6);
        checkBoxes[5] = b6;
        b7 = (CheckBox)findViewById(R.id.box7);
        checkBoxes[6] = b7;
        b8 = (CheckBox)findViewById(R.id.box8);
        checkBoxes[7] = b8;
        b9 = (CheckBox)findViewById(R.id.box9);
        checkBoxes[8] = b9;
        b10 = (CheckBox)findViewById(R.id.box10);
        checkBoxes[9] = b10;
        b11 = (CheckBox)findViewById(R.id.box11);
        checkBoxes[10] = b11;
        b12 = (CheckBox)findViewById(R.id.box12);
        checkBoxes[11] = b12;

       /* DateFormat day = new SimpleDateFormat("E");
        String dayName = day.format(currentDateTime);
        int pos = getDayPosition(dayName);
        if(fournisseur !=null) {
            ArrayList<ArrayList<Boolean>> h = fournisseur.get_currentOrganisation().get_organisationHorraire().get_array();
            for (int i = 0; i < 12; i++) {
                if (!(h.get(i)).get(pos)) checkBoxes[i].setEnabled(false);
                else checkBoxes[i].setEnabled(true);
            }
        }
        /*ArrayList<ArrayList<Boolean>> h = fournisseur.get_currentOrganisation().get_organisationHorraire().get_array();
        for (int i = 0; i < 12; i++) {
            if (!(h.get(dayPosition)).get(i))
                checkBoxes[i].setEnabled(false);
        }*/
        // getFourClient();

    }

    /////////////////////////////////////////////////////////////////////////////

    private int getDayPosition(String dayName){
        int dayPosition;
        switch (dayName){
            case "lun.":
            case "Mon":
                dayPosition = 0;
                return dayPosition;
            case "mar.":
            case "Tue":
                dayPosition = 1;
                return dayPosition;
            case "mer.":
            case "Wed":
                dayPosition = 2;
                return dayPosition;
            case "jeu.":
            case "Thu":
                dayPosition = 3;
                return dayPosition;
            case "ven.":
            case "Fri":
                dayPosition = 4;
                return dayPosition;
            case "sam.":
            case "Sat":
                dayPosition = 5;
                return dayPosition;
            case "dim.":
            case "Sun":
                dayPosition = 6;
                return dayPosition;
            default:
                dayPosition = -1;
                return dayPosition;
        }
    }

    public void enregistrer(){


        for (int i = 0; i < 12; i++) {
            if (checkBoxes[i].isChecked())
                index = i;
        }
        String id = databaseUsers.child(fUser).push().getKey();

        reference  =FirebaseDatabase.getInstance().getReference("Users").child(fUser).child("_currentOrganisation");

        //toastMessage(spinner.getSelecedItem().toString());

        Reservation rdv = new Reservation(id, fournisseur.get_currentOrganisation().get_organisationName(),
                (fournisseur.get_firstname() + " " + fournisseur.get_lastname()), (client.get_firstname() + " " + client.get_lastname()),
                date, spinner.getSelectedItem().toString(),index);

        Organisation org =  fournisseur.get_currentOrganisation();

        ArrayList<Reservation> l = fournisseur.get_currentOrganisation().getReservations();

        l.add(rdv);

        org.setReservations(l);



        reference.setValue(org);
        toastMessage("Reservation ajouter");
    }



    ///////////////////////////////////////////////////////////////////////////////

    private void getFourClient(){
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(fUser != null && cUser != null){
                    Users user = dataSnapshot.child(fUser).getValue(Users.class);
                    fournisseur = new Users(user.getId(), user.get_firstname(), user.get_lastname(), user.get_email(), user.get_type(), user.get_currentOrganisation());
                    ArrayList<ArrayList<Boolean>> h = fournisseur.get_currentOrganisation().get_organisationHorraire().get_array();
                    date = calendar.getDate();
                    DateFormat day = new SimpleDateFormat("E");
                    String dayName = day.format(date);
                    for (Service s:fournisseur.get_currentOrganisation().get_services()){
                        spinnerArrayAdapter.add(s.getServiceName());
                    }
                    int pos = getDayPosition(dayName);
                    //toastMessage(dayName);
                    for (int i = 0; i < 12; i++) {
                        if (!(h.get(i)).get(pos)) {
                            checkBoxes[i].setEnabled(false);
                            checkBoxes[i].setChecked(false);
                        } else {
                            checkBoxes[i].setEnabled(true);
                        }

                    }
                    ArrayList<Reservation> h2 = fournisseur.get_currentOrganisation().getReservations();

                    for(Reservation res: h2) {
                        dayName = day.format(res.getrDate());
                        pos = getDayPosition(dayName);
                        day = new SimpleDateFormat("yyyy/MM/dd");
                        dayName = day.format(res.getrDate());

                        index = res.getTimeslt();

                        if(dayName.equals(day.format(date).toString())) {
//                            toastMessage(dayName+" lol "+ day.format(date));
//                            toastMessage(String.valueOf(index));
                            checkBoxes[index].setEnabled(false);
                            checkBoxes[index].setChecked(false);

                        }
                    }
                    Log.d("DEBUG", "Value is: " + fournisseur);

                    client = dataSnapshot.child(cUser).getValue(Users.class);
                    Log.d("DEBUG", "Value is: " + client);
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
