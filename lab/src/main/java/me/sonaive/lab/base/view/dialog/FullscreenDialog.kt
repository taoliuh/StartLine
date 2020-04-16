package me.sonaive.lab.base.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.WindowManager
import me.sonaive.lab.util.getRawScreenHeight
import me.sonaive.lab.util.getScreenWidth
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import me.sonaive.lab.R

/**
 * Created by liutao on 2020-03-20.
 * 如何防止底部状态栏闪动出现，参考https://www.codeleading.com/article/4277958381/
 * Describe: 隐藏状态和导航栏的加载框，禁止用户操作
 */
abstract class FullscreenDialog(context: Context,
                       themeResId: Int = R.style.TransparentBackgroundDialog):
    Dialog(context, themeResId) {

    protected val scopeProvider: AndroidLifecycleScopeProvider by lazy {
        AndroidLifecycleScopeProvider.from(context as FragmentActivity, Lifecycle.Event.ON_DESTROY)
    }

    protected val activeProvider: AndroidLifecycleScopeProvider by lazy {
        AndroidLifecycleScopeProvider.from(context as FragmentActivity, Lifecycle.Event.ON_PAUSE)
    }

    abstract fun getLayoutRedId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRedId())
        setCanceledOnTouchOutside(true)
        setCancelable(false)
        val attributes = window?.attributes
        // 设置窗口大小，否则dialog内容部分无法显示
        attributes?.width = getScreenWidth()
        attributes?.height = getRawScreenHeight()
        window?.attributes = attributes
        window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        hideSystemBar()
        binds()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> if (isShowing) {
                dismiss()
            }
        }
        return true
    }

    override fun show() {
        if (isShowing) {
            return
        }
        window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
        super.show()
        window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    override fun dismiss() {
        if (!isShowing) return
        super.dismiss()
    }

    open fun binds() {}

    private fun hideSystemBar() {
        window?.decorView?.setOnSystemUiVisibilityChangeListener {
            var uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    //布局位于状态栏下方
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    //全屏
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    //隐藏导航栏
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            if (Build.VERSION.SDK_INT >= 19) {
                uiOptions = uiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            } else {
                uiOptions = uiOptions or View.SYSTEM_UI_FLAG_LOW_PROFILE
            }
            uiOptions = uiOptions or 0x00001000
            window?.decorView?.systemUiVisibility = uiOptions
        }
    }
}