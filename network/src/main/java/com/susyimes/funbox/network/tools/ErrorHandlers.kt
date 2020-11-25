package com.susyimes.funbox.network.tools

import android.content.Context
import android.util.Log
import android.widget.Toast
import io.reactivex.functions.Consumer
import retrofit2.HttpException
import java.net.UnknownHostException

object ErrorHandlers {
    private val TAG = ErrorHandlers::class.java.simpleName
    fun displayError(context: Context?, message: String?) {
        if (context == null) {
            return
        }
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun displayError(context: Context?, throwable: Throwable?) {
        if (context == null) {
            Log.e(TAG, "[300] " + "Context is null")
            return
        }
        var errorMessage: String? = null
        if (throwable is HttpException) {
            errorMessage = Errors.errorResponse(throwable).error
        } else if (throwable is UnknownHostException) {
            errorMessage = "no network"
        }
        if (errorMessage != null) {
            displayError(context, errorMessage)
        } else {
            Log.e(TAG, "[301]", throwable)
            displayError(context, Errors.errorMessage(throwable))
        }
    }

    fun displayErrorConsumer(context: Context?): Consumer<Throwable> {
        return Consumer { throwable -> displayError(context, throwable) }
    }
}