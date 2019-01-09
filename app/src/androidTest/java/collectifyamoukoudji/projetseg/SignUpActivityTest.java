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

public class SignUpActivityTest {
    @Rule
    public ActivityTestRule<SignUpActivity> mActivityTestRule = new
            ActivityTestRule<>(SignUpActivity.class);
    //EXPECTED TO PASS
    @Test
    public void signUpIsValid(){
        onView(withId(R.id.editTextNom)).perform(typeText("clientTest"), closeSoftKeyboard());
        onView(withId(R.id.editTextPrenom)).perform(typeText("clientTest"), closeSoftKeyboard());
        onView(withId(R.id.editTextEmail)).perform(typeText("clientTest@clientTest.com"), closeSoftKeyboard());
        onView(withId(R.id.mdp)).perform(typeText("1234cl"), closeSoftKeyboard());
        onView(withId(R.id.confmdp)).perform(typeText("1234cl"), closeSoftKeyboard());
        onView(withId(R.id.btnContinuer)).perform(click()); // click register
        isDisplayed();
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //EXPECTED TO PASS
    @Test
    public void signUpIsInvalid(){
        onView(withId(R.id.editTextNom)).perform(typeText("clientTest"), closeSoftKeyboard());
        onView(withId(R.id.editTextPrenom)).perform(typeText("clientTest"), closeSoftKeyboard());
        onView(withId(R.id.editTextEmail)).perform(typeText("clientTest@clientTest.com"), closeSoftKeyboard());
        onView(withId(R.id.mdp)).perform(typeText("1234cl"), closeSoftKeyboard());
        onView(withId(R.id.confmdp)).perform(typeText("1234cl"), closeSoftKeyboard());
        onView(withId(R.id.btnContinuer)).perform(click()); // click register
        isDisplayed();

    }
}
