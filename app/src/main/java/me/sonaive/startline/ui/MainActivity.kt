package me.sonaive.startline.ui

import androidx.lifecycle.ViewModelProvider
import com.uber.autodispose.autoDisposable
import com.wuhenzhizao.titlebar.widget.CommonTitleBar.ACTION_LEFT_TEXT
import kotlinx.android.synthetic.main.activity_main.*
import me.sonaive.lab.base.view.activity.BaseBusinessActivity
import me.sonaive.lab.base.viewstate.BaseViewState
import me.sonaive.lab.ext.reactivex.clicksThrottleFirst
import me.sonaive.lab.util.RxSchedulers
import me.sonaive.startline.R
import me.sonaive.startline.ui.main.MainViewModel

class MainActivity : BaseBusinessActivity() {

    private val mViewModel: MainViewModel by lazy {
        ViewModelProvider(this,
            MainViewModel.ViewModelFactory()).get(MainViewModel::class.java)
    }

    override val layoutId = R.layout.activity_main

    override fun binds() {
        titleBar.setListener { v, action, extra -> if (action == ACTION_LEFT_TEXT) finish() }
        helloWorld.clicksThrottleFirst().autoDisposable(scopeProvider)
            .subscribe {
                mViewModel.getData()
            }
        mViewModel.observeViewState()
            .observeOn(RxSchedulers.ui)
            .autoDisposable(scopeProvider)
            .subscribe(this::onNewState)
    }

    override fun <T> processData(state: BaseViewState<T>) {
        helloWorld.text = state.result as String
    }
}
