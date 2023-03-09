package com.example.weather

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainFragmentTest {
    @Rule
    @JvmField
    var mFragmentScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    //1 test UI
    fun etSearchTest() {
        val et = onView(
            allOf(
                withId(R.id.etSearch),
                isDisplayed(),
            )
        )
        et.check(matches(isEnabled()))
    }

    @Test
    //2 test UI
    fun lastCityTest(){
        val last = onView(
            allOf(
                withId(R.id.tvLastCity),
                isDisplayed()
            )
        )
        last.check(matches(isClickable()))
    }
}