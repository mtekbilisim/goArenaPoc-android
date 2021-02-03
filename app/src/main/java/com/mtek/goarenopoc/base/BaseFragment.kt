package com.mtek.goarenopoc.base


import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.mtek.goarenopoc.R
import com.mtek.goarenopoc.module.progress.ProgressBar
import com.mtek.goarenopoc.ui.MainActivity
import com.mtek.goarenopoc.utils.errorControl
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

            it?.let {
                errorControl(it)
            }


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

    private val READ_WRITE_STORAGE = 52
    private var mProgressDialog: ProgressDialog? = null

    open fun requestPermission(permission: String): Boolean {
        val isGranted =
            ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED
        if (!isGranted) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(permission),
                READ_WRITE_STORAGE
            )
        }
        return isGranted
    }

    open fun isPermissionGranted(isGranted: Boolean, permission: String?) {}

    open fun makeFullScreen() {
        requireActivity().requestWindowFeature(Window.FEATURE_NO_TITLE)
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            READ_WRITE_STORAGE -> isPermissionGranted(
                grantResults[0] == PackageManager.PERMISSION_GRANTED,
                permissions[0]
            )
        }
    }

    open fun showLoading(message: String) {
        mProgressDialog = ProgressDialog(requireContext())
        mProgressDialog!!.setMessage(message)
        mProgressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        mProgressDialog!!.setCancelable(false)
        mProgressDialog!!.show()
    }

    open fun hideLoading() {
        if (mProgressDialog != null) {
            mProgressDialog!!.dismiss()
        }
    }






}