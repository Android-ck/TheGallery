package com.zerir.thegallery.base.network

import android.content.Context
import android.net.ConnectivityManager
import com.zerir.thegallery.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import java.io.IOException
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class RemoteDataSource @Inject constructor(
    private val networkConnectionInterceptor: NetworkConnectionInterceptor
) {

    companion object {
        private const val BASE_URL = "https://pixabay.com/api/"
    }

    fun <Api> buildApi(api: Class<Api>): Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(buildRetrofitClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }

    private fun buildRetrofitClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(networkConnectionInterceptor).also { client ->
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
                }
            }.build()
    }

}

class NetworkConnectionInterceptor @Inject constructor(
    @ApplicationContext context: Context,
) : Interceptor {

    private val applicationContext = context.applicationContext

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected) {
            throw NoConnectivityException()
        }
        val builder: Request.Builder = chain.request().newBuilder().also {
                it.addHeader("Accept", "application/json")
            }
        return chain.proceed(builder.build())
    }

    //TODO: not use deprecated
    private val isConnected: Boolean
        get() {
            val connectivityManager =
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = connectivityManager.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
        }
}

class NoConnectivityException(message: String = "No Internet Connection") : IOException(message)