package com.mtek.goarenopoc.base


import android.R
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.mtek.goarenopoc.module.progress.ProgressBar
import com.mtek.goarenopoc.utils.errorControl
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass


abstract class BaseActivity<VB : ViewBinding, out BVM : BaseViewModel<BaseRepository>>(
    viewModelType: KClass<out BVM>
) : AppCompatActivity(){


    val viewModel : BVM by viewModel(viewModelType)
    private val progressBar : ProgressBar by lazy {
        ProgressBar.newInstance(viewModel.showProgress)
    }

    protected  val observerProgressBar : Observer<Boolean> = Observer {
        if (it){
            progressBar.show(supportFragmentManager)
            return@Observer
        }

        if (progressBar.isProgressShown()){
            progressBar.hide()
        }


    }

    protected  val observerErrMsg : Observer<BaseErrorModel> = Observer {
        it?.code?.let { code -> it?.error?.let { message -> errorControl(code, message) } } }

    lateinit var binding: VB
    abstract fun getViewBinding(): VB


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = getViewBinding()
        setContentView(binding.root)

    }



    protected  inline  fun viewModel(action: BVM.() -> Unit){
        action(viewModel)
        viewModel.showProgress.observe(this, observerProgressBar)
        viewModel.errMsg?.observe(this, observerErrMsg)
    }


    fun showProgress() = progressBar.show(supportFragmentManager)


    fun hideProgress() = progressBar.hide()


    protected inline fun viewBinding(action: VB.() -> Unit) = action(binding)




//    fun baseSuccessDialog(message: String? = "", manager: FragmentManager, callback: () -> Unit) = SuccessDialog.newInstance(message!!, callback).show(manager, SuccessDialog.TAG)
//
//    fun baseInfoDialog(message: String? = "", manager: FragmentManager, callback: () -> Unit) = InfoDialog.newInstance(message!!, callback).show(manager, InfoDialog.TAG)
//
//    fun baseErrorDialog(message: String?,manager : FragmentManager, callback: () -> Unit) = ErrorDialog.newInstance(message!!,callback).show(manager, ErrorDialog.TAG)

}