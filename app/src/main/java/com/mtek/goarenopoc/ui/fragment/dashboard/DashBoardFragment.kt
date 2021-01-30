package com.mtek.goarenopoc.ui.fragment.dashboard

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mtek.goarenopoc.R
import com.mtek.goarenopoc.base.BaseFragment
import com.mtek.goarenopoc.databinding.FragmentDashBoardBinding
import com.mtek.goarenopoc.utils.flag_error
import com.mtek.goarenopoc.utils.setSafeOnClickListener


class DashBoardFragment : BaseFragment<FragmentDashBoardBinding,DashboardViewModel>(DashboardViewModel::class) {
    override fun getViewBinding()= FragmentDashBoardBinding.inflate(layoutInflater)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        flag_error("onAttach Dashboard")
    }

    override fun onDetach() {
        super.onDetach()
        flag_error("onDetach Dashboard")
    }
}