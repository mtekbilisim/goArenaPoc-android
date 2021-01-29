package com.mtek.goarenopoc.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.mtek.goarenopoc.module.progress.ProgressBar
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

abstract class BaseFragment<VB : ViewBinding, out BVM : BaseViewModel<BaseRepository>>(
    viewModelType: KClass<out BVM>
) : Fragment() {

    protected val viewModel: BVM by viewModel(viewModelType)
    protected val progressBar: ProgressBar by lazy { ProgressBar.newInstance(viewModel.showProgress) }
    lateinit var binding: VB
    abstract fun getViewBinding(): VB
    // OBSERVERS
    protected val observerProgressBar: Observer<Boolean> = Observer {
        if (it) {
            if (!progressBar.isProgressShown()) {
                progressBar.show(requireActivity().supportFragmentManager)
            }
            return@Observer
        }
        if (progressBar.isProgressShown()) {
            progressBar.hide()
        }
    }

    protected val observerErrMsg: Observer<BaseErrorModel> = Observer {
//        if (it != ErrorType.SUCCESS) {
//            Utils.showAlert(requireContext(), msg = getString(ErrorManager.checkRuleSetMessage(it)))
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding()
        return  binding.root
    }

    protected inline fun viewModel(action: BVM.() -> Unit) {
        action(viewModel)
        viewModel.showProgress.observe(this, observerProgressBar)
        viewModel.errMsg?.observe(this, observerErrMsg)
    }

    protected inline fun viewBinding(action: VB.() -> Unit) = action(binding)

}