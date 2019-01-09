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

public class ServiceActivityTest {
    @Rule
    public ActivityTestRule<LogIn> mActivityTestRule = new
            ActivityTestRule<>(LogIn.class);
    //EXPECTED TO PASS
    @Test
    public void serviceIsValid() throws InterruptedException {
        onView(withId(R.id.email)).perform(typeText("admin@admin.com"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("1234admin"), closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());
        Thread.sleep(2500);
        onView(withId(R.id.btnServices)).perform(click()); // click services
        Thread.sleep(2500);

        // click services
        onView(withId(R.id.editTextService)).perform(typeText("TestService"), closeSoftKeyboard());
        onView(withId(R.id.editTextRate)).perform(typeText("160"), closeSoftKeyboard());
        onView(withId(R.id.addButton)).perform(click());
        isDisplayed();
        Thread.sleep(2500);
    }
    //EXPECTED TO PASS
    @Test
    public void serviceIsInvalid() throws InterruptedException {
        onView(withId(R.id.email)).perform(typeText("admin@admin.com"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("1234admin"), closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());
        Thread.sleep(2500);
        onView(withId(R.id.btnServices)).perform(click()); // click services
        Thread.sleep(2500);

        // click services
        onView(withId(R.id.editTextService)).perform(typeText("TestService"), closeSoftKeyboard());
        onView(withId(R.id.editTextRate)).perform(typeText("160"), closeSoftKeyboard());
        onView(withId(R.id.addButton)).perform(click());
        isDisplayed();
        Thread.sleep(2500);
    }
}
