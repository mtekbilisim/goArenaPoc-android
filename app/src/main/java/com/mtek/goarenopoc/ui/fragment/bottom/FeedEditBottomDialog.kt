package com.mtek.goarenopoc.ui.fragment.bottom

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mtek.goarenopoc.R
import com.mtek.goarenopoc.data.model.FeedModel
import com.mtek.goarenopoc.databinding.FragmentFeedEditLayoutBinding
import com.mtek.goarenopoc.ui.fragment.SharedViewModel
import com.mtek.goarenopoc.ui.fragment.home.HomeFragment
import com.mtek.goarenopoc.utils.setSafeOnClickListener

class FeedEditBottomDialog(var feedModel: FeedModel,var onItemClickListener : ()->Unit) : BottomSheetDialogFragment() {

    private var _binding: FragmentFeedEditLayoutBinding? = null
    private val binding get() = _binding!!
    var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    lateinit var sharedViewModel: SharedViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(context, R.layout.fragment_feed_edit_layout, null)
        _binding = FragmentFeedEditLayoutBinding.bind(view)
        bottomSheet.setContentView(view)
        bottomSheetBehavior = BottomSheetBehavior.from((view.parent) as View)
        dialog?.setOnShowListener {
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
        }
        bottomSheetBehavior?.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO
        binding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels) / 5)
        clickFun()

        return bottomSheet
    }

    private fun clickFun() {
        binding.txtModify.setSafeOnClickListener {
            sharedViewModel.setUpdateFeedModel(feedModel)
            findNavController().navigate(R.id.postFragment2)
            bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
        }

        binding.txtDelete.setSafeOnClickListener {
            onItemClickListener.invoke()
            bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    override fun onStart() {
        super.onStart()
        bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
    }

}