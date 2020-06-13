package com.andy_dev.arpractical.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.andy_dev.arpractical.BaseApplication
import com.andy_dev.arpractical.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

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

    fun loadImage(mActivity: Context, url: String, imageView: ImageView) {
        Glide.with(mActivity)
            .load(url)
            .apply(getRequest())
            .into(imageView)
    }

    private fun getRequest(): RequestOptions {
        return RequestOptions()
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.ALL)

    }
}