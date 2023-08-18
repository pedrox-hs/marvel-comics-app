package com.example.comics.rules

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module

class KoinTestRule(
    private val modules: List<Module>,
    private val propertiesProvider: () -> Map<String, Any> = { emptyMap() },
) : TestWatcher() {
    override fun starting(description: Description) {
        startKoin {
            androidContext(InstrumentationRegistry.getInstrumentation().targetContext.applicationContext)
            androidFileProperties()
            properties(propertiesProvider())
            modules(modules)
        }
    }

    override fun finished(description: Description) {
        stopKoin()
    }
}