package me.sonaive.lab.base.viewmodel

import me.sonaive.lab.base.viewstate.BaseViewState
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by liutao on 2020-03-02.
 * Describe: 抽象业务ViewModel
 */
open class BaseBusinessViewModel<T>: BaseViewModel() {
    protected val mViewStateSubject: BehaviorSubject<BaseViewState<T>> =
        BehaviorSubject.createDefault(BaseViewState.initial())

    fun observeViewState(): Observable<BaseViewState<T>> {
        return mViewStateSubject.hide().distinctUntilChanged()
    }
}