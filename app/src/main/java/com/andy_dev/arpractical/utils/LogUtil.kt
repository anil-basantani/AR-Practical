package com.andy_dev.arpractical.utils

import android.util.Log
import com.andy_dev.arpractical.BuildConfig

object LogUtil {
    private val isLogShow = BuildConfig.DEBUG

    fun LOGE(tag: String, message: String) {
        if (isLogShow)
            Log.e(tag, message)
    }

    fun LOGE(tag: String, message: String, th: Throwable) {
        if (isLogShow)
            Log.e(tag, message, th)
    }
}