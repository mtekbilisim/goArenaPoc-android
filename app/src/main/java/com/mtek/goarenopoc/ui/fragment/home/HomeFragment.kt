package com.mtek.goarenopoc.ui.fragment.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mtek.goarenopoc.R
import com.mtek.goarenopoc.base.BaseAdapter
import com.mtek.goarenopoc.base.BaseFragment
import com.mtek.goarenopoc.databinding.FragmentHomeBinding
import com.mtek.goarenopoc.ui.MainActivity
import com.mtek.goarenopoc.utils.applyDivider
import com.mtek.goarenopoc.utils.gone


class HomeFragment : BaseFragment<FragmentHomeBinding,HomeViewModel>(HomeViewModel::class) {

    override fun getViewBinding()= FragmentHomeBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireContext() as MainActivity).showBottomNav()
//        binding.imageView.setSafeOnClickListener {
//            findNavController().popBackStackAllInstances(R.id.splashFragment,true)
//            flag_error("Çalıştı")
//        }


        binding.recyclerStories.apply {
            adapter = BaseAdapter<String>(requireContext(),R.layout.row_item_feed_stories,
                arrayListOf("1","2","3","4","5","1","2","3","4","5")
            ) {v, item, position ->
                val view = v?.findViewById<View>(R.id.view)

                if (position != 0){
                    view?.gone()
                }

            }
        }

        binding.recyclerFeed.apply {
            adapter = BaseAdapter<String>(requireContext(),R.layout.row_item_feed_thumnail_layout,
                arrayListOf("1","2","3","4","5")
            ) {v, item, position ->
                val rc = v?.findViewById<RecyclerView>(R.id.recyclerViewThumnail)

                rc?.adapter = BaseAdapter<String>(requireContext(),R.layout.row_item_thumnail_layout,
                        arrayListOf("1","2","3","4","5")
                    ) {v, item, position ->

                    }
            }
        }.also {
            it.applyDivider()
        }
    }






}