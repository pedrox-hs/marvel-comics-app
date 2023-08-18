package com.example.comics.rules

import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.ExternalResource

class MockWebServerTestRule : ExternalResource() {
    lateinit var server: MockWebServer

    override fun before() {
        server = MockWebServer().apply {
            start()
        }
    }

    override fun after() {
        server.shutdown()
    }
}