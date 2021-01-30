package com.mtek.goarenopoc.ui.fragment.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mtek.goarenopoc.R
import com.mtek.goarenopoc.base.BaseFragment
import com.mtek.goarenopoc.databinding.FragmentProfileBinding
import com.mtek.goarenopoc.ui.fragment.home.HomeViewModel


class ProfileFragment : BaseFragment<FragmentProfileBinding,HomeViewModel>(HomeViewModel::class) {

    override fun getViewBinding()= FragmentProfileBinding.inflate(layoutInflater)


}