package me.sonaive.lab.base.view.dialog

import android.content.Context
import me.sonaive.lab.R

/**
 * Created by liutao on 2020-03-20.
 * Describe: loading加载框，禁止用户操作
 */
class LoadingDialog(context: Context): FullscreenDialog(context) {
    override fun getLayoutRedId(): Int {
        return R.layout.dialog_loading
    }
}