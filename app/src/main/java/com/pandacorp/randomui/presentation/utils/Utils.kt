package com.pandacorp.randomui.presentation.utils

object Utils {
    /**
     * This function is needed for coroutines logs work on Xiaomi devices.
     */
    fun setupExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            throw (throwable)
        }
    }
}

