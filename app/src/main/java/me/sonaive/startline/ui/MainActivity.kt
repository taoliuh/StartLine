package me.sonaive.startline.ui

import android.os.Bundle
import android.view.View
import com.uber.autodispose.autoDisposable
import kotlinx.android.synthetic.main.activity_main.*
import me.sonaive.lab.base.view.activity.BaseActivity
import me.sonaive.lab.ext.reactivex.clicksThrottleFirst
import me.sonaive.lab.ext.toast
import me.sonaive.lab.logger.logd
import me.sonaive.lab.util.RxSchedulers
import me.sonaive.startline.R
import me.sonaive.startline.ui.main.MainViewModel
import me.sonaive.startline.ui.main.MainViewState
import retrofit2.HttpException

class MainActivity : BaseActivity() {

    private val mViewModel: MainViewModel by lazy {
        MainViewModel()
    }

    override val layoutId = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binds()
    }

    private fun binds() {
        helloWorld.clicksThrottleFirst().autoDisposable(scopeProvider)
            .subscribe {
                mViewModel.getData()
            }
        mViewModel.observeViewState()
            .observeOn(RxSchedulers.ui)
            .autoDisposable(scopeProvider)
            .subscribe(this::onNewState)
    }

    private fun onNewState(state: MainViewState) {
        when {
            state.throwable != null -> when (state.throwable) {
                is HttpException ->
                    when (state.throwable.code()) {
                        401 -> "bad request"
                        else -> "network failure"
                    }
                else -> "network failure"
            }.also { str ->
                progressBar.visibility = View.GONE
                toast { str }
            }
            state.result != null -> {
                progressBar.visibility = View.GONE
                helloWorld.text = state.result
                logd { "load success! ${state.result}" }
            }
            else -> progressBar.visibility = View.VISIBLE
        }
    }
}
