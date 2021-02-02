package com.mtek.goarenopoc.module.photofilter.core

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.isseiaoki.simplecropview.CropImageView
import com.isseiaoki.simplecropview.callback.CropCallback
import com.isseiaoki.simplecropview.callback.LoadCallback
import com.isseiaoki.simplecropview.callback.SaveCallback
import com.mtek.goarenopoc.R
import com.mtek.goarenopoc.base.BaseFragment
import com.mtek.goarenopoc.databinding.FragmentCropBinding
import com.mtek.goarenopoc.ui.MainActivity
import com.mtek.goarenopoc.ui.fragment.splash.SplashViewModel
import com.mtek.goarenopoc.utils.*

class CropFragment : BaseFragment<FragmentCropBinding, SplashViewModel>(SplashViewModel::class) {

    override fun getViewBinding() = FragmentCropBinding.inflate(layoutInflater)

    private var mSourceUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        clickFun()

    }

    private fun init() {
        binding.cropImageView.setCropMode(CropImageView.CropMode.FREE)
        mSourceUri = Uri.parse(arguments?.getString(Constants.SELECTED_URI))

        binding.cropImageView.load(mSourceUri)
            .useThumbnail(true)
            .execute(mLoadCallback)


    }

    private fun clickFun() {
        binding.buttonDone.setSafeOnClickListener {
            binding.progresView.visibility = View.VISIBLE
            binding.cropImageView.crop(mSourceUri)
                .execute(object : CropCallback {
                    override fun onSuccess(cropped: Bitmap) {
                        binding.cropImageView.save(cropped)
                            .execute(mSourceUri, mSaveCallback)
                    }
                    override fun onError(e: Throwable) {
                        flag_error("CropCallBack ${e.message}")
                    }
                })
        }

        binding.buttonRotateLeft.setOnClickListener {
            binding.cropImageView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D)
        }

        binding.buttonRotateRight.setOnClickListener {
            binding.cropImageView.rotateImage(CropImageView.RotateDegrees.ROTATE_M90D)
        }

        binding.btnCancel.setSafeOnClickListener {
            (requireActivity() as MainActivity).onBackPressed()
        }
    }

    private val mLoadCallback: LoadCallback = object : LoadCallback {
        override fun onSuccess() {
            requireContext().extToast("LoadCallBack!!!")
        }

        override fun onError(e: Throwable) {

        }
    }

    private val mSaveCallback: SaveCallback = object : SaveCallback {
            override fun onSuccess(outputUri: Uri) {
                val data = Intent()
                data.data = outputUri
                mSourceUri = outputUri
                val bundle = Bundle()
                bundle.putString(Constants.SELECTED_URI, outputUri.toString())
                findNavController().navigate(
                    R.id.action_cropFragment2_to_photoEditorFragment,
                    bundle
                )
            }

            override fun onError(e: Throwable) {
                flag_error("on Succes ${e.message}")
            }
        }

}