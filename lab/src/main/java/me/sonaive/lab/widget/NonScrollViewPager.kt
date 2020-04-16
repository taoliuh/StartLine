package me.sonaive.lab.widget

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager
import android.view.MotionEvent
import android.view.animation.DecelerateInterpolator
import android.widget.Scroller

/**
 * Created by liutao on 2020-03-27.
 * Describe: 禁止滑动ViewPager
 */
class NonScrollViewPager(context: Context, attrs: AttributeSet? = null): ViewPager(context, attrs) {

    init {
        setDisabledScroller()
    }

    override fun onTouchEvent(arg0: MotionEvent): Boolean {
        return false
    }

    override fun onInterceptTouchEvent(arg0: MotionEvent): Boolean {
        return false
    }

    private fun setDisabledScroller() {
        try {
            val viewpager = ViewPager::class.java
            val scroller = viewpager.getDeclaredField("mScroller")
            scroller.isAccessible = true
            scroller.set(this, DisabledScroller(context))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    inner class DisabledScroller(context: Context) : Scroller(context, DecelerateInterpolator()) {

        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
            super.startScroll(startX, startY, dx, dy, 0)
        }
    }
}