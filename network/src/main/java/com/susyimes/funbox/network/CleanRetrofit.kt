package com.susyimes.funbox.network

import android.content.Context
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class CleanRetrofit internal constructor(context: Context) {
    //    Cache providesCache() {
    //        File httpCacheFile = FileUtils.getDiskCacheDir("response");
    //        return new Cache(httpCacheFile, 1024 * 100 * 1024);
    //    }
    val service: FunApi
    private val mContext: Context = context.applicationContext
    private fun okHttpClient(context: Context): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().cache(Cache(context.cacheDir, CACHE_SIZE_BYTES.toLong()))
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(object : Interceptor {
                    @Throws(IOException::class)
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val oldRequest = chain.request()
                        val authorizedUrlBuilder = oldRequest.url
                                .newBuilder()
                                .scheme(oldRequest.url.scheme)
                                .host(oldRequest.url.host)
                        val newRequest = oldRequest.newBuilder()
                                .method(oldRequest.method, oldRequest.body)
                                .header("Content-Type", "application/json")
                                .header("User-Agent", "Chrome/39.0.2171.71")
                                .url(authorizedUrlBuilder.build())
                                .build()
                        return chain.proceed(newRequest)
                    }
                })
                .addInterceptor(logging) //.addInterceptor(new AddHeaderInterceptor())
                //.cache(providesCache())
                .build()
    }

    companion object {
        var debug = true
        private const val CACHE_SIZE_BYTES = 1024 * 1024 * 2
        var ENDPOINT = "https://api.huobi.pro/market/"
    }

    init {
        val pkName = mContext.packageName
        val gson = GsonBuilder()
                .setExclusionStrategies(object : ExclusionStrategy {
                    override fun shouldSkipField(f: FieldAttributes): Boolean {
                        return false
                    }

                    override fun shouldSkipClass(clazz: Class<*>?): Boolean {
                        return false
                    }
                }).setLenient() //.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
                .create()
        val builder = Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(okHttpClient(context))
        val retrofit = builder.build()
        service = retrofit.create(FunApi::class.java)
    }
}