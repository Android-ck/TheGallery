package com.zerir.thegallery.base.network

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class NetworkConnection @Inject constructor() {

    private val _connected = MutableLiveData(false)
    val connectionState: LiveData<Boolean> get() = _connected

    val networkRequests: NetworkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    val networkCallbacks: ConnectivityManager.NetworkCallback =
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                _connected.postValue(true)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                _connected.postValue(false)
            }
        }

    companion object {

        fun registerCallbacks(
            manager: ConnectivityManager,
            requests: NetworkRequest,
            callbacks: ConnectivityManager.NetworkCallback,
        ) {
            manager.registerNetworkCallback(requests, callbacks)
        }

        fun unregisterCallbacks(
            manager: ConnectivityManager,
            callbacks: ConnectivityManager.NetworkCallback,
        ) {
            try {
                manager.unregisterNetworkCallback(callbacks)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

}