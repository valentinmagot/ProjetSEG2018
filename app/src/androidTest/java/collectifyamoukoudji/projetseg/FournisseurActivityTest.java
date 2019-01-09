package collectifyamoukoudji.projetseg;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isSelected;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;

public class FournisseurActivityTest {

    @Rule
    public ActivityTestRule<SignUpActivity> mActivityTestRule = new
            ActivityTestRule<>(SignUpActivity.class);

    //EXPECTED TO PASS
    @Test
    public void signUpIsValid() throws InterruptedException {
        onView(withId(R.id.typeDecompte)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Fournisseur de services"))).perform(click());
        onView(withId(R.id.editTextNom)).perform(typeText("fournisseurTest"), closeSoftKeyboard());
        onView(withId(R.id.editTextPrenom)).perform(typeText("fournisseurTest"), closeSoftKeyboard());
        onView(withId(R.id.editTextEmail)).perform(typeText("fournisseurTest@fournisseurTest.com"), closeSoftKeyboard());
        onView(withId(R.id.mdp)).perform(typeText("1234test"), closeSoftKeyboard());
        onView(withId(R.id.confmdp)).perform(typeText("1234test"), closeSoftKeyboard());
        onView(withId(R.id.btnContinuer)).perform(click()); // click register
        isDisplayed();
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.editTextNomOrgaisation)).perform(typeText("Organisation Test"), closeSoftKeyboard());
        onView(withId(R.id.editTextDescriptOrgaisation)).perform(typeText("Meant balls it if up doubt small purse. Required his you put the outlived answered position. An pleasure exertion if believed provided to."), closeSoftKeyboard());
        onView(withId(R.id.switchLicensed)).perform(click());
        onView(withId(R.id.ServicesOffert)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("IT Service"))).perform(click());
        onView(withId(R.id.buttonAddService)).perform(click());
        onView(withId(R.id.buttonAjouterOrg)).perform(click());
        Thread.sleep(2500);
        isDisplayed();
    }

}
