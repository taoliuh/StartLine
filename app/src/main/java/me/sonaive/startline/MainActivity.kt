package me.sonaive.startline

import android.os.Bundle
import com.uber.autodispose.autoDisposable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import me.sonaive.lab.base.view.activity.BaseActivity
import me.sonaive.lab.logger.logd
import me.sonaive.lab.util.RxSchedulers
import me.sonaive.startline.api.NetworkManager
import okhttp3.ResponseBody
import java.io.IOException

class MainActivity : BaseActivity() {

    override val layoutId = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        helloWorld.setOnClickListener {
            NetworkManager.instance.getOneApiService()
                .getData(10, 1)
                .subscribeOn(RxSchedulers.io)
                .observeOn(RxSchedulers.ui)
                .autoDisposable(scopeProvider)
                .subscribe(getDefaultObserver())
        }
    }

    private fun getDefaultObserver(): Observer<ResponseBody> {
        return object : Observer<ResponseBody> {
            override fun onSubscribe(d: Disposable) {
                logd { "onSubscribe!" }
            }

            override fun onNext(response: ResponseBody) {
                try {
                    val s = response.string()
                    logd { "load success! $s" }
                    helloWorld.text = s
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }

            override fun onError(throwable: Throwable) {
                throwable.printStackTrace()
            }

            override fun onComplete() {
                logd { "load onComplete!" }
            }
        }
    }
}
