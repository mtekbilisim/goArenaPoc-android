package com.mtek.goarenopoc.ui.adapter.homefeed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.mtek.goarenopoc.R
import com.mtek.goarenopoc.data.model.Data
import com.mtek.goarenopoc.data.model.FeedModel
import com.mtek.goarenopoc.utils.getProgressDrawable
import com.mtek.goarenopoc.utils.loadImage
import com.mtek.goarenopoc.utils.loadImageCircle
import de.hdodenhof.circleimageview.CircleImageView

class HolderOnlyText(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.row_item_feed_only_text,
        parent,
        false
    )
) {
    val profilePhoto = itemView?.findViewById(R.id.profileImage) as CircleImageView
    val userName = itemView?.findViewById(R.id.userName) as AppCompatTextView
    val date = itemView?.findViewById(R.id.date) as AppCompatTextView
    val txtText = itemView?.findViewById(R.id.txtText) as AppCompatTextView
    val likeState = itemView?.findViewById(R.id.likeState) as AppCompatTextView
    val commentState = itemView?.findViewById(R.id.commentState) as AppCompatTextView

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
        itemView.setOnClickListener {
            onItemClickListener(item)
        }
    }
}