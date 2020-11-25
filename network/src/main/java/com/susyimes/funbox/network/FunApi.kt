package com.susyimes.funbox.network

import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface FunApi {
    @GET
    suspend fun get(@Url url: String, @QueryMap map: HashMap<String, Any>?): Any?
    @POST
    suspend fun post(@Url url: String): Any?
}