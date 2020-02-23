package me.sonaive.startline.utils

import me.sonaive.lab.ext.toast
import me.sonaive.startline.App

inline fun toast(value: () -> String): Unit =
        App.instance().toast(value)