package com.example.openspot


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.view.ViewGroup
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class testContactUs_43 {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(NavigationActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.ACCESS_FINE_LOCATION"
        )

    @Test
    fun testContactUs_43() {
        Thread.sleep(2000)
        //2 second wait

        val supportVectorDrawablesButton = onView(
            allOf(
                withId(R.id.phone_button), withText("Sign in with phone"),
                childAtPosition(
                    allOf(
                        withId(R.id.btn_holder),
                        childAtPosition(
                            withId(R.id.container),
                            0
                        )
                    ),
                    0
                )
            )
        )
        //variable for sign in button
        supportVectorDrawablesButton.perform(scrollTo(), click())
        //click sign in button

        Thread.sleep(500)
        //half second wait

        val textInputEditText = onView(
            allOf(
                withId(R.id.phone_number),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.phone_layout),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText.perform(scrollTo(), replaceText("5555555555"), closeSoftKeyboard())
        //create variable for entering phone number, enter default phone number

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.phone_number), withText("5555555555"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.phone_layout),
                        0
                    ),
                    0
                )
            )
        )
        //replace text with default phone number
        textInputEditText2.perform(pressImeActionButton())

        Thread.sleep(2000)
        //2 second wait

        //*********************************************************************************
        val spacedEditText = onView(
            allOf(
                withId(R.id.confirmation_code), withText("- - - - - -"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.confirmation_code_layout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        spacedEditText.perform(scrollTo(), replaceText("5 5 5 5 5 5"))

        //need variable getting confirmation code, with the default code i want to enter in
        //perform action
        //*********************************************************************************

        Thread.sleep(500)
        //half second delay

        val appCompatButton = onView(
            allOf(
                withId(R.id.submit_confirmation_code), withText("Continue"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        )
        appCompatButton.perform(scrollTo(), click())
        //create button for continue, press continue


        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.navigation_settings), withContentDescription("Settings"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.navigation),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val recyclerView = onView(
            allOf(
                withId(R.id.recycler_view),
                childAtPosition(
                    withId(android.R.id.list_container),
                    0
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(4, click()))

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatEditText = onView(
            allOf(
                withId(R.id.your_subject),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.support.design.widget.CoordinatorLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(click())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.your_subject),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.support.design.widget.CoordinatorLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText("Hi"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.your_message),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.support.design.widget.CoordinatorLayout")),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText3.perform(replaceText("this is a test"), closeSoftKeyboard())

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.your_message), withText("this is a test"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.support.design.widget.CoordinatorLayout")),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText4.perform(click())

        val appCompatButton2 = onView(
            allOf(
                withId(R.id.post_message), withText("Send Message"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.support.design.widget.CoordinatorLayout")),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatButton2.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatImageButton = onView(
            allOf(
                withContentDescription("Navigate up"),
                childAtPosition(
                    allOf(
                        withId(R.id.action_bar),
                        childAtPosition(
                            withId(R.id.action_bar_container),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
