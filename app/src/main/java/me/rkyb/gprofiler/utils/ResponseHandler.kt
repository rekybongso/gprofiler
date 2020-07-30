package me.rkyb.gprofiler.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import me.rkyb.gprofiler.R
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

// A class to handle response from api call

class ResponseHandler @Inject constructor (
    @ApplicationContext private val context: Context){

    enum class ErrorCodes(val code: Int) {
        SocketTimeOut(-1)
    }

    fun <T : Any> handleSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    fun <T : Any> handleException(e: Exception): Resource<T> {
        return when (e) {
            is UnknownHostException -> Resource.error(context.getString(R.string.host_notice),null)
            is HttpException -> Resource.error(getErrorMessage(e.code()), null)
            is SocketTimeoutException -> Resource.error(getErrorMessage(ErrorCodes.SocketTimeOut.code), null)
            else -> Resource.error(getErrorMessage(Int.MAX_VALUE), null)
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            ErrorCodes.SocketTimeOut.code -> context.getString(R.string.timeout_notice)
            401 -> context.getString(R.string.unauthorised_notice)
            404 -> context.getString(R.string.server_not_found_notice)
            else -> context.getString(R.string.error_notice)
        }
    }

}

