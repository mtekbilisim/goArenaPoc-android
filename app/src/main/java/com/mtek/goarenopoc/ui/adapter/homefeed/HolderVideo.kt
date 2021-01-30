package com.mtek.goarenopoc.ui.adapter.homefeed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mtek.goarenopoc.R

//class HolderVideo(parent: ViewGroup) : RecyclerView.ViewHolder(
//    LayoutInflater.from(parent.context).inflate(
//        R.layout.adapter_item_big_news,
//        parent,
//        false
//    )
//) {
//    fun bind(
//        newsModel: BaseNewsModel,
//        onItemClickListener: (BaseNewsModel) -> Unit
//    ) {
//        Glide.with(itemView.context)
//            .load((newsModel as NewsModel).newsImageUrl)
//            .centerCrop()
//            .into(itemView.imgNews)
//
//        itemView.txtNewsTitle.text = newsModel.newsTitle
//
//        itemView.setOnClickListener {
//            onItemClickListener(newsModel)
//        }
//    }
//}