package com.nosetrap.util

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.nosetrap.util.activity.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {


    @Rule
    @JvmField
    val rule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun clickingAddButton_addsToTheCounter(){
        //step 1 - find view
        val btnAdd = withId(R.id.btnAdd)
        val tvCounter = withId(R.id.tvCounter)

        onView(tvCounter).check(matches(withText("0")))

        //step 2 - perform action
        onView(btnAdd).perform(click())

                //step 3 - assert condition
        onView(tvCounter).check(matches(withText("1")))

        onView(btnAdd).perform(doubleClick())
        onView(tvCounter).check(matches(withText("3")))
    }

    @Test
    fun clickingReset_resetsTheCounter(){
        val tvCounter = withId(R.id.tvCounter)
        val btnReset = withId(R.id.btnReset)
        val btnAdd = withId(R.id.btnAdd)

        onView(btnAdd).perform(click())
        onView(tvCounter).check(matches(withText("1")))
        onView(btnReset).perform(click())
        onView(tvCounter).check(matches(withText("0")))
    }
}