package me.sonaive.startline.ui.main

import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import me.sonaive.lab.base.viewmodel.BaseViewModel
import me.sonaive.lab.ext.reactivex.onNextWithLast
import me.sonaive.startline.Result
import me.sonaive.startline.api.globalHandleError
import java.util.concurrent.TimeUnit

/**
 * Created by liutao on 2020-02-24.
 * Describe: 主页业务逻辑
 */
class MainViewModel: BaseViewModel() {

    private val mViewStateSubject: BehaviorSubject<MainViewState> =
        BehaviorSubject.createDefault(MainViewState.initial())

    fun observeViewState(): Observable<MainViewState> {
        return mViewStateSubject.hide().distinctUntilChanged()
    }

    fun getData() {
        MainRemoteRepository.instance.getData().compose(globalHandleError())
            .delay(2, TimeUnit.SECONDS, Schedulers.io())
            .map { either ->
                either.fold({
                    Result.failure<String>(it)
                }, {
                    Result.success(it)
                })
            }
            .startWith(Result.loading())
            .startWith(Result.idle())
            .onErrorReturn { Result.failure(it) }
            .autoDisposable(this)
            .subscribe {
                state ->
                when (state) {
                    is Result.Loading -> mViewStateSubject.onNextWithLast {
                        it.copy(isLoading = true, throwable = null, result = null)
                    }
                    is Result.Idle -> mViewStateSubject.onNextWithLast {
                        it.copy(isLoading = false, throwable = null, result = null)
                    }
                    is Result.Failure -> mViewStateSubject.onNextWithLast {
                        it.copy(isLoading = true, throwable = state.error, result = null)
                    }
                    is Result.Success -> mViewStateSubject.onNextWithLast {
                        it.copy(isLoading = true, throwable = null, result = state.data)
                    }
                }
            }
    }
}