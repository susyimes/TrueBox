package com.susyimes.funbox.network

import android.content.Context

/**
 * Created by Susyimes on 2018/1/23.
 */
object Retrofits {
    //val service by lazy { CleanRetrofit().getService()  }
    @Volatile
    private var SINGLETON: FunApi? = null
    fun getService(context: Context): FunApi? {

        if (SINGLETON == null) {
            synchronized(FunApi::class.java) {
                if (SINGLETON == null) {
                    SINGLETON = CleanRetrofit(context.applicationContext).service
                }
            }
        }
        return SINGLETON
    }

}