package me.sonaive.startline.ui.main

import arrow.core.Either
import io.reactivex.Flowable
import me.sonaive.lab.base.repository.IRemoteDataSource
import me.sonaive.lab.util.RxSchedulers
import me.sonaive.startline.api.Errors
import me.sonaive.startline.api.NetworkManager

/**
 * Created by liutao on 2020-02-24.
 * Describe: 首页获取远程数据的仓库
 */

class MainRemoteRepository private constructor(): IRemoteDataSource {

    companion object {
        val instance: MainRemoteRepository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            MainRemoteRepository()
        }
    }

    fun getData(): Flowable<Either<Errors, String>> {
        return NetworkManager.instance.getOneApiService()
            .getData(10, 1)
            .subscribeOn(RxSchedulers.io)
            .map { Either.right(it.string()) }
    }

}