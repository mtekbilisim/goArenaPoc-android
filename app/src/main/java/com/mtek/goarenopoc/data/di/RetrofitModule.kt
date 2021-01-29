package com.mtek.goarenopoc.data.di




import com.google.gson.GsonBuilder
import com.mtek.goarenopoc.BuildConfig
import com.mtek.goarenopoc.data.network.client.UserTokenHeader
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val retrofitModule = module {
    factory { getOkHttpClient() }
    factory { GsonBuilder().create() }
    factory { GsonConverterFactory.create((get())) }
    factory { getRetrofitInstance(get(), get()) }
}

const val API_TIMEOUT = 15L

private fun getRetrofitInstance(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory
): Retrofit =
    with(Retrofit.Builder()) {
        baseUrl(BuildConfig.BASE_URL)
        client(okHttpClient)
        addConverterFactory(gsonConverterFactory)
        build()
    }

private fun getOkHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
    addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.HEADERS
        level = HttpLoggingInterceptor.Level.BODY

    })
    addInterceptor(UserTokenHeader())
    callTimeout(API_TIMEOUT, TimeUnit.SECONDS)
    connectTimeout(API_TIMEOUT, TimeUnit.SECONDS)
    readTimeout(API_TIMEOUT, TimeUnit.SECONDS)
}.run {
    build()
}
