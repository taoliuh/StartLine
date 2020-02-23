package me.sonaive.startline.api

import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by liutao on 2020-02-24.
 * Describe: 测试gank接口
 */
interface OneApiService {
    @GET("/api/data/Android/{size}/{page}")
    fun getData(@Path("size") size: Int,
                @Path("page") page: Int): Flowable<ResponseBody>
}