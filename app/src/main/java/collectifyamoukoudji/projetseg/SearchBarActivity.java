package collectifyamoukoudji.projetseg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchBarActivity extends AppCompatActivity {
    EditText search_edit_text;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    ArrayList<String> serviceList;
    ArrayList<String> rateList;
    ArrayList<String> hoursList;
    ArrayList<String> idList;
    SearchAdapter searchAdapter;
    Users cuser;
    ArrayList<ArrayList<Boolean>> plageHorraire;
    ArrayList<String> days;
    ArrayList<String> times;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bar);

         days = new ArrayList<>();
         times = new ArrayList<>();

        days.add("LUN");
        days.add("MAR");
        days.add("MER");
        days.add("JED");
        days.add("VEN");
        days.add("SAM");
        days.add("DIM");

        times.add("8-9h");
        times.add("9-10h");
        times.add("10-11h");
        times.add("11-12h");
        times.add("12-13h");
        times.add("13-14h");
        times.add("14-15h");
        times.add("15-16h");
        times.add("16-17h");
        times.add("17-18h");
        times.add("18-19h");
        times.add("19-20h");


        search_edit_text = (EditText) findViewById(R.id.search_edit_text);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        /*
        * Create a array list for each node you want to use
        * */
        serviceList = new ArrayList<>();
        rateList = new ArrayList<>();
        hoursList = new ArrayList<>();
        idList = new ArrayList<>();

        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    setAdapter(s.toString());
                } else {
                    /*
                    * Clear the list when editText is empty
                    * */
                    serviceList.clear();
                    rateList.clear();
                    recyclerView.removeAllViews();
                }
            }
        });

    }

    private void setAdapter(final String searchedString) {
        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*
                * Clear the list for every new search
                * */
                serviceList.clear();
                rateList.clear();
                hoursList.clear();
                idList.clear();
                recyclerView.removeAllViews();

                int counter = 0;

                /*
                 * Search all users for matching searched string
                 * */
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    cuser = snapshot.getValue(Users.class);
                    String user_type = cuser.get_type();
                    if(user_type.equals("Fournisseur de services")){
                        ArrayList<String> s = new ArrayList<>();
                        ArrayList<Double> r = new ArrayList<>();
                        ArrayList<String> ids = new ArrayList<>();
                        ArrayList<String> a = new ArrayList<>();
                        ArrayList<String> t = new ArrayList<>();


                        ids.add(snapshot.getKey());


                        cuser.get_currentOrganisation().get_organisationHorraire().get_array();

                        plageHorraire = cuser.get_currentOrganisation().get_organisationHorraire().get_array();

                        for (int i = 0; i < 12 ; i++) {
                            for (int j = 0; j < 7; j++) {
                                if(plageHorraire.get(i).get(j).equals(true)){
                                    a.add(days.get(j)+"-"+times.get(i));
                                    t.add(times.get(i));
                                }

                            }
                        }

                        plageHorraire.clear();

                        for(int i = 0; i < cuser.get_currentOrganisation().get_services().size(); i++){

                            s.add(cuser.get_currentOrganisation().get_services().get(i).getServiceName());
                            r.add(cuser.get_currentOrganisation().get_services().get(i).getRate());
                        }

                        String service_name = s.toString();
                        String rate = r.toString();
                        String hours = t.toString();
                        String uid = ids.get(0);

                        if (service_name.toLowerCase().contains(searchedString.toLowerCase())) {
                            serviceList.add(service_name);
                            rateList.add(rate);
                            hoursList.add(hours);
                            idList.add(uid);
                            counter++;
                        }else if(rate.toLowerCase().contains(searchedString.toLowerCase())){
                            serviceList.add(service_name);
                            rateList.add(rate);
                            hoursList.add(hours);
                            idList.add(uid);
                            counter++;
                        }else if(hours.toLowerCase().contains(searchedString.toLowerCase())){
                            serviceList.add(service_name);
                            rateList.add(rate);
                            hoursList.add(hours);
                            idList.add(uid);
                            counter++;
                        }

                        /*
                         * Get maximum of 15 searched results only
                         * */
                        if (counter == 15)
                            break;
                    }else {

                    }
                }

                searchAdapter = new SearchAdapter(SearchBarActivity.this, serviceList, rateList, hoursList, idList);
                recyclerView.setAdapter(searchAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
