package me.sonaive.startline.api

import android.util.SparseIntArray
import me.sonaive.startline.App
import me.sonaive.startline.R

/**
 * Created by liutao on 14/04/2020.
 * 服务请求异常
 */

object KeyValueErrors {
    private val map = SparseIntArray()

    init {
        map.put(-1, R.string.EN1)
    }

    fun getMessage(code: Int): String {
        val id = map.get(code)
        return if (id == 0) "" else App.instance().getString(id)
    }
}