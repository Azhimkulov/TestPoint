package com.siroca.testpoint

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.siroca.testpoint.screen.EnergyActivity
import com.siroca.testpoint.screen.StatActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by azamat  on 9/15/19.
 */
@RunWith(AndroidJUnit4::class)
class UiTest {

    @Rule
    @JvmField
    val activity = ActivityTestRule<EnergyActivity>(EnergyActivity::class.java, false, false)

    @Test
    fun launchActivity(){
        activity.launchActivity(null)
    }

    @Test
    fun openStatActivity(){
        activity.launchActivity(null)
        Espresso.onView(ViewMatchers.withId(R.id.openStat)).perform(ViewActions.click())
        intended(hasComponent(StatActivity::class.java.name))
    }

    @Rule @JvmField var mStatActivityActivityTestRule: IntentsTestRule<StatActivity> =
        IntentsTestRule(StatActivity::class.java)
}