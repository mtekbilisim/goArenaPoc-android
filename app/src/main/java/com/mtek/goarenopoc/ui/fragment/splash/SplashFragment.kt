package com.mtek.goarenopoc.ui.fragment.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mtek.goarenopoc.R
import com.mtek.goarenopoc.base.BaseFragment
import com.mtek.goarenopoc.databinding.FragmentSplashBinding


class SplashFragment : BaseFragment<FragmentSplashBinding,SplashViewModel>(SplashViewModel::class) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun getViewBinding() = FragmentSplashBinding.inflate(layoutInflater)


}