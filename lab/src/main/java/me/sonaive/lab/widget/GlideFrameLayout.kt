package me.sonaive.lab.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import com.bumptech.glide.request.target.ViewTarget
import com.bumptech.glide.request.transition.Transition

/**
 * Created by liutao on 2020-04-15.
 * Describe: 使用Glide加载FrameLayout背景图片，对图片缩放拉伸并进行底部裁剪，
 * 扩展实现Glide接口获取更高性能
 */
open class GlideFrameLayout(context: Context, attrs: AttributeSet? = null):
    FrameLayout(context, attrs) {

    val viewTarget = FrameLayoutTarget()

    inner class FrameLayoutTarget: ViewTarget<GlideFrameLayout, Drawable>(this) {
        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
            background = resource
        }
    }
}