package com.mtek.goarenopoc.ui.adapter.homefeed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mtek.goarenopoc.R
import com.mtek.goarenopoc.data.model.Data

class HolderOnlyText(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.row_item_feed_only_text,
        parent,
        false
    )
) {
    fun bind(
        newsModel: Data,
        onItemClickListener: (Data) -> Unit
    ) {


        itemView.setOnClickListener {
            onItemClickListener(newsModel)
        }
    }
}