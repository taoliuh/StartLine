package me.sonaive.lab.util

import android.annotation.SuppressLint
import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

@SuppressLint("PrivateApi")
fun getStatusbarHeight(): Int {
    var statusBarHeight = 0
    try {
        val c = Class.forName("com.android.internal.R\$dimen")
        val o = c.newInstance()
        val field = c.getField("status_bar_height")
        val x = field.get(o) as Int
        statusBarHeight = AppUtil.appContext.resources.getDimensionPixelSize(x)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return statusBarHeight
}

fun getScreenHeightExcludeStatusbar(): Int {
    return AppUtil.appContext.resources.displayMetrics.heightPixels - getStatusbarHeight()
}

fun px2dp(pxValue: Float): Int {
    val density = AppUtil.appContext.resources.displayMetrics.density
    return (pxValue / density + 0.5f).toInt()
}

fun dp2px(dpValue: Float): Int {
    val density = AppUtil.appContext.resources.displayMetrics.density
    return (dpValue * density + 0.5f).toInt()
}

fun px2sp(pxValue: Float): Int {
    val scaleDensity = AppUtil.appContext.resources.displayMetrics.scaledDensity
    return (pxValue / scaleDensity + 0.5f).toInt()
}

fun sp2px(spValue: Float): Int {
    val scaleDensity = AppUtil.appContext.resources.displayMetrics.scaledDensity
    return (spValue * scaleDensity + 0.5f).toInt()
}

fun getDensity(): Float {
    return AppUtil.appContext.resources.displayMetrics.density
}

fun getScreenWidth(): Int {
    return AppUtil.appContext.resources.displayMetrics.widthPixels
}

fun getScreenHeight(): Int {
    return AppUtil.appContext.resources.displayMetrics.heightPixels
}

/**
 * 通过反射，获取包含虚拟导航栏的整体屏幕高度
 *
 * @return
 */
fun getRawScreenHeight(): Int {
    var height = 0
    val windowManager = AppUtil.appContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = windowManager.defaultDisplay
    val dm = DisplayMetrics()
    val c: Class<*>
    try {
        c = Class.forName("android.view.Display")
        val method = c.getMethod("getRealMetrics", DisplayMetrics::class.java)
        method.invoke(display, dm)
        height = dm.heightPixels
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return height
}
