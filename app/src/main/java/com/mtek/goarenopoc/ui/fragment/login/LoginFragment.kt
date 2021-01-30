package com.mtek.goarenopoc.ui.fragment.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mtek.goarenopoc.R
import com.mtek.goarenopoc.base.BaseFragment
import com.mtek.goarenopoc.databinding.FragmentLoginBinding
import com.mtek.goarenopoc.ui.fragment.splash.SplashViewModel
import com.mtek.goarenopoc.utils.popBackStackAllInstances
import com.mtek.goarenopoc.utils.setSafeOnClickListener


class LoginFragment : BaseFragment<FragmentLoginBinding,SplashViewModel>(SplashViewModel::class) {

    override fun getViewBinding() = FragmentLoginBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnLogin.setSafeOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

}