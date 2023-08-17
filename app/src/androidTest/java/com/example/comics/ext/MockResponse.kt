package com.example.comics.ext

import okhttp3.mockwebserver.MockResponse

fun MockResponse.useResourceAsBody(resource: String): MockResponse {
	val content = javaClass.classLoader?.getResource(resource)?.readText()
	return setBody(content ?: "")
}