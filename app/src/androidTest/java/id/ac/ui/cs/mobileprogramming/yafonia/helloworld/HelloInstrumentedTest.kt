package id.ac.ui.cs.mobileprogramming.yafonia.helloworld

import androidx.core.content.MimeTypeFilter.matches
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class HelloInstrumentedTest {

    @Rule
    @JvmField
    val rule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("id.ac.ui.cs.mobileprogramming.yafonia.helloworld", appContext.packageName)
    }

    @Test
    fun user_can_see_title() {
        onView(withId(R.id.title)).check(ViewAssertions.matches(withText("What is your name?")))
    }

    @Test
    fun user_can_enter_name() {
        onView(withId(R.id.name)).perform(typeText("Yafonia"))
    }

    @Test
    fun user_can_submit() {
        onView(withId(R.id.submit)).perform(click())
    }

    @Test
    fun user_can_see_response() {
        onView(withId(R.id.name)).perform(typeText("Yafonia"))
        onView(withId(R.id.submit)).perform(click())
        onView(withId(R.id.response)).check(ViewAssertions.matches(withText("Hello Yafonia! You've done your best today!")))
    }


}
