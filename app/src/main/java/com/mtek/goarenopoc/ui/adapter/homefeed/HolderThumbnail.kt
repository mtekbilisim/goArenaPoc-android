package com.mtek.goarenopoc.ui.adapter.homefeed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mtek.goarenopoc.R
import com.mtek.goarenopoc.base.BaseAdapter
import com.mtek.goarenopoc.data.model.Data

class HolderThumbnail(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.row_item_feed_thumnail_layout,
        parent,
        false
    )
) {
    fun bind(
        newsModel: Data,
        onItemClickListener: (Data)->Unit
    ) {

        val rc = itemView?.findViewById<RecyclerView>(R.id.recyclerViewThumnail)

                rc?.adapter = BaseAdapter<String>(itemView.context,R.layout.row_item_thumnail_layout,
                        arrayListOf("1","2","3","4","5")
                    ) {v, item, position ->

                    }
        itemView.setOnClickListener {
            onItemClickListener(newsModel)
        }
    }
}