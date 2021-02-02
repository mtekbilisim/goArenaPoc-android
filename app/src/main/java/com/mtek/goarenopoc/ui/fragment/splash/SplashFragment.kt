package com.mtek.goarenopoc.ui.fragment.splash


import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.mtek.goarenopoc.R
import com.mtek.goarenopoc.base.BaseFragment
import com.mtek.goarenopoc.databinding.FragmentSplashBinding
import com.mtek.goarenopoc.ui.MainActivity
import com.mtek.goarenopoc.utils.popBackStackAllInstances


class SplashFragment : BaseFragment<FragmentSplashBinding,SplashViewModel>(SplashViewModel::class) {

    override fun getViewBinding() = FragmentSplashBinding.inflate(layoutInflater)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        splashTime()
       (requireContext() as MainActivity).hideBottomNav()

    }

   private fun splashTime(){
       val timer = object: CountDownTimer(3000, 1000) {
           override fun onTick(millisUntilFinished: Long) {
               Log.e("TICK","$millisUntilFinished")
           }

           override fun onFinish() {
             findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
               findNavController().graph.startDestination = R.id.loginFragment
           }
       }
       timer.start()
   }




}