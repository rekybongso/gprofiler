package me.rkyb.gprofiler.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import me.rkyb.gprofiler.R
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

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
