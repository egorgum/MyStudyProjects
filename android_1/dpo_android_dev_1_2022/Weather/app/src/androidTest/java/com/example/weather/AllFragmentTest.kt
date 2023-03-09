package com.example.weather

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AllFragmentTest {
    @Rule
    @JvmField
    var mFragmentScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    //3 test UI
    fun rvTest() {
        val btCities = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(com.example.weather.R.id.btCities),
                ViewMatchers.isDisplayed(),
            )
        )
        btCities.perform(click())

        val rv = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.rv),
                ViewMatchers.isDisplayed(),
            )
        )
        rv.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}