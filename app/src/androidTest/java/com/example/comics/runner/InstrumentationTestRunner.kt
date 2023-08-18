package com.example.comics.runner

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.example.comics.ComicsApp

class InstrumentationTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        classLoader: ClassLoader?,
        className: String?,
        context: Context?
    ): Application = super.newApplication(classLoader, TestApplication::class.java.name, context)
}

class TestApplication : ComicsApp() {
    override fun startDI() {
        // Do nothing
    }
}