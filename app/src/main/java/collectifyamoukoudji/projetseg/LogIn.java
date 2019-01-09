package collectifyamoukoudji.projetseg;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LogIn extends AppCompatActivity {

    private static final String TAG = "LogIn";

    private EditText userEmail;
    private EditText userPassword;

    private String userID;


    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener mAuth;
    private FirebaseDatabase db;
    private Button btnSignIn;
    private FirebaseUser user;
    private Users cUser;
    private DatabaseReference databaseUsers;
    private DatabaseReference dR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        auth = FirebaseAuth.getInstance();

        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");



        mAuth = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                 user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//                    toastMessage("Successfully signed in with: " + user.getEmail());
                    userID = user.getUid();

                    String type = FirebaseDatabase.getInstance().getReference("Users").child(userID).getKey();

//                    toastMessage(type);

                }else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
//                    toastMessage("Successfully signed out.");
                }
            }
        };

        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if(user != null){
                    Users value = dataSnapshot.child(userID).getValue(Users.class);
                    Log.d(TAG, "Value is: " + value);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                toastMessage("Failed to alter database.");
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });


        userEmail = (EditText) findViewById(R.id.email);
        userPassword = (EditText) findViewById(R.id.password);

        btnSignIn = (Button) findViewById(R.id.login);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlphaAnimation alpha = new AlphaAnimation(0f, 1f);
                alpha.setDuration(500);
                if(confirm()){

                    btnSignIn.startAnimation(alpha);
                    logIn();
                }

            }
        });



        super.onStart();
        auth.addAuthStateListener(mAuth);
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.signOut();
        auth.removeAuthStateListener(mAuth);
    }




    private void logIn(){

        String email = userEmail.getText().toString();
        String password  = userPassword.getText().toString();

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    // there was an error
                    toastMessage("Entrez les bonnes informatiosn d'utilisateur");
                } else {

                    Query nameQuery = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
                    nameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getChildrenCount() > 0){

                                Users ds = dataSnapshot.getValue(Users.class);
                                if(ds.get_type().equals("Administrateur")){
                                    //toastMessage("Admin");
                                    openAdmin();
                                    finish();

                                }else if(ds.get_type().equals("Fournisseur de services")) {
                                    //toastMessage("Fournisseur de services");
                                    openFour();
                                    finish();
                                }else {
                                    //toastMessage("Client");
                                    openWelcome();
                                    finish();
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });

    }

    public boolean confirm(){
        Boolean valide = false;

        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString().trim();

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Entrer tout les details svp.", Toast.LENGTH_SHORT).show();
        }else {
            valide = true;
        }

        return valide;
    }

    private void openWelcome(){
        Intent intent = new Intent(this, ClientActivity.class);
        intent.putExtra("iduser", user.getUid().toString());
        startActivity(intent);
    }

    private void openAdmin(){

        Intent intent = new Intent(this, AdminActivity.class);
        intent.putExtra("iduser", user.getUid().toString());
        startActivity(intent);
    }

    private void openFour(){

        Intent intent = new Intent(this, FournisseurActivity.class);
        intent.putExtra("iduser", user.getUid().toString());
        startActivity(intent);
    }

    private void toastMessage (String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}