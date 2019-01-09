package collectifyamoukoudji.projetseg;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class ClientActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle to;
    private NavigationView nv;
    Bundle bundle;
    private String iduser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        bundle = new Bundle();


        dl = (DrawerLayout)findViewById(R.id.drawer_layout2);
        to = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(to);
        to.syncState();


        iduser = getIntent().getStringExtra("iduser");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final android.support.v4.app.FragmentManager  fragmentManager = getSupportFragmentManager();

        Bundle arguments = new Bundle();
        arguments.putString("iduser", iduser);



        search_fragment mySFragment = new search_fragment();
        mySFragment.setArguments(arguments);
        fragmentManager.beginTransaction().replace(R.id.content_frame2, mySFragment).commit();


        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                Bundle arguments = new Bundle();
                arguments.putString("iduser", iduser);

                 if(id == R.id.nav_Search){
                    search_fragment myFragment = new search_fragment();
                    myFragment.setArguments(arguments);
                    fragmentManager.beginTransaction().replace(R.id.content_frame2, myFragment).commit();
                }else if(id == R.id.nav_Resa){
                     ClientResaFragment myFragment = new ClientResaFragment();
                     myFragment.setArguments(arguments);
                     fragmentManager.beginTransaction().replace(R.id.content_frame2, myFragment).commit();
                 }else{
                    return false;
                }
                return  true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(to.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    private void openFour(){

        Intent intent = new Intent(this, SearchBarActivity.class);
        startActivity(intent);
    }
}
