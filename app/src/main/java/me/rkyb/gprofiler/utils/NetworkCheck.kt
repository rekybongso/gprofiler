package me.rkyb.gprofiler.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkCheck @Inject constructor(@ApplicationContext private val context: Context) {

    fun isAvailable(): Boolean {
        var result: Boolean
        val connectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.run {

            /*
                Check available network with logical OR operator instead of switch cases.
                If no connection available, the result will be false
            */

            result = getNetworkCapabilities(activeNetwork)?.run {
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                        || hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || hasTransport(NetworkCapabilities.TRANSPORT_VPN)
            } ?: false

        }

        return result
    }

}