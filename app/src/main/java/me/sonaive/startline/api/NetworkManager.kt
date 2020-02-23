package me.sonaive.startline.api

import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import me.sonaive.startline.api.Api.APP_DEFAULT_DOMAIN
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by liutao on 2020-02-24.
 * Describe: 网络api管理器
 */
class NetworkManager private constructor() {
    private var mOkHttpClient: OkHttpClient
    private var mRetrofit: Retrofit
    private var mOneApiService: OneApiService

    companion object {
        val instance: NetworkManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkManager()
        }
    }

    init {
        mOkHttpClient =
            RetrofitUrlManager.getInstance().with(OkHttpClient.Builder())
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build()

        mRetrofit = Retrofit.Builder()
            .baseUrl(APP_DEFAULT_DOMAIN)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient)
            .build()
        mOneApiService = mRetrofit.create(OneApiService::class.java)
    }

    fun getOneApiService(): OneApiService {
        return mOneApiService
    }

}