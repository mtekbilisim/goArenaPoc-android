package com.mtek.goarenopoc.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mtek.goarenopoc.R
import com.mtek.goarenopoc.base.BaseFragment
import com.mtek.goarenopoc.databinding.FragmentPhotoEditorBinding
import com.mtek.goarenopoc.module.photofilter.core.*
import com.mtek.goarenopoc.module.photofilter.filter.FilterListener
import com.mtek.goarenopoc.module.photofilter.filter.FilterViewAdapter
import com.mtek.goarenopoc.module.photofilter.tools.EditingToolsAdapter
import com.mtek.goarenopoc.module.photofilter.tools.ToolType
import com.mtek.goarenopoc.ui.MainActivity
import com.mtek.goarenopoc.ui.fragment.home.HomeViewModel
import com.mtek.goarenopoc.utils.*
import ja.burhanrashid52.photoeditor.*
import ja.burhanrashid52.photoeditor.PhotoEditor.OnSaveListener
import java.io.File
import java.io.IOException


class PhotoEditorFragment :
    BaseFragment<FragmentPhotoEditorBinding, HomeViewModel>(HomeViewModel::class),
    OnPhotoEditorListener,
    PropertiesBSFragment.Properties,
    EmojiBSFragment.EmojiListener,
    StickerBSFragment.StickerListener, EditingToolsAdapter.OnItemSelected, FilterListener {


    override fun getViewBinding() = FragmentPhotoEditorBinding.inflate(layoutInflater)

    private var mWonderFont: Typeface? = null
    private val TAG: String =
        "Deneme"
    val FILE_PROVIDER_AUTHORITY = "com.mtek.goarenopoc.fileprovider"

    lateinit var mPhotoEditor: PhotoEditor
    private var mPropertiesBSFragment: PropertiesBSFragment? = null
    private var mEmojiBSFragment: EmojiBSFragment? = null
    private var mStickerBSFragment: StickerBSFragment? = null
    private var mTxtCurrentTool: TextView? = null
    private var mRootView: ConstraintLayout? = null
    private val mConstraintSet = ConstraintSet()
    private var mIsFilterVisible = false
    private var toBackPress = false;

    @VisibleForTesting
    var mSaveImageUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photoControl()
        (requireActivity() as MainActivity).hideBottomNav()
        init()
    }

    private fun photoControl() {
        val uri = arguments?.getString(Constants.SELECTED_URI)
        if (uri != null && uri != "") {
            mSaveImageUri = Uri.parse(uri)
            binding.photoEditorView.source.setImageURI(mSaveImageUri)
            binding.rootView.visible()
        } else {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(
                cameraIntent,
                Constants.CAMERA_REQUEST
            )
        }
    }

    private fun init() {

        handleIntentImage(binding.photoEditorView.source)

        mPropertiesBSFragment = PropertiesBSFragment()
        mEmojiBSFragment = EmojiBSFragment()
        mStickerBSFragment = StickerBSFragment()
        mStickerBSFragment?.setStickerListener(this)
        mEmojiBSFragment?.setEmojiListener(this)
        mPropertiesBSFragment?.setPropertiesChangeListener(this)

        val llmTools = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvConstraintTools.layoutManager = llmTools
        binding.rvConstraintTools.adapter = EditingToolsAdapter(this)

        val llmFilters = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rvFilterView.layoutManager = llmFilters
        binding.rvFilterView.adapter = FilterViewAdapter(this)

        mPhotoEditor = PhotoEditor.Builder(requireContext(), binding.photoEditorView)
            .setPinchTextScalable(true)
            .build()
        mPhotoEditor.setOnPhotoEditorListener(this)
        clickFun()
    }

    private fun handleIntentImage(source: ImageView) {
        val intent = Intent()
        val intentType = intent.type
        if (intentType != null && intentType.startsWith("image/")) {
            val imageUri = intent.data
            if (imageUri != null) {
                source.setImageURI(imageUri)
            }
        }
    }


    override fun onEditTextChangeListener(rootView: View?, text: String?, colorCode: Int) {
        val textEditorDialogFragment: TextEditorDialogFragment =
            TextEditorDialogFragment.show((requireActivity() as MainActivity), text!!, colorCode)
        textEditorDialogFragment.setOnTextEditorListener { inputText, colorCode ->
            val styleBuilder = TextStyleBuilder()
            styleBuilder.withTextColor(colorCode)
            mPhotoEditor.editText(rootView!!, inputText, styleBuilder)
            mTxtCurrentTool?.setText(R.string.label_text)
        }
    }

    override fun onAddViewListener(viewType: ViewType, numberOfAddedViews: Int) {
        Log.d(
            TAG,
            "onAddViewListener() called with: viewType = [$viewType], numberOfAddedViews = [$numberOfAddedViews]"
        )
    }

    override fun onRemoveViewListener(viewType: ViewType, numberOfAddedViews: Int) {
        Log.d(
            TAG,
            "onRemoveViewListener() called with: viewType = [$viewType], numberOfAddedViews = [$numberOfAddedViews]"
        )
    }

    override fun onStartViewChangeListener(viewType: ViewType) {
        Log.d(
            TAG,
            "onStartViewChangeListener() called with: viewType = [$viewType]"
        )
    }

    override fun onStopViewChangeListener(viewType: ViewType) {
        Log.d(
            TAG,
            "onStopViewChangeListener() called with: viewType = [$viewType]"
        )
    }

    private fun clickFun() {

        binding.imgSave.setSafeOnClickListener {
            saveImage()
        }

        binding.imgClose.setSafeOnClickListener {
            if (mIsFilterVisible) {
                showFilter(false)
                mTxtCurrentTool?.setText(R.string.app_name)
            } else if (!mPhotoEditor.isCacheEmpty) {
                showSaveDialog()
            } else {
                (requireActivity() as MainActivity).onBackPressed()
            }
        }

    }

