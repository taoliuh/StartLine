package me.sonaive.lab.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.Environment
import me.sonaive.lab.logger.logd
import io.reactivex.Observable
import java.io.File

/**
 * Created by liutao on 2020-02-28.
 * Describe: 获取用户唯一设备id, 为了最大程度保证该id在一台设备上不发生变化，会将id进行数据持久化
 */

private const val KEY_DEVICE_ID = "device_id"
private lateinit var deviceId: String


fun initDeviceId(context: Context) {
    Observable.just(1).map { getLocalDeviceId() }
        .doOnNext {
            if (it.isNotEmpty()) {
                deviceId = it
                logd { "getLocalDeviceId, device id = $deviceId" }
            } else {
                deviceId = getAndroidID(context)
                logd { "getAndroidID, device id = $deviceId" }
            }
            saveLocalDeviceId(deviceId)
        }
        .subscribeOn(RxSchedulers.io)
        .subscribe()
}

/**
 * Return the android id of device.
 *
 * @return the android id of device
 */
@SuppressLint("HardwareIds")
fun getDeviceId(): String {
    return deviceId
}

private fun getLocalDeviceId(): String {
    logd { "getLocalDeviceId, current thread name : ${Thread.currentThread().name}" }
    var id = getString(KEY_DEVICE_ID)
    val filePath = Environment.getExternalStorageDirectory().absolutePath +
            File.separator +  ".android" +
            File.separator + "device_id.dat"
    return if (id.isEmpty()) {
        id = readFile2String(filePath) ?: ""
        logd { "readFile2String, sdcard: $id" }
        id
    } else {
        logd { "readFile2String, sp: $id" }
        id
    }
}

private fun saveLocalDeviceId(id: String) {
    logd { "saveLocalDeviceId, current thread name : ${Thread.currentThread().name}" }
    putString(KEY_DEVICE_ID, id)
    val filePath = Environment.getExternalStorageDirectory().absolutePath +
            File.separator +  ".android" +
            File.separator + "device_id.dat"
    writeFileFromString(filePath, id, false)
}