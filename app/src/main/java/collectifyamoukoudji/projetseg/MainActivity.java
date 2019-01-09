package collectifyamoukoudji.projetseg;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button login;
    private Button singin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();
        final AlphaAnimation alpha = new AlphaAnimation(0f, 1f);
        alpha.setDuration(500);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.startAnimation(alpha);
                openLogin();
//                finish();
            }
        });

        singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignup();
                singin.startAnimation(alpha);
//                finish();
            }
        });
    }

    public void openLogin(){
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }
    public void openSignup(){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
    public void setupUI(){
        login = (Button)findViewById(R.id.btnLogin);
        singin = (Button)findViewById(R.id.btnSignUp);
    }
}
