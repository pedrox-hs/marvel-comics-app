package com.example.comics.idlingresource

import androidx.test.espresso.IdlingResource
import okhttp3.OkHttpClient

/**
 * An implementation of [IdlingResource] for [OkHttpClient].
 * This class makes Espresso wait until all the requests are completed.
 * @param name The name of [OkHttpClient].
 * @param client The [OkHttpClient] to be used.
 * @see <a href="https://github.com/JakeWharton/okhttp-idling-resource">JakeWharton/okhttp-idling-resource</a>
 */
class OkHttp3IdlingResource(
    private val name: String,
    client: OkHttpClient,
) : IdlingResource {

    private val dispatcher = client.dispatcher
    private var callback: IdlingResource.ResourceCallback? = null

    init {
        dispatcher.idleCallback = Runnable {
            callback?.onTransitionToIdle()
        }
    }

    override fun getName() = name

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback = callback
    }

    override fun isIdleNow() = dispatcher.runningCallsCount() == 0
}