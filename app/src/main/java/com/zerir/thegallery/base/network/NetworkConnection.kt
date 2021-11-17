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
    val connected: LiveData<Boolean> get() = _connected

    fun getNetworkRequest(): NetworkRequest {
        return NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
    }

    fun getNetworkCallBack(): ConnectivityManager.NetworkCallback {
        return object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                _connected.postValue(true)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                _connected.postValue(false)
            }
        }
    }

}