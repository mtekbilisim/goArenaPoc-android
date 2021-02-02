package com.mtek.goarenopoc.data.di



import com.mtek.goarenopoc.ui.fragment.home.HomeViewModel
import com.mtek.goarenopoc.ui.fragment.postfragment.PostViewModel
import com.mtek.goarenopoc.ui.fragment.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {


    viewModel {
       HomeViewModel()
    }
    viewModel {
       PostViewModel()
    }
}

