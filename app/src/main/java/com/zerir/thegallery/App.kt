package com.zerir.thegallery

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.zerir.thegallery.base.network.NetworkConnection
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), LifecycleEventObserver {

    @Inject
    lateinit var networkConnection: NetworkConnection

    override fun onCreate() {
        super.onCreate()

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                NetworkConnection.registerCallbacks(
                    getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager,
                    networkConnection.networkRequests,
                    networkConnection.networkCallbacks,
                )
            }
            Lifecycle.Event.ON_STOP -> {
                NetworkConnection.unregisterCallbacks(
                    getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager,
                    networkConnection.networkCallbacks,
                )
            }
            else -> {}
        }
    }

}