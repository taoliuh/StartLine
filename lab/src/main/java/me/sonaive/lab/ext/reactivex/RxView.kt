package me.sonaive.lab.ext.reactivex

import android.view.View
import com.jakewharton.rxbinding3.view.clicks
import me.sonaive.lab.logger.logd
import io.reactivex.Observable
import io.reactivex.schedulers.Timed
import java.util.concurrent.TimeUnit

fun View.clicksThrottleFirst(): Observable<Unit> {
    return clicks().throttleFirst(500, TimeUnit.MILLISECONDS)
}

fun View.combo(hit: Int = 7): Observable<Unit> {
    return clicks().map { 1 }
        .timestamp()
        .scan { t1: Timed<Int>, t2: Timed<Int> ->
            if (t2.time() - t1.time() < 500) {
                Timed<Int>(t1.value() + 1, t2.time(), TimeUnit.MILLISECONDS)
            } else{
                Timed<Int>(1, t2.time(), TimeUnit.MILLISECONDS)
            }
        }
        .filter {
            logd { "combo hit ${it.value()}" }
            it.value() == hit
        }
        .map { Unit }
}