package me.sonaive.lab.ext

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Context.toast(value: String) = toast { value }

fun Fragment.toast(value: String) = toast { value }

inline fun Context.toast(value: () -> String) =
        Toast.makeText(this, value(), Toast.LENGTH_SHORT).show()

inline fun Fragment.toast(value: () -> String) =
        Toast.makeText(activity, value(), Toast.LENGTH_SHORT).show()

