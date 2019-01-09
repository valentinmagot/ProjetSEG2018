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

public class LogInActivityTest {
    @Rule
    public ActivityTestRule<LogIn> mActivityTestRule = new
            ActivityTestRule<>(LogIn.class);

    //EXPECTED TO PASS
    @Test
    public void ClientLoginIsValid(){


        onView(withId(R.id.email)).perform(typeText("cl@cl.com"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("1234cl"), closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());
        isDisplayed();
    }
    //EXPECTED TO PASS
    @Test
    public void ClientEmailLoginIsInvalid(){

        onView(withId(R.id.email)).perform(typeText("cl1@cl.com"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("1234cl"), closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());
        isDisplayed();

    }
    //EXPECTED TO FAIL
    @Test
    public void ClientPasswordLoginIsInvalid(){

        onView(withId(R.id.email)).perform(typeText("cl@cl.com"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("12345cl"), closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());
        isDisplayed();

    }
    //EXPECTED TO PAss
    @Test
    public void AdminLoginIsValid() throws InterruptedException {

        onView(withId(R.id.email)).perform(typeText("admin@admin.com"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("1234admin"), closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());
        isDisplayed();
        Thread.sleep(2500);

    }
    //EXPECTED TO PASS
    @Test
    public void AdminLoginPasswordIsInvalid(){

        onView(withId(R.id.email)).perform(typeText("admin@admin.com"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("12345admin"), closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());
        isDisplayed();

    }
    //EXPECTED TO PASS
    @Test
    public void AdminLoginEmailIsInvalid(){

        onView(withId(R.id.email)).perform(typeText("admmin@admin.com"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("1234admin"), closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());
        isDisplayed();

    }
}
