package com.mtek.goarenopoc.data.di



import com.mtek.goarenopoc.data.network.api.ApiService
import com.mtek.goarenopoc.data.repository.SplashRepository
import org.koin.core.qualifier.TypeQualifier
import org.koin.dsl.module
import retrofit2.Retrofit

inline fun <reified T> createApiInstance(retrofit: Retrofit): T =
    retrofit.create(T::class.java)

val apiModule = module {
    //API
    factory { createApiInstance<ApiService>(get()) }

   // single(TypeQualifier(SplashRepository::class)) { SplashRepository(get()) }

}