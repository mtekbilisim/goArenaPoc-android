package com.mtek.goarenopoc.data.di



import com.mtek.goarenopoc.data.network.api.ApiService
import com.mtek.goarenopoc.data.repository.*
import com.mtek.goarenopoc.data.repository.HomeRepository
import com.mtek.goarenopoc.data.repository.LoginRepository
import com.mtek.goarenopoc.data.repository.PostRepository
import com.mtek.goarenopoc.data.repository.DashboardRepository
import com.mtek.goarenopoc.data.repository.SplashRepository
import org.koin.core.qualifier.TypeQualifier
import org.koin.dsl.module
import retrofit2.Retrofit

inline fun <reified T> createApiInstance(retrofit: Retrofit): T =
    retrofit.create(T::class.java)

val apiModule = module {
    //API
    factory { createApiInstance<ApiService>(get()) }
    single(TypeQualifier(DashboardRepository::class)) { DashboardRepository(get()) }

    single(TypeQualifier(HomeRepository::class)) { HomeRepository(get()) }
    single(TypeQualifier(PostRepository::class)) { PostRepository(get()) }
 single(TypeQualifier(LoginRepository::class)) { LoginRepository(get()) }

}