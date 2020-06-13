package com.andy_dev.arpractical.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.andy_dev.arpractical.BaseApplication
import com.andy_dev.arpractical.R

object Utility {


    fun setSwipeColor(swipeRefreshLayout: SwipeRefreshLayout) {
        val indicatorColorArr =
            intArrayOf(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary)
        swipeRefreshLayout.setColorSchemeResources(*indicatorColorArr)
    }

    fun hasInternet(): Boolean {
        val connectivityManager =
            BaseApplication.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}