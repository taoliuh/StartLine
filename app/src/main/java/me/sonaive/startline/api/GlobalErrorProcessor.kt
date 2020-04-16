package me.sonaive.startline.api

import com.github.qingmei2.core.GlobalErrorTransformer
import io.reactivex.Single
import me.sonaive.lab.base.ApiException
import me.sonaive.lab.logger.loge
import me.sonaive.lab.util.RxSchedulers
import me.sonaive.lab.util.isNetworkAvailable
import me.sonaive.startline.App
import me.sonaive.startline.api.KeyValueErrors.getMessage
import me.sonaive.startline.utils.toast
import retrofit2.HttpException

fun <T> globalHandleError(): GlobalErrorTransformer<T> = GlobalErrorTransformer(
        globalDoOnErrorConsumer = { error ->
            var result = error.message ?: "unknown error"
            if (error is HttpException) {
                result = error.code().toString() + " "
                result += error.response().errorBody()?.string() ?: ""
            } else if (error is ApiException) {
                result = "code = " + error.code +
                        ", message = " + error.message
            }
            if (!isNetworkAvailable(App.instance())) {
                result = getMessage(-1)
            }
            Single.just(result).observeOn(RxSchedulers.ui)
                .subscribe { t ->
                    if (t != null && t.isNotEmpty()) {
                        toast { t }
                    }
                }
            loge { "global handle error, $error" }
        }
)