//    private fun shareImage() {
//        if (mSaveImageUri == null) {
//            requireActivity().extToast(getString(R.string.msg_save_image_to_share))
//            return
//        }
//        val intent = Intent(Intent.ACTION_SEND)
//        intent.type = "image/*"
//        intent.putExtra(Intent.EXTRA_STREAM, buildFileProviderUri(mSaveImageUri!!))
//        startActivity(Intent.createChooser(intent, getString(R.string.msg_share_image)))
//    }
//
//    private fun buildFileProviderUri(uri: Uri): Uri? {
//        return FileProvider.getUriForFile(
//            requireContext(),
//            FILE_PROVIDER_AUTHORITY,
//            File(uri.path)
//        )
//    }


    @SuppressLint("MissingPermission")
    private fun saveImage() {
        if (requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            showLoading("Saving...")
            val file = File(
                Environment.getExternalStorageDirectory()
                    .toString() + File.separator + ""
                        + System.currentTimeMillis() + ".png"
            )
            try {
                file.createNewFile()
                val saveSettings = SaveSettings.Builder()
                    .setClearViewsEnabled(true)
                    .setTransparencyEnabled(true)
                    .build()
                mPhotoEditor.saveAsFile(file.absolutePath, saveSettings, object : OnSaveListener {
                    override fun onSuccess(imagePath: String) {
                        hideLoading()
                        requireContext().extToast("Image Saved Successfully")
                        mSaveImageUri = Uri.fromFile(File(imagePath))
                        binding.photoEditorView.source.setImageURI(mSaveImageUri)
                        val bundle = Bundle()
                        bundle.putString(Constants.SELECTED_URI, mSaveImageUri.toString())
                      //  Constants.filterUriStr = mSaveImageUri.toString()
                       val model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
                        model.setFilteredImage(mSaveImageUri.toString())
                        findNavController().popBackStack()
                    }

                    override fun onFailure(exception: Exception) {
                        hideLoading()
                        requireContext().extToast("Failed to save Image")
                    }
                })
            } catch (e: IOException) {
                e.printStackTrace()
                hideLoading()
                requireContext().extToast((e.message)!!)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {

            Constants.CAMERA_REQUEST -> {
                mPhotoEditor.clearAllViews()
                if (data != null) {
                    mSaveImageUri = data.data
                    binding.rootView.visible()
                    val photo = data.extras!!["data"] as Bitmap?
                    binding.photoEditorView.source.setImageBitmap(photo)
                } else {
                    (requireActivity() as MainActivity).onBackPressed()
                }

            }
            Constants.PICK_REQUEST -> try {
                mPhotoEditor.clearAllViews()
                val uri = data?.data
                if (uri != null) {
                    mSaveImageUri = data.data
                    binding.rootView.visible()
                    val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
                    binding.photoEditorView.source?.setImageBitmap(bitmap)
                } else {
                    (requireActivity() as MainActivity).onBackPressed()
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    }


    override fun onColorChanged(colorCode: Int) {
        mPhotoEditor.brushColor = colorCode
        mTxtCurrentTool?.setText(R.string.label_brush)
    }

    override fun onOpacityChanged(opacity: Int) {
        mPhotoEditor.setOpacity(opacity)
        mTxtCurrentTool?.setText(R.string.label_brush)
    }

    override fun onBrushSizeChanged(brushSize: Int) {
        mPhotoEditor.brushSize = brushSize.toFloat()
        mTxtCurrentTool?.setText(R.string.label_brush)
    }

    override fun onEmojiClick(emojiUnicode: String?) {
        mPhotoEditor.addEmoji(emojiUnicode)
        mTxtCurrentTool?.setText(R.string.label_emoji)
    }

    override fun onStickerClick(bitmap: Bitmap?) {
        mPhotoEditor.addImage(bitmap)
        mTxtCurrentTool?.setText(R.string.label_sticker)
    }

    override fun isPermissionGranted(isGranted: Boolean, permission: String?) {
        if (isGranted) {
            saveImage()
        }
    }

    private fun showSaveDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(getString(R.string.msg_save_image))
        builder.setPositiveButton(
            "Kaydet"
        ) { dialog, which -> saveImage() }
        builder.setNegativeButton(
            "İptal"
        ) { dialog, which -> dialog.dismiss() }
        builder.setNeutralButton(
            "Geri Dön"
        ) { dialog, which -> (requireContext() as MainActivity).onBackPressed() }
        builder.create().show()
    }

    override fun onFilterSelected(photoFilter: PhotoFilter?) {
        mPhotoEditor.setFilterEffect(photoFilter)
    }

    override fun onToolSelected(toolType: ToolType?) {
        when (toolType) {
            ToolType.BRUSH -> {
                mPhotoEditor.setBrushDrawingMode(true)
                mTxtCurrentTool?.setText(R.string.label_brush)
                showBottomSheetDialogFragment(mPropertiesBSFragment)
            }
            ToolType.TEXT -> {
                val textEditorDialogFragment: TextEditorDialogFragment =
                    TextEditorDialogFragment.show((requireActivity() as MainActivity))
                textEditorDialogFragment.setOnTextEditorListener { inputText, colorCode ->
                    val styleBuilder = TextStyleBuilder()
                    styleBuilder.withTextColor(colorCode)
                    mPhotoEditor.addText(inputText, styleBuilder)
                    mTxtCurrentTool?.setText(R.string.label_text)
                }
            }
            ToolType.ERASER -> {
                mPhotoEditor.brushEraser()
                mTxtCurrentTool?.setText(R.string.label_adjust)
            }
            ToolType.FILTER -> {
                mTxtCurrentTool?.setText(R.string.label_filter)
                showFilter(true)
            }
            ToolType.EMOJI -> showBottomSheetDialogFragment(mEmojiBSFragment)
            ToolType.STICKER -> showBottomSheetDialogFragment(mStickerBSFragment)
            ToolType.CROP -> {
                val bundle = Bundle()
                bundle.putString("SelectedImageUri", mSaveImageUri.toString())
                findNavController().navigate(
                    R.id.action_photoEditorFragment2_to_cropFragment,
                    bundle
                )

            }
        }
    }

    private fun showBottomSheetDialogFragment(fragment: BottomSheetDialogFragment?) {
        if (fragment == null || fragment.isAdded) {
            return
        }
        fragment.show(childFragmentManager, fragment.tag)
    }


    fun showFilter(isVisible: Boolean) {
        mIsFilterVisible = isVisible
        mConstraintSet.clone(binding.rootView)
        if (isVisible) {
            toBackPress = false
            mConstraintSet.clear(binding.rvFilterView.id, ConstraintSet.START)
            mConstraintSet.connect(
                binding.rvFilterView.id, ConstraintSet.START,
                ConstraintSet.PARENT_ID, ConstraintSet.START
            )
            mConstraintSet.connect(
                binding.rvFilterView.id, ConstraintSet.END,
                ConstraintSet.PARENT_ID, ConstraintSet.END
            )
        } else {

            mConstraintSet.connect(
                binding.rvFilterView.id, ConstraintSet.START,
                ConstraintSet.PARENT_ID, ConstraintSet.END
            )
            mConstraintSet.clear(binding.rvFilterView.id, ConstraintSet.END)
            if (!toBackPress) {
                toBackPress = true
            }
        }
        val changeBounds = ChangeBounds()
        changeBounds.duration = 350
        changeBounds.interpolator = AnticipateOvershootInterpolator(1.0f)
        TransitionManager.beginDelayedTransition(binding.rootView, changeBounds)
        mConstraintSet.applyTo(binding.rootView)
    }

//    fun onBackPressed() {
//        if (mIsFilterVisible) {
//            showFilter(false)
//            mTxtCurrentTool!!.setText(R.string.app_name)
//        } else if (!mPhotoEditor!!.isCacheEmpty) {
//            showSaveDialog()
//        } else {
//            super.onBackPressed()
//        }
//    }


}