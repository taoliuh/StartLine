package me.sonaive.lab.util

import android.app.Application
import android.content.Context

/**
 * Created by liutao on 2020-02-28.
 * Describe: 获取全局application, Application启动时注入，内部使用
 */
object AppUtil {
    private lateinit var application: Application

    fun init(application: Application) {
        this.application = application
    }

    @JvmStatic
    val appContext: Context
        get() = application.applicationContext
}
