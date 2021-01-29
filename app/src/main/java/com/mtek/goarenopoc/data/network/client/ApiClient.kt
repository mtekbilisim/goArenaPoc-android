package com.mtek.goarenopoc.data.network.client


import com.google.gson.GsonBuilder
import com.mtek.goarenopoc.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val API_TIMEOUT = 15L

class ApiClient {
    private fun getClient(): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
        return with(retrofit, {
            baseUrl(BuildConfig.BASE_URL)
            addConverterFactory(GsonConverterFactory.create(gson))
            client(client())
            this.build()
        })
    }

    private fun client(): OkHttpClient {
        return with(OkHttpClient.Builder(), {
            addInterceptor(UserTokenHeader())
            callTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            connectTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            this.build()
        })
    }

    fun <S> createService(serviceClass: Class<S>): S {
        return getClient().create(serviceClass)
    }
}