package collectifyamoukoudji.projetseg;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class LogInActivityTest2 {
    @Rule
    public ActivityTestRule<LogIn> mActivityTestRule = new
            ActivityTestRule<>(LogIn.class);

    //EXPECTED TO PASS
    @Test
    public void AdminLoginIsValid() throws InterruptedException {


        onView(withId(R.id.email)).perform(typeText("admin@admin.com"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("1234admin"), closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());
        Thread.sleep(2500);
        isDisplayed();
    }
    //EXPECTED TO PASS
    @Test
    public void FourLoginIsValid() throws InterruptedException {


        onView(withId(R.id.email)).perform(typeText("f@f.com"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("1234test"), closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());
        Thread.sleep(2500);
        isDisplayed();
    }

    //EXPECTED TO PASS
    @Test
    public void ClientLoginIsValid() throws InterruptedException {


        onView(withId(R.id.email)).perform(typeText("client@client.com"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("1234client"), closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());
        Thread.sleep(2500);
        isDisplayed();
    }

}
