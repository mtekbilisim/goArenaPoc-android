package com.mtek.goarenopoc.ui.fragment.postfragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import com.mtek.goarenopoc.R
import com.mtek.goarenopoc.base.BaseAdapter
import com.mtek.goarenopoc.base.BaseFragment
import com.mtek.goarenopoc.databinding.FragmentPostBinding
import com.mtek.goarenopoc.ui.MainActivity
import com.mtek.goarenopoc.ui.fragment.splash.SplashViewModel
import com.mtek.goarenopoc.ui.fragment.splash.TestViewModel
import com.mtek.goarenopoc.utils.Constants
import com.mtek.goarenopoc.utils.emptyString
import com.mtek.goarenopoc.utils.setSafeOnClickListener
import pl.aprilapps.easyphotopicker.*
import java.io.File
import java.io.InputStream


class PostFragment : BaseFragment<FragmentPostBinding, SplashViewModel>(SplashViewModel::class) {

    override fun getViewBinding() = FragmentPostBinding.inflate(layoutInflater)

    private var easyImage: EasyImage? = null
    private var returnedPhotos: Array<MediaFile>? = null
    private var photos: ArrayList<MediaFile>? = arrayListOf()
    private var baseAdapter: BaseAdapter<MediaFile>? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Constants.filterUriStr != "") {
            val uri = Uri.parse(Constants.filterUriStr)
            val mediaFile = MediaFile(uri,uri.toFile())
            photos?.add(mediaFile)
            baseAdapter?.setList(photos)
            Constants.filterUriStr = emptyString()
        }

        edtContentControl()
        clickFun()
        setAdapter()

    }

    private fun setAdapter(){
        baseAdapter = BaseAdapter(
            requireContext(), R.layout.row_item_post_thumnail_layout,
            photos
        ) { v, item, position ->

        }
        binding.recyclerView.adapter = baseAdapter

    }

    private fun clickFun(){
        binding.btnCancel.setSafeOnClickListener {
            (requireActivity() as MainActivity).onBackPressed()
        }

        if (binding.etContent.isFocusable) {
            (requireActivity() as MainActivity).hideBottomNav()
        }
        init()
        binding.imgCamera.setSafeOnClickListener {
            openCamera()
        }
        binding.imgGallery.setSafeOnClickListener {
            val necessaryPermissions =
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (arePermissionsGranted(necessaryPermissions)) {
                easyImage!!.openGallery(this)
            } else {
                requestPermissionsCompat(
                    necessaryPermissions,
                    Constants.CHOOSER_PERMISSIONS_REQUEST_CODE
                )
            }
        }
    }



    private fun edtContentControl(){
        binding.etContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                val last = "${400 - p0.toString().length}"
                binding.postMaxValue.text = "${p0.toString().length} / 400"
                if (p0.toString().length > 400) {
                    binding.postMaxValue.setTextColor(
                        resources.getColor(
                            R.color.red_color_picker,
                            null
                        )
                    )
                } else {
                    binding.postMaxValue.setTextColor(
                        resources.getColor(
                            R.color.colorPrimary,
                            null
                        )
                    )
                }
            }

        })

    }

   private fun openCamera() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED
        ) {
            val permissions = arrayOf(android.Manifest.permission.CAMERA);
            requestPermissions(permissions, Constants.PERMISSION_CODE_CAMERA)
        } else {
            easyImage?.openCameraForImage(this)
        }
    }

    private fun init() {
        easyImage = EasyImage.Builder(requireContext())
            .setChooserTitle("Resim seçme")
            .setCopyImagesToPublicGalleryFolder(false)
            .setChooserType(ChooserType.CAMERA_AND_GALLERY)
            .allowMultiple(true)
            .build()
    }

    private fun requestPermissionsCompat(permissions: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(
            (requireActivity() as MainActivity),
            permissions,
            requestCode
        )
    }

    private fun arePermissionsGranted(permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.CHOOSER_PERMISSIONS_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            easyImage?.openChooser(requireActivity())
        } else if (requestCode == Constants.CAMERA_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            easyImage?.openCameraForImage(requireActivity())
        } else if (requestCode == Constants.CAMERA_VIDEO_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            easyImage?.openCameraForVideo(requireActivity())
        } else if (requestCode == Constants.GALLERY_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            easyImage?.openGallery(requireActivity())
        } else if (requestCode == Constants.DOCUMENTS_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            easyImage?.openDocuments(requireActivity())
        }
    }

    private fun onPhotosReturned(returnedPhotos: Array<MediaFile>) {
        photos!!.addAll(returnedPhotos)
        baseAdapter?.setList(photos)
        binding.recyclerView.scrollToPosition(photos!!.size - 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        easyImage!!.handleActivityResult(
            requestCode,
            resultCode,
            data,
            requireActivity(),
            object : DefaultCallback() {
                override fun onMediaFilesPicked(
                    imageFiles: Array<MediaFile>,
                    source: MediaSource
                ) {

                    val hereUrl: Uri = imageFiles[0].file.toUri()
                    val bundle = Bundle()
                    bundle.putString("SelectedImageUri", hereUrl.toString())
                    if (imageFiles.size == 1) {
                        findNavController().navigate(
                            R.id.action_postFragment_to_photoEditorFragment2,
                            bundle
                        )
                        return
                    }

                    returnedPhotos = imageFiles
                    onPhotosReturned(returnedPhotos!!)

                }

                override fun onImagePickerError(error: Throwable, source: MediaSource) {
                    error.printStackTrace()

                }

                override fun onCanceled(source: MediaSource) {
                }


            })
    }





}