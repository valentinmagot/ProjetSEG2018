package collectifyamoukoudji.projetseg;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class MainActivityTest {



    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new
            ActivityTestRule<>(MainActivity.class);
    //EXPECTED TO PASS
    @Test
    public void btnLoginIsValid(){
        onView(withId(R.id.btnLogin)).perform(click()); // click login
    }
    //EXPECTED TO PASS
    @Test
    public void btnSignupIsValid(){
        onView(withId(R.id.btnSignUp)).perform(click()); // click signup
    }
}
