package me.sonaive.lab.base.view.fragment

import me.sonaive.lab.base.view.dialog.LoadingDialog
import me.sonaive.lab.base.viewstate.BaseViewState
import me.sonaive.lab.logger.logd

/**
 * Created by liutao on 2020-04-04.
 * Describe: 数据请求响应处理基类，包括请求统一loading，数据异常处理，响应结果分发到子类单独处理
 */
abstract class BaseBusinessFragment: BaseFragment() {
    private val mLoadingDialog: LoadingDialog by lazy {
        LoadingDialog(activity!!)
    }

    open fun <T> onNewState(state: BaseViewState<T>) {
        when {
            state.isLoading -> {
                if (activity == null || activity!!.isFinishing) return
                mLoadingDialog.show()
            }
            state.throwable != null -> {
                if (activity == null || activity!!.isFinishing) return
                mLoadingDialog.dismiss()
            }
            state.result != null -> {
                if (activity == null || activity!!.isFinishing) return
                mLoadingDialog.dismiss()
                logd { "load success! ${state.result}" }
                processData(state)
            }
        }
    }

    override fun onDestroy() {
        mLoadingDialog.dismiss()
        super.onDestroy()
    }

    abstract fun <T> processData(state: BaseViewState<T>)
}