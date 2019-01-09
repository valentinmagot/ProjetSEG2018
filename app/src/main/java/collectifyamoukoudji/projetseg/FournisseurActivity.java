package collectifyamoukoudji.projetseg;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class FournisseurActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    Bundle bundle;
    private String iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fournisseur);
        bundle = new Bundle();


                dl = (DrawerLayout)findViewById(R.id.drawer_layout);
                t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

                dl.addDrawerListener(t);
                t.syncState();


                iduser = getIntent().getStringExtra("iduser");

                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                final android.support.v4.app.FragmentManager  fragmentManager = getSupportFragmentManager();

                Bundle arguments = new Bundle();
                arguments.putString("iduser", iduser);


                OrganisationFragment myOFragment = new OrganisationFragment();
                myOFragment.setArguments(arguments);
                fragmentManager.beginTransaction().replace(R.id.content_frame, myOFragment).commit();


                nv = (NavigationView)findViewById(R.id.nv);
                nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int id = item.getItemId();

                        Bundle arguments = new Bundle();
                        arguments.putString("iduser", iduser);

                        if(id == R.id.nav_Adresse){
                            InformationFragment myFragment = new InformationFragment();
                            myFragment.setArguments(arguments);
                            fragmentManager.beginTransaction().replace(R.id.content_frame, myFragment).commit();
                        }else if(id == R.id.nav_Information){
                            ContactFragment myFragment = new ContactFragment();
                            myFragment.setArguments(arguments);
                            fragmentManager.beginTransaction().replace(R.id.content_frame, myFragment).commit();
                        }else if(id == R.id.nav_Shop){
                            OrganisationFragment myFragment = new OrganisationFragment();
                            myFragment.setArguments(arguments);
                            fragmentManager.beginTransaction().replace(R.id.content_frame, myFragment).commit();
                        }else if(id == R.id.nav_Resa) {
                            ResaFragment myFragment = new ResaFragment();
                            myFragment.setArguments(arguments);
                            fragmentManager.beginTransaction().replace(R.id.content_frame, myFragment).commit();
                        }else{
                           return false;
                        }
                        return  true;
                    }
                });


            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {

                if(t.onOptionsItemSelected(item))
                    return true;

                return super.onOptionsItemSelected(item);
            }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) return;

        //Getting the Avatar Image we show to our users
        ImageView avatarImage = (ImageView) findViewById(R.id.avatarImage);

        //Figuring out the correct image
        String drawableName = "ic_logo_00";
        switch (data.getIntExtra("imageID",R.id.teamid00)) {
            case R.id.teamid00:
                drawableName = "ic_logo_00";
                break;
            case R.id.teamid01:
                drawableName = "ic_logo_01";
                break;
            case R.id.teamid02:
                drawableName = "ic_logo_02";
                break;
            case R.id.teamid03:
                drawableName = "ic_logo_03";
                break;
            case R.id.teamid04:
                drawableName = "ic_logo_04";
                break;
            case R.id.teamid05:
                drawableName = "ic_logo_05";
                break;
            default:
                drawableName = "ic_logo_00";
                break;
        }
        int resID = getResources().getIdentifier(drawableName, "drawable",  getPackageName());
        avatarImage.setImageResource(resID);
    }

    private void toastMessage (String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}

