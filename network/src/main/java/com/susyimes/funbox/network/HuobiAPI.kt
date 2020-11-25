package com.susyimes.funbox.network

import com.google.gson.JsonObject
import io.reactivex.Flowable
import retrofit2.http.*

/**
 * Created by Susyimes on 2018/1/23.
 */
interface HuobiAPI {
    @GET("/market/history/kline")
    fun base(@Query("symbol") symbol: String?, @Query("period") period: String?): Flowable<HuobiBaseBean?>?

    @GET
    suspend fun get(@Url url: String, @QueryMap map: HashMap<String, Any>?): JsonObject
}