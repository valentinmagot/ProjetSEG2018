package collectifyamoukoudji.projetseg;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class AdminActivityTest {


    @Rule
    public ActivityTestRule<LogIn> mActivityTestRule = new
            ActivityTestRule<>(LogIn.class);
    //EXPECTED TO PASS
    @Test
    public void btnUserIsValid() throws InterruptedException {
        onView(withId(R.id.email)).perform(typeText("admin@admin.com"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("1234admin"), closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());
        Thread.sleep(2500);
        onView(withId(R.id.btnUtilisateur)).perform(click()); // click users
        Thread.sleep(2500);
    }
//    EXPECTED TO PASS
    @Test
    public void btnServiceIsValid() throws InterruptedException {
        onView(withId(R.id.email)).perform(typeText("admin@admin.com"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("1234admin"), closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());
        Thread.sleep(2500);
        onView(withId(R.id.btnServices)).perform(click()); // click services
        Thread.sleep(2500);
    }
}
