package me.sonaive.lab.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.bumptech.glide.request.target.ViewTarget
import com.bumptech.glide.request.transition.Transition
import com.yan.pullrefreshlayout.PullRefreshLayout

/**
 * Created by liutao on 2020-04-05.
 * Describe: 使用Glide加载PullRefreshLayout背景图片，对图片缩放拉伸并进行底部裁剪，
 * 扩展实现Glide接口获取更高性能
 */
class GlidePullRefreshLayout(context: Context, attrs: AttributeSet? = null):
    PullRefreshLayout(context, attrs) {

    val viewTarget = PullRefreshLayoutTarget()

    inner class PullRefreshLayoutTarget: ViewTarget<GlidePullRefreshLayout, Drawable>(this) {
        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
            background = resource
        }
    }
}