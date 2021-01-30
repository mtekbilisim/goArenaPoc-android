package com.mtek.goarenopoc.ui.adapter.homefeed


import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mtek.goarenopoc.data.model.Data

class HomeFeedAdapter(
    private var items: ArrayList<Data>,
    private val onItemClickListener: ( newsModel: Data)->Unit
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



    override fun getItemViewType(position: Int): Int {
        return position % 2 * 2
    }

}

enum class LayoutType(val id: Int) {
    Thumnail(0), Text(1), Video(2)
}



