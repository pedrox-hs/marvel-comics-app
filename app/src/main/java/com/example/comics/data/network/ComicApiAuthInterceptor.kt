package com.example.comics.data.network

import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Property

@Factory
class ComicApiAuthInterceptor(
    @Property("api_key")
    private val apiKey: String,
    @Property("api_ts")
    private val ts: String,
    @Property("api_hash")
    private val hash: String,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val url = originalRequest.url.newBuilder()
            .setQueryParameter("apikey", apiKey)
            .setQueryParameter("ts", ts)
            .setQueryParameter("hash", hash)
            .build()

        val request = originalRequest.newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}