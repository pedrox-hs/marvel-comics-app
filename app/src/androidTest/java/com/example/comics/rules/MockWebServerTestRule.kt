package com.example.comics.rules

import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.ExternalResource

class MockWebServerTestRule : ExternalResource() {
    val server = MockWebServer()

    override fun before() {
        server.start()
    }

    override fun after() {
        server.shutdown()
    }
}