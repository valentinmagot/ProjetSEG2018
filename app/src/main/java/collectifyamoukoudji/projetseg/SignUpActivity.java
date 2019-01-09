package collectifyamoukoudji.projetseg;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignUpActivity extends AppCompatActivity {

    //Instance variables ************************************************

    /**
     * Contains a EditText type of field
     * that is assigned for the fistname of the user.
     */
    private EditText prenom;
    /**
     * Contains a EditText type of field
     * that is assigned for the lastname of the user.
     */
    private EditText nom;
    /**
     * Contains a EditText type of field
     * that is assigned for the emailAdress of the user.
     */
    private EditText adressemail;
    /**
     * Contains a EditText type of field
     * that is assigned for the password of the user.
     */
    private EditText mdp;
    /**
     * Contains a EditText type of field
     * that is assigned for the password of the user.
     */
    private EditText confmdp;
    /**
     * Contains a Button type of field
     * that is assigned for the register button of the page.
     */
    private Button btnContinuer;
    /**
     * Contains a Spinner type of field
     * that contains users type of accounts.
     */
    private Spinner spinner;
    /**
     * Contains a String id
     * that is assigned for the user id.
     */
    private String id;
    /**
     * Contains a list of users type of account
     * that is assigned the user to choose from.
     */
    private List<String> plantsList;
    private ArrayAdapter<String> spinnerArrayAdapter;
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
     * Contains a FirebaseUser
     * that is assigned for users of the Firebase database.
     */
    private FirebaseUser databaseUser;
    /**
     * Contains a User variable
     * that is assigned for new client of the db.
     */
    private Users cl;
    /**
     * Contains a String
     * that is assigned for Debug purpose.
     */
    private static final String TAG = "Signup";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth = firebaseAuth.getInstance();


        setupUI();



        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users value = dataSnapshot.getValue(Users.class);
                Log.d(TAG, "Value is: " + value);

                for (DataSnapshot postsnapshot : dataSnapshot.getChildren()){

                    if(postsnapshot != null) {
                        value = postsnapshot.getValue(Users.class);
                        Log.d(TAG, "Value is: " + value.get_type());
                        if (value.get_type().equals("Administrateur") && plantsList.size() > 2) {
                            plantsList.remove(2);
                            spinnerArrayAdapter.notifyDataSetChanged();
                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                toastMessage("Failed to alter database.");
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });

        btnContinuer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlphaAnimation alpha = new AlphaAnimation(0f, 1f);
                alpha.setDuration(500);
                if(confirm()&& valider()){

                    btnContinuer.startAnimation(alpha);
                    toastMessage("Enregistrement...");
                    logIn();

                }

            }
        });
    }
    /**
     * Verifies the user email address and password.
     *
     * @return An boolean if the user information are valid or not.
     */
    private boolean valider() {
        String user_email = adressemail.getText().toString().trim();
        String user_mdp = mdp.getText().toString().trim();
        String user_mdpconf = confmdp.getText().toString().trim();
        if ( isEmailAdress(user_email)){
            if (user_mdp.equals(user_mdpconf)){
                return true;
            }else{
                System.out.println("\n\n\n\n\n\n\nmdp a echoué:"+user_mdp+" "+user_mdpconf+" "+user_email);
                Toast.makeText(SignUpActivity.this, "Votre motdepasse ne correspond pas!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else {
            System.out.println("\n\n\n\n\n\n\nemail a echoué:"+user_mdp+" "+user_mdpconf+" "+user_email);
            Toast.makeText(SignUpActivity.this, "Veuillez rentrer une adresse email valide!", Toast.LENGTH_SHORT).show();
            return false;
        }
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
    /**
     * Setting up all the fields of the interface.
     *
     */
    public void setupUI(){

        prenom = (EditText)findViewById(R.id.editTextPrenom);
        nom = (EditText)findViewById(R.id.editTextNom);
        adressemail = (EditText)findViewById(R.id.editTextEmail);
        mdp = (EditText)findViewById(R.id.mdp);
        confmdp = (EditText)findViewById(R.id.confmdp);
        btnContinuer = (Button) findViewById(R.id.btnContinuer);
        spinner = (Spinner)findViewById(R.id.typeDecompte);
        // Initializing a String Array
        String[] plants = new String[]{
                "Clients",
                "Fournisseur de services",
                "Administrateur"
        };

        plantsList = new ArrayList<>(Arrays.asList(plants));

        // Initializing an ArrayAdapter
        spinnerArrayAdapter = new ArrayAdapter<>(
                this,R.layout.spinner_item,plantsList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);
    }
    /**
     * Verifies that all the fields required in the form are populated.
     *
     * @return An boolean if the user information are valid or not.
     */
    public boolean confirm(){
        Boolean valide = false;

        String fname = prenom.getText().toString();
        String lname = nom.getText().toString();
        String email = adressemail.getText().toString();
        String password = mdp.getText().toString().trim();
        String passwordconf = confmdp.getText().toString().trim();

        if(fname.isEmpty() || lname.isEmpty() || email.isEmpty() || password.isEmpty() || passwordconf.isEmpty()){
            Toast.makeText(this,"Entrer tout les details svp.", Toast.LENGTH_SHORT).show();
        }else {
            valide = true;
        }

        return valide;
    }
    /**
     * Register the user into the FirebaseDatabase.
     *
     */
    private  void logIn(){
        final String user_email = adressemail.getText().toString().trim();
        final String user_mdp = mdp.getText().toString().trim();

        firebaseAuth.createUserWithEmailAndPassword(user_email, user_mdp).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "Enregistrement non reussi!", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(SignUpActivity.this, "Enregistrement reussi", Toast.LENGTH_SHORT).show();
                    addUser();
                    prenom.setText(null);
                    nom.setText(null);
                    adressemail.setText(null);
                    confmdp.setText(null);
                    mdp.setText(null);

                }
            }
        });

    }
    /**
     * Adding the user information to the Realtime database
     * along with starting the appropriated activity depending
     * on the type of user account.
     *
     */
    private void addUser(){

        String fname = prenom.getText().toString();
        String lname = nom.getText().toString();
        String email = adressemail.getText().toString();
        String type = spinner.getSelectedItem().toString();

        if (!TextUtils.isEmpty(email)){

            databaseUser = firebaseAuth.getCurrentUser();

            id = databaseUser.getUid();
            cl = new Users(id, fname, lname, email, type);

//            user =  client;
            databaseUsers.child(id).setValue(cl);

            Toast.makeText(this, "Information ajouté à la base de donnée", Toast.LENGTH_LONG).show();
            if(cl.get_type().equals("Administrateur")){
                openAdmin();
            }else if(cl.get_type().equals("Fournisseur de services")){
                Organisation organisation = new Organisation();
                cl = new Users(id, fname, lname, email, type, organisation);
                databaseUsers.child(id).setValue(cl);
                openFour();
            }else {
                openWelcome();
            }

        }
        else {
            Toast.makeText(this, "Courriel manquant", Toast.LENGTH_SHORT).show();
        }


    }
    /**
     * Opens welcome activity.
     *
     */
    private void openWelcome(){

        Intent intent = new Intent(this, ClientActivity.class);
        intent.putExtra("iduser", id);
        startActivity(intent);
    }
    /**
     * Opens fournisseur activity.
     *
     */
    private void openFour(){

        Intent intent = new Intent(this, FournisseurActivity.class);
        intent.putExtra("iduser", id);
        startActivity(intent);
    }
    /**
     * Opens Admin activity.
     *
     */
    private void openAdmin(){

        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
    }
    /**
     * Displaying toast message to the user.
     *
     */
    private void toastMessage (String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}



