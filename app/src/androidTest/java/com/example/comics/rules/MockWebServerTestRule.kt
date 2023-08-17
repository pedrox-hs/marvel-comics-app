package com.example.comics.rules

import com.example.comics.MOCK_WEB_SERVER_PORT
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.ExternalResource

class MockWebServerTestRule : ExternalResource() {
    lateinit var server: MockWebServer

    override fun before() {
        server = MockWebServer().apply {
            start(MOCK_WEB_SERVER_PORT)
        }
    }

    override fun after() {
        server.shutdown()
    }
}