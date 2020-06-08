package me.sonaive.lab.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

/**
 * Created by liutao on 24/04/2020.
 */
fun Context.showKeyboard(view: View) {
    view.isFocusable = true
    view.isFocusableInTouchMode = true
    view.requestFocus()
    val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        ?: return
    inputManager.showSoftInput(view, 0)
}

fun Context.hideKeyboard(view: View) {
    val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        ?: return
    inputManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.showKeyboard(view: View) {
    view.isFocusable = true
    view.isFocusableInTouchMode = true
    view.requestFocus()
    val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager? ?: return
    inputManager.showSoftInput(view, 0)
}

fun Fragment.hideKeyboard(view: View) {
    val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager? ?: return
    inputManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun View.showKeyboard(view: View) {
    view.isFocusable = true
    view.isFocusableInTouchMode = true
    view.requestFocus()
    val inputManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager? ?: return
    inputManager.showSoftInput(view, 0)
}

fun View.hideKeyboard(view: View) {
    val inputManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager? ?: return
    inputManager.hideSoftInputFromWindow(view.windowToken, 0)
}
