package me.sonaive.startline

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import me.sonaive.lab.logger.initLogger
import me.sonaive.lab.util.AppUtil
import me.sonaive.lab.util.initDeviceId

/**
 * Created by liutao on 2020-02-23.
 * Describe: 应用程序入口
 */
open class App: Application() {

    companion object {
        private lateinit var INSTANCE: App
        fun instance() = INSTANCE
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        AppUtil.init(this)
        initLogger(BuildConfig.DEBUG)
        initDeviceId(this)
        initLeakCanary()
    }

    private fun initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }
}