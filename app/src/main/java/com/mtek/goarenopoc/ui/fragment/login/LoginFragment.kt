package com.mtek.goarenopoc.ui.fragment.login

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mtek.BaseApp
import com.mtek.goarenopoc.R
import com.mtek.goarenopoc.base.BaseActivity
import com.mtek.goarenopoc.base.BaseFragment
import com.mtek.goarenopoc.data.model.Feed
import com.mtek.goarenopoc.data.model.FeedModel
import com.mtek.goarenopoc.data.model.TokenModel
import com.mtek.goarenopoc.data.network.request.LoginRequestModel
import com.mtek.goarenopoc.data.network.response.FeedResponseModel
import com.mtek.goarenopoc.data.network.response.UserResponseModel
import com.mtek.goarenopoc.databinding.FragmentLoginBinding
import com.mtek.goarenopoc.ui.fragment.SharedViewModel
import com.mtek.goarenopoc.utils.*
import com.mtek.goarenopoc.utils.manager.LocalDataManager
import com.mtek.goarenopoc.utils.manager.UserManager


class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(LoginViewModel::class) {

    override fun getViewBinding() = FragmentLoginBinding.inflate(layoutInflater)

    private val observerFeed: Observer<FeedResponseModel> = Observer {
        if (it != null) {
            val data = Feed()
            data.data = it.data
            sharedViewModel.setFeedList(data)
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

        }

    }

    private val observerToken: Observer<TokenModel> = Observer {
        if (it != null) {
            Constants.ACCESS_TOKEN = it.access_token
            Constants.TOKEN_TYPE = it.token_type!!
            if (LocalDataManager.instance.getSharedPreferenceBoolean(
                    BaseApp.appContext,
                    Constants.SAVE_INFORMATION,
                    false
                )
            ) {
                LocalDataManager.instance.setSharedPreferenceString(
                    BaseApp.appContext,
                    Constants.EMAIL,
                    binding.txtEmail.text
                )
                LocalDataManager.instance.setSharedPreferenceString(
                    BaseApp.appContext,
                    Constants.XPS,
                    binding.txtXps.text
                )
            }
            LocalDataManager.instance.setSharedPreferenceString(
                BaseApp.appContext,
                "token",
                it.access_token
            )
            viewModel.senRequestInformation()
        }
    }

    private val observerInformation: Observer<UserResponseModel> = Observer {
        if (it != null) {
            UserManager.instance.user = it.data
            viewModel.senRequestVFeed()

        }
    }

    lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel {
            responseToken.observe(viewLifecycleOwner, observerToken)
            responseUserInformation.observe(viewLifecycleOwner, observerInformation)
            feedResponse.observe(viewLifecycleOwner,observerFeed)
        }
        clickFun()
        rememberControl()
    }

    private fun rememberControl() {
        if (LocalDataManager.instance.getSharedPreferenceBoolean(
                BaseApp.appContext,
                Constants.SAVE_INFORMATION,
                false
            )
        ) {
            viewBinding {
                rememberMe.isChecked = true
            }
            viewBinding {
                txtEmail.text = LocalDataManager.instance.getSharedPreferenceString(
                    BaseApp.appContext,
                    Constants.EMAIL,
                    ""
                )
                txtXps.text = LocalDataManager.instance.getSharedPreferenceString(
                    BaseApp.appContext,
                    Constants.XPS,
                    ""
                )
            }

        }
    }

    private fun clickFun() {
        viewBinding {

            rememberMe.setOnCheckedChangeListener { _, p1 ->
                if (p1) {
                    LocalDataManager.instance.setSharedPreferenceBoolean(
                        BaseApp.appContext,
                        Constants.SAVE_INFORMATION,
                        true
                    )
                } else {
                    LocalDataManager.instance.setSharedPreferenceBoolean(
                        BaseApp.appContext,
                        Constants.SAVE_INFORMATION,
                        false
                    )

                }
            }
            btnLogin.setSafeOnClickListener {
                if (validate()) {
                    if (LocalDataManager.instance.getSharedPreferenceString(
                            BaseApp.appContext,
                            Constants.EMAIL,
                            ""
                        ) != binding.txtEmail.text
                    ) {
                        LocalDataManager.instance.setSharedPreferenceString(
                            BaseApp.appContext,
                            Constants.EMAIL,
                            ""
                        )
                        LocalDataManager.instance.setSharedPreferenceString(
                            BaseApp.appContext,
                            Constants.XPS,
                            ""
                        )
                        LocalDataManager.instance.setSharedPreferenceString(
                            BaseApp.appContext,
                            "token",
                            ""
                        )
                        Constants.TOKEN_TYPE = emptyString()

                    }
                    viewModel.senRequestToken(
                        LoginRequestModel(
                            binding.txtEmail.text.toString(),
                            binding.txtXps.text.toString()
                        )
                    )
                }

            }
        }

    }

    private fun validate(): Boolean {
        when {
            !binding.txtEmail.validateUsername(1) -> {
                requireContext().extToast(getString(R.string.e_mail_error_message))
                return false
            }
            !binding.txtXps.validateUsername(2) -> {
                requireContext().extToast("Geçerli şifre giriniz(Minimum 6 karakter)")
                return false
            }
        }
        return true
    }

}