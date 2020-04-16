package me.sonaive.lab.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.core.widget.NestedScrollView
import com.bumptech.glide.request.target.ViewTarget
import com.bumptech.glide.request.transition.Transition

/**
 * Created by liutao on 2020-03-27.
 * Describe: 使用Glide加载NestedScrollView背景图片，对图片缩放拉伸并进行底部裁剪，
 * 扩展实现Glide接口获取更高性能
 */
class GlideNestedScrollView(context: Context, attrs: AttributeSet? = null):
    NestedScrollView(context, attrs) {

    val viewTarget = NestScrollViewTarget()

    inner class NestScrollViewTarget: ViewTarget<GlideNestedScrollView, Drawable>(this) {
        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
            background = resource
        }
    }
}