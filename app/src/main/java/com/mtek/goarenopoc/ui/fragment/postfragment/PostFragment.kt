package com.mtek.goarenopoc.ui.fragment.postfragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mtek.goarenopoc.R
import com.mtek.goarenopoc.base.BaseAdapter
import com.mtek.goarenopoc.base.BaseFragment
import com.mtek.goarenopoc.data.model.*
import com.mtek.goarenopoc.data.network.response.FeedUpdateResponseModel
import com.mtek.goarenopoc.data.network.response.FileResponseModel
import com.mtek.goarenopoc.data.network.response.MediaModelResponseModel
import com.mtek.goarenopoc.data.network.response.PostResponseModel
import com.mtek.goarenopoc.databinding.FragmentPostBinding
import com.mtek.goarenopoc.ui.MainActivity
import com.mtek.goarenopoc.ui.fragment.SharedViewModel
import com.mtek.goarenopoc.utils.*
import com.mtek.goarenopoc.utils.manager.UserManager
import okhttp3.MultipartBody
import pl.aprilapps.easyphotopicker.*


class PostFragment : BaseFragment<FragmentPostBinding, PostViewModel>(PostViewModel::class) {

    override fun getViewBinding() = FragmentPostBinding.inflate(layoutInflater)

    private val observerFile: Observer<FileResponseModel> = Observer {
        if (it != null) {
            fileResponseList = it.data as ArrayList<FileModel>
            sendPost()
        }

    }

    private val observerRequest: Observer<PostResponseModel> = Observer {
        if (it != null) {
            flag_error("${it.toString()}")
            val sendList = ArrayList<MediaModel>()
            for (i in fileResponseList){
                sendList.add(MediaModel(0,i.fileDownloadUri,i.fileType,it.data?.id,it.data?.userId))
            }
            viewModel.sendCompletedFeed(it.data?.id.toString(),sendList)
        }

    }

    private val observerUpdateFeed : Observer<FeedUpdateResponseModel> = Observer {
        if (it != null){
            cleanForm()
            sharedViewModel.setFilteredImage("")
            sharedViewModel.setPostValue("")
            sharedViewModel.setUpdateFeedModel(FeedModel(null,null,null,null,null,null,null,null,null))
            progressBar.hide()
            updateFeed = false
            findNavController().navigate(R.id.action_postFragment2_to_homeFragment)

        }
    }

    private val observerCompleted: Observer<MediaModelResponseModel> = Observer {
        if (it != null) {
            flag_error("${it.toString()}")

            cleanForm()
            sharedViewModel.setFilteredImage("")
            sharedViewModel.setPostValue("")

            progressBar.hide()
            findNavController().navigate(R.id.action_postFragment2_to_homeFragment)


        }

    }


    private var fileResponseList : ArrayList<FileModel> = arrayListOf()
    private var easyImage: EasyImage? = null
    private var returnedPhotos: Array<MediaFile>? = null
    private var mediaType: ArrayList<MultipartBody.Part> = arrayListOf()
    private var photos: ArrayList<MediaFile>? = arrayListOf()
    private var baseAdapter: BaseAdapter<MediaFile>? = null
    lateinit var sharedViewModel: SharedViewModel
    private var updateFeed : Boolean = false
    private var updateFeedModel : FeedModel?= null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel {
            responseFile.observe(viewLifecycleOwner, observerFile)
            responseFeed.observe(viewLifecycleOwner, observerRequest)
            responseCompleted.observe(viewLifecycleOwner, observerCompleted)
            responseUpdateFeed.observe(viewLifecycleOwner,observerUpdateFeed)
        }
        updateUI()
        edtContentControl()
        clickFun()
        setAdapter()

    }

    private fun updateUI() {
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        sharedViewModel.getFeedUpdateIdModel().observe(viewLifecycleOwner,{
            if (it!= null){
                binding.etContent.setText(it.title)
                binding.etContent.requestFocus()
                updateFeed = true
                updateFeedModel = it
            }
        })

        sharedViewModel.getPostValue().observe(viewLifecycleOwner, Observer {
            binding.etContent.setText(it)
        })

        sharedViewModel.getFilteredImage().observe(viewLifecycleOwner, Observer {
            if (it != "") {
                val uri = Uri.parse(it)
                val mediaFile = MediaFile(uri, uri.toFile())
                photos?.add(mediaFile)
                baseAdapter?.setList(photos)
                sharedViewModel.cleanDataImage()
            }
        })

    }

    private fun setAdapter() {
        baseAdapter = BaseAdapter(
            requireContext(), R.layout.row_item_post_thumnail_layout,
            photos
        ) { v, item, position ->
            val remove = v?.findViewById(R.id.btnRemove) as AppCompatImageView
            val imageView = v.findViewById(R.id.imageView) as AppCompatImageView

            remove.setSafeOnClickListener {
                baseAdapter?.removeAt(position)
            }

            loadImageLocal(imageView, item.file.toUri(), getProgressDrawable(imageView.context))

        }
        binding.recyclerView.adapter = baseAdapter

    }

    private fun sendPost() {

        val type = if (!photos.isNullOrEmpty()) {
            "IMAGE"
        } else {
            "TEXT"
        }
        viewModel.senRequestFeed(
            FeedPlainModel(
                null,
                binding.etContent.text.toString(),
                type,
                0,
                currentDay,
                UserManager.instance.user?.id,
                "DRAFT"
            )
        )
    }

    private fun clickFun() {
        binding.btnCancel.setSafeOnClickListener { findNavController().popBackStack() }

        init()

        binding.imgCamera.setSafeOnClickListener { openCamera() }

        binding.btnSendPost.setSafeOnClickListener {
            if (updateFeed){
                updateFeed()
                return@setSafeOnClickListener
            }


            if (!photos.isNullOrEmpty()){
                for (i in photos!!) {
                    val body = UploadRequestBody(i.file, "image")
                    mediaType.add(
                        MultipartBody.Part.createFormData(
                            "files",
                            i.file.name,
                            body
                        )
                    )
                }
                viewModel.sendFeedImageUpload(mediaType)
                return@setSafeOnClickListener
            }
            if (binding.etContent.text.toString().length < 6 ){
                requireContext().extToast("Lütfen gönderi yazısı girin")
                return@setSafeOnClickListener
            }

            sendPost()

        }

        binding.imgGallery.setSafeOnClickListener {
            val necessaryPermissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
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

   private fun updateFeed(){
       viewModel.senRequestUpdateFeed(updateFeedModel?.id.toString(),   FeedPlainModel(
                updateFeedModel?.id,
                binding.etContent.text.toString(),
                updateFeedModel?.postType,
                updateFeedModel?.likes,
                updateFeedModel?.postDate,
                updateFeedModel?.user?.id,
                "DRAFT"
            ))


    }


    private fun edtContentControl() {
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
                    bundle.putString(Constants.SELECTED_URI, hereUrl.toString())

                    if (imageFiles.size == 1) {
                        val model =
                            ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
                        model.setPostValue(binding.etContent.text.toString())
                        findNavController().navigate(
                            R.id.action_postFragment2_to_photoEditorFragment,
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

    private fun cleanForm(){
        binding.etContent.setText("")
        photos?.clear()
        baseAdapter?.setList(photos)
    }


}