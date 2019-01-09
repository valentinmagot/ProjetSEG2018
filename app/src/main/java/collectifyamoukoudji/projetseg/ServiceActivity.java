package collectifyamoukoudji.projetseg;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ServiceActivity extends AppCompatActivity {

    private EditText editTextService;
    private EditText editTextRate;
    private ListView listViewServices;
    private Button buttonAddService;
    private DatabaseReference databaseServices;
    private DatabaseReference updateReference;
    private Service updatedService;
    List<Service> services;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        setupUI();

        buttonAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduct();
            }
        });

        listViewServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service = services.get(i);
                showUpdateDeleteDialog(service.getId(), service.getServiceName(), service.getRate());
                return true;
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseServices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                services.clear();
                for(DataSnapshot postDataSnapshot : dataSnapshot.getChildren()) {
                    //getting product
                    Service service = postDataSnapshot.getValue(Service.class);

                    services.add(service);
                }

                //creatig adapter
                ServiceList productAdapter = new ServiceList(ServiceActivity.this,services);
                //attaching adapter to Listview
                listViewServices.setAdapter(productAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }




    private void showUpdateDeleteDialog(final String productId, final String productName, final double prix) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextService);
        final EditText editTextPrice  = (EditText) dialogView.findViewById(R.id.editTextRate);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateProduct);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteProduct);

        dialogBuilder.setTitle(productName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String stringPrice = String.valueOf(editTextPrice.getText().toString());

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(stringPrice)) {
                    System.out.println("a ete cliqué");
                    double price = Double.parseDouble(stringPrice);
                    updateProduct(productId, name, price, true);
                    b.dismiss();
                }else if(!TextUtils.isEmpty(name)){
                    System.out.println("a ete cliqué");
                    updateProduct(productId, name, prix, true);
                    b.dismiss();
                }
                else if(!TextUtils.isEmpty(stringPrice)){
                    System.out.println("a ete cliqué");
                    double price = Double.parseDouble(stringPrice);
                    updateProduct(productId, productName, price, false);
                    b.dismiss();
                }else {
                    toastMessage("Entrer toutes les informations");
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct(productId);
                b.dismiss();
            }
        });
    }

    private void updateProduct(String id, String name, double price, boolean flag) {
        //getting the specified product
        updateReference =FirebaseDatabase.getInstance().getReference("Services").child(id);
        //updating product
        updatedService = new Service(id,name,price);
        if(flag){

            Query nameQuery = FirebaseDatabase.getInstance().getReference().child("Services").orderByChild("serviceName").equalTo(updatedService.getServiceName());
            nameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getChildrenCount() > 0){
                        toastMessage("Service name already exist");
                    }else{
                        updateReference.setValue(updatedService);
                        toastMessage("Service Updated");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }else {
            updateReference.setValue(updatedService);
            toastMessage("Service Updated");
        }



    }

    private boolean deleteProduct(String id) {

        //getting the specified product
        DatabaseReference dR=FirebaseDatabase.getInstance().getReference("Services").child(id);
        //removing product
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Service Deleted", Toast.LENGTH_LONG).show();
        return true;
    }

    private void addProduct() {
        //getting the value

        String name = editTextService.getText().toString();

        Query nameQuery = FirebaseDatabase.getInstance().getReference().child("Services").orderByChild("serviceName").equalTo(name);
        nameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0){
//                    flag = "";
                    toastMessage("Service name already exist");

                }else{
//                    flag = "Ok";
                    String name = editTextService.getText().toString();
                    String stringPrice = String.valueOf(editTextRate.getText().toString());
                    //checking if the value is provided
                    if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(stringPrice)){



                        double price = Double.parseDouble(stringPrice);

                        //getting a unique id using push().getKey() method
                        //it will create a unique id and will use it as the Primary Key for our Product
                        String id = databaseServices.push().getKey();

                        //creating a Product
                        Service service = new Service(id,name,price);

                        //Saving the Product
                        databaseServices.child(id).setValue(service);

                        //setting edittext to blank again
                        editTextService.setText("");
                        editTextRate.setText("");

                        //displaying a success toast
                        toastMessage("Service added");
                    }else{
                        //if th value is not given displaying a toast
                        toastMessage("Please enter a name and a price");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    public void setupUI(){
        editTextService = (EditText) findViewById(R.id.editTextService);
        editTextRate = (EditText) findViewById(R.id.editTextRate);
        listViewServices = (ListView) findViewById(R.id.listViewServices);
        buttonAddService = (Button) findViewById(R.id.addButton);


        databaseServices = FirebaseDatabase.getInstance().getReference("Services");

        services = new ArrayList<>();

    }

    private void toastMessage (String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
