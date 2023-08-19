package com.example.comics.data.network

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class ComicApiAuthInterceptorTest {
    private lateinit var okHttpClient: OkHttpClient
    private val mockWebServer = MockWebServer()

    @Before
    fun setup() {
        val interceptor = ComicApiAuthInterceptor(
            apiKey = "my apiKey",
            ts = "the ts",
            hash = "an hash",
        )
        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `when intercept add query params apikey`() {
        // arrange
        val request = Request.Builder()
            .url(mockWebServer.url("/"))
            .build()
        mockWebServer.enqueue(MockResponse().setResponseCode(204))

        // act
        okHttpClient.newCall(request).execute()
        val requestedUrl = mockWebServer.takeRequest().requestUrl!!

        // assert
        assertThat(requestedUrl.queryParameter("apikey"), equalTo("my apiKey"))
    }

    @Test
    fun `when intercept add query params ts`() {
        // arrange
        val request = Request.Builder()
            .url(mockWebServer.url("/"))
            .build()
        mockWebServer.enqueue(MockResponse().setResponseCode(204))

        // act
        okHttpClient.newCall(request).execute()
        val requestedUrl = mockWebServer.takeRequest().requestUrl!!

        // assert
        assertThat(requestedUrl.queryParameter("ts"), equalTo("the ts"))
    }

    @Test
    fun `when intercept add query params hash`() {
        // arrange
        val request = Request.Builder()
            .url(mockWebServer.url("/"))
            .build()
        mockWebServer.enqueue(MockResponse().setResponseCode(204))

        // act
        okHttpClient.newCall(request).execute()
        val requestedUrl = mockWebServer.takeRequest().requestUrl!!

        // assert
        assertThat(requestedUrl.queryParameter("hash"), equalTo("an hash"))
    }
}