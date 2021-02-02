package com.mtek.goarenopoc.ui.adapter.homefeed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.mtek.goarenopoc.R
import com.mtek.goarenopoc.base.BaseAdapter
import com.mtek.goarenopoc.data.model.FeedModel
import com.mtek.goarenopoc.data.model.MediaModel
import com.mtek.goarenopoc.utils.getProgressDrawable
import com.mtek.goarenopoc.utils.loadImage
import com.mtek.goarenopoc.utils.loadImageCircle
import de.hdodenhof.circleimageview.CircleImageView


class HolderThumbnail(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.row_item_feed_thumnail_layout,
        parent,
        false
    )
) {

    val rc = itemView.findViewById<RecyclerView>(R.id.recyclerViewThumnail)
    val profilePhoto = itemView.findViewById(R.id.profileImage) as CircleImageView
    val userName = itemView.findViewById(R.id.userName) as AppCompatTextView
    val date = itemView.findViewById(R.id.date) as AppCompatTextView
    val txtText = itemView.findViewById(R.id.txtText) as AppCompatTextView
    val likeState = itemView.findViewById(R.id.likeState) as AppCompatTextView
    val commentState = itemView.findViewById(R.id.commentState) as AppCompatTextView

    fun bind(
        item: FeedModel,
        onItemClickListener: (FeedModel) -> Unit
    ) {
   loadImageCircle(
       (profilePhoto as CircleImageView),
       item.user?.avatar,
       getProgressDrawable(profilePhoto.context)
   )
        userName.text = item.user?.username
        date.text = item.postDate
        txtText.text = item.title
        likeState.text = "   ${item.likes.toString()}"
        commentState.text = "   123"
                rc?.adapter = BaseAdapter<MediaModel>(
                    itemView.context, R.layout.row_item_thumnail_layout,
                    item.medias
                ) { v, item, position ->
                    val imageView = v?.findViewById(R.id.imageView) as AppCompatImageView
                    val options: RequestOptions = RequestOptions().centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        Glide.with (itemView.context).load(
                        item.uri
                    ).apply(options).thumbnail(0.1f).into(imageView)
                   // loadImage(imageView, item.uri, getProgressDrawable(imageView.context))

                }
        itemView.setOnClickListener {
            onItemClickListener(item)
        }
    }
}