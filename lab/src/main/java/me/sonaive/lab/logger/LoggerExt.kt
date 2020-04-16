package me.sonaive.lab.logger

import me.sonaive.lab.functional.Supplier
import timber.log.Timber

fun initLogger(isDebug: Boolean = true) {
    Timber.uprootAll()
    if (isDebug) {
        Timber.plant(Timber.DebugTree())
    } else {
        Timber.plant(CrashReportingTree())
    }
    log { "initLogger successfully, isDebug = $isDebug" }
}

inline fun log(supplier: Supplier<String>) = logd(supplier)

inline fun logd(supplier: Supplier<String>) = Timber.d(supplier())

inline fun logi(supplier: Supplier<String>) = Timber.i(supplier())

inline fun logw(supplier: Supplier<String>) = Timber.w(supplier())

inline fun loge(supplier: Supplier<String>) = Timber.e(supplier())