package me.sonaive.lab.base.view.activity

import android.os.Bundle
import android.view.View

/**
 * Created by liutao on 2020-03-20.
 * Describe: 全屏Activity,有全屏需求的继承该Activity
 */
abstract class FullScreenActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        hideSystemBar()
        super.onCreate(savedInstanceState)
        listenSystemBarChange()
    }

    private fun hideSystemBar() {
        val uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                //布局位于状态栏下方
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                //全屏
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                //隐藏导航栏
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = uiOptions
    }

    private fun listenSystemBarChange() {
        window.decorView.setOnSystemUiVisibilityChangeListener {
            val uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    //布局位于状态栏下方
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    //全屏
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    //隐藏导航栏
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            window.decorView.systemUiVisibility = uiOptions
        }
    }
}