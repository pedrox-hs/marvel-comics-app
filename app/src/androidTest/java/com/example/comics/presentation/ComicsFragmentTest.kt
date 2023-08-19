package com.example.comics.presentation

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onIdle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToLastPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.comics.R
import com.example.comics.di.AppModule
import com.example.comics.di.networkModule
import com.example.comics.ext.useResourceAsBody
import com.example.comics.idlingresource.OkHttp3IdlingResource
import com.example.comics.rules.KoinTestRule
import com.example.comics.rules.MockWebServerTestRule
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.startsWith
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.ksp.generated.module

@RunWith(AndroidJUnit4::class)
class ComicsFragmentTest {
    private val okHttpClient = OkHttpClient()
    private val okHttp3IdlingResource = OkHttp3IdlingResource("OkHttp", okHttpClient)

    private val webServerTestRule = MockWebServerTestRule()

    @get:Rule
    val chain: RuleChain = RuleChain
        .outerRule(webServerTestRule)
        .around(
            KoinTestRule(
                modules = AppModule().module + networkModule + module {
                    single { okHttpClient }
                },
                propertiesProvider = {
                    mapOf(
                        "api_base_url" to webServerTestRule.server.url("/").toString()
                    )
                },
            )
        )

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(okHttp3IdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(okHttp3IdlingResource)
    }

    @Test
    fun shouldContainsCorrectListSize() {
        // arrange
        webServerTestRule.server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .useResourceAsBody("list_comics_success.json")
        )
        launchFragmentInContainer<ComicsFragment>(themeResId = R.style.Theme_Comics)

        // act
        onView(withId(R.id.list_item))
            .perform(scrollToLastPosition<ComicsAdapter.ItemViewHolder>())

        // assert
        onView(withId(R.id.list_item))
            .check { view, _ ->
                val adapter = (view as? RecyclerView)?.adapter
                assertThat(adapter?.itemCount, equalTo(20))
            }
    }

    @Test
    fun shouldContainsCorrectItemContent() {
        // arrange
        webServerTestRule.server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .useResourceAsBody("list_comics_success.json")
        )
        launchFragmentInContainer<ComicsFragment>(themeResId = R.style.Theme_Comics)

        // act
        onView(withId(R.id.list_item))
            .perform(scrollToPosition<ComicsAdapter.ItemViewHolder>(7))

        // assert
        onView(withId(R.id.list_item))
            .check(matches(hasDescendant(withText("Startling Stories: The Incorrigible Hulk (2004) #1"))))
            .check(matches(hasDescendant(withText(startsWith("For Doctor Bruce Banner life is anything but normal. But what happens when two women get between him and his alter ego, the Incorrigible Hulk? Hulk confused!")))))
    }

    @Test
    fun shouldShowErrorMessage() {
        // arrange
        webServerTestRule.server.enqueue(
            MockResponse()
                .setResponseCode(500)
        )
        launchFragmentInContainer<ComicsFragment>(themeResId = R.style.Theme_Comics)
        onIdle()

        // assert
        onView(withId(R.id.errorTV))
            .check(matches(allOf(isDisplayed(), withText("Ocorreu algum erro"))))
    }
}

