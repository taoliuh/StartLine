package me.sonaive.lab.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.request.target.ViewTarget
import com.bumptech.glide.request.transition.Transition

/**
 * Created by liutao on 2020-03-31.
 * Describe: 使用Glide加载ConstraintLayout背景图片，对图片缩放拉伸并进行底部裁剪，
 * 扩展实现Glide接口获取更高性能
 */
class GlideConstraintLayout(context: Context, attrs: AttributeSet? = null):
    ConstraintLayout(context, attrs) {

    val viewTarget = GlideConstraintLayoutTarget()

    inner class GlideConstraintLayoutTarget: ViewTarget<GlideConstraintLayout, Drawable>(this) {
        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
            background = resource
        }
    }
}