package com.mtek.goarenopoc.ui.adapter.homefeed


import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mtek.goarenopoc.data.model.Data
import com.mtek.goarenopoc.data.model.FeedModel
import com.mtek.goarenopoc.data.network.response.FeedResponseModel

class HomeFeedAdapter(
    private var items: ArrayList<FeedModel>,
    private val onItemClickListener: ( newsModel: FeedModel)->Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){

            LayoutType.Thumnail.id->{
                    HolderThumbnail(parent)
            }
            else ->{
                    HolderOnlyText(parent)
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            LayoutType.Thumnail.id -> {
                (holder as HolderThumbnail).bind(items[position],onItemClickListener)
            }

            else -> {
                (holder as HolderOnlyText).bind(items[position], onItemClickListener)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun setList(newlist: ArrayList<FeedModel>?) {
        if (newlist != null) {
            items = newlist
        }
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position].postType == "TEXT"){
            1
        }else if(items[position].postType == "VIDEO"){
            0
        }else{
            0
        }
    }

}

enum class LayoutType(val id: Int) {
    Thumnail(0), Text(1), Video(2)
}



