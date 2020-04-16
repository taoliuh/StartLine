package me.sonaive.lab.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.LocaleList
import android.provider.Settings
import java.math.BigInteger
import java.security.SecureRandom
import java.util.*

/**
 * Created by liutao on 2020-02-27.
 * Describe: 关于设备的各种属性
 */

/**
 * Return the manufacturer of the product/hardware.
 *
 * e.g. Xiaomi
 *
 * @return the manufacturer of the product/hardware
 */
fun getManufacturer(): String {
    return Build.MANUFACTURER
}

/**
 * Return the model of device.
 * <p>e.g. MI2SC</p>
 *
 * @return the model of device
 */
fun getModel(): String {
    var model: String? = Build.MODEL
    if (model != null) {
        model = model.trim { it <= ' ' }.replace("\\s*".toRegex(), "")
    } else {
        model = ""
    }
    return model
}

/**
 * Return the version name of device's system.
 *
 * @return the version name of device's system
 */
fun getSDKVersionName(): String {
    return Build.VERSION.RELEASE
}

/**
 * Return version code of device's system.
 *
 * @return version code of device's system
 */
fun getSDKVersionCode(): Int {
    return Build.VERSION.SDK_INT
}

/**
 * Return default language of device's system
 *
 * @return the default language of device
 */
fun getSystemLanguage(): String {
    val locale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        LocaleList.getDefault().get(0)
    } else {
        Locale.getDefault()
    }
    return locale.language + "-" + locale.country
}

/**
 * Return the android id of device.
 *
 * @return the android id of device
 */
@SuppressLint("HardwareIds")
fun getAndroidID(context: Context): String {
    var id = Settings.Secure.getString(
        context.applicationContext.contentResolver,
        Settings.Secure.ANDROID_ID
    )
    if (id == null || "9774d56d682e549c" == id || id.length < 16) {
        val random = SecureRandom()
        id = BigInteger(64, random).toString(16)
    }
    return id
}