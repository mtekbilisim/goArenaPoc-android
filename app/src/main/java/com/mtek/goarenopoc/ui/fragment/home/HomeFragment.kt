package com.mtek.goarenopoc.ui.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mtek.goarenopoc.R
import com.mtek.goarenopoc.base.BaseAdapter
import com.mtek.goarenopoc.base.BaseFragment
import com.mtek.goarenopoc.data.model.Data
import com.mtek.goarenopoc.data.model.FeedModel
import com.mtek.goarenopoc.data.network.response.FeedResponseModel
import com.mtek.goarenopoc.databinding.FragmentHomeBinding
import com.mtek.goarenopoc.ui.MainActivity
import com.mtek.goarenopoc.ui.adapter.homefeed.HomeFeedAdapter
import com.mtek.goarenopoc.ui.adapter.homefeed.LayoutType
import com.mtek.goarenopoc.utils.applyDivider
import com.mtek.goarenopoc.utils.flag_error
import com.mtek.goarenopoc.utils.gone
import com.mtek.goarenopoc.utils.setSafeOnClickListener


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(HomeViewModel::class) {

    override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)

    var responseFeedList: ArrayList<FeedModel>? = arrayListOf()
    private var homeAdapter: HomeFeedAdapter? = null

    private val observerFeed: Observer<FeedResponseModel> = Observer {
        if (it != null) {
            responseFeedList = it.data as ArrayList<FeedModel>?
           homeAdapter?.setList(responseFeedList)
        }
        flag_error("${responseFeedList.toString()}")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireContext() as MainActivity).showBottomNav()
        init()
        storyAdapter()
        setAdapter()
        clickFun()
    }

    private fun init() {
        viewModel {
            feedResponse.observe(viewLifecycleOwner, observerFeed)

        }
        viewModel.senRequestVFeed()
    }

    private fun clickFun() {
        binding.toolbar.btnBack.setSafeOnClickListener {


        }

        binding.toolbar.btnAdd.setSafeOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_postFragment2)
        }

    }

    private fun storyAdapter() {
        val dataList = ArrayList<Data>()
        dataList.add(Data(LayoutType.Thumnail.id, "1. Hi! I am in View 1"))
        dataList.add(Data(LayoutType.Thumnail.id, "2. Hi! I am in View 2"))
        dataList.add(Data(LayoutType.Text.id, "3. Hi! I am in View 3"))
        dataList.add(Data(LayoutType.Thumnail.id, "4. Hi! I am in View 4"))
        dataList.add(Data(LayoutType.Thumnail.id, "5. Hi! I am in View 5"))
        dataList.add(Data(LayoutType.Thumnail.id, "6. Hi! I am in View 6"))
        dataList.add(Data(LayoutType.Thumnail.id, "7. Hi! I am in View 7"))
        dataList.add(Data(LayoutType.Text.id, "8. Hi! I am in View 8"))
        dataList.add(Data(LayoutType.Thumnail.id, "9. Hi! I am in View 9"))
        dataList.add(Data(LayoutType.Thumnail.id, "10. Hi! I am in View 10"))
        dataList.add(Data(LayoutType.Text.id, "11. Hi! I am in View 11"))
        dataList.add(Data(LayoutType.Thumnail.id, "12. Hi! I am in View 12"))



        binding.recyclerStories.apply {
            adapter = BaseAdapter<String>(
                requireContext(), R.layout.row_item_feed_stories,
                arrayListOf("1", "2", "3", "4", "5", "1", "2", "3", "4", "5")
            ) { v, item, position ->
                val view = v?.findViewById<View>(R.id.view)

                if (position != 0) {
                    view?.gone()
                }

            }
        }

    }

    private fun setAdapter() {
        homeAdapter = HomeFeedAdapter(responseFeedList as ArrayList<FeedModel>) {}
        binding.recyclerFeed.apply {
            adapter = homeAdapter

        }.also {
            it.applyDivider()
        }
    }


}