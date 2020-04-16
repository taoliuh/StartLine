package me.sonaive.lab.util

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by liutao on 14/04/2020.
 * 判断网络状态是否可用
 */
fun isNetworkAvailable(application: Application): Boolean {
    val manager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = manager.activeNetworkInfo
    if (networkInfo == null || !networkInfo.isAvailable) {
        return false
    }
    return true
}