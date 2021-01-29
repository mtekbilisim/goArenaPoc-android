package com.mtek.goarenopoc.data.di



import com.mtek.goarenopoc.data.network.api.ApiService
import org.koin.dsl.module
import retrofit2.Retrofit

inline fun <reified T> createApiInstance(retrofit: Retrofit): T =
    retrofit.create(T::class.java)

val apiModule = module {
    //API
    factory { createApiInstance<ApiService>(get()) }

}