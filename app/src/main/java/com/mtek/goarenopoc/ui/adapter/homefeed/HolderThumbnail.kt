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
import com.mtek.goarenopoc.ui.MainActivity
import com.mtek.goarenopoc.ui.fragment.bottom.FeedEditBottomDialog
import com.mtek.goarenopoc.utils.*
import com.mtek.goarenopoc.utils.manager.UserManager
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
    val btnEdit = itemView?.findViewById(R.id.btnEdit) as AppCompatImageView

    fun bind(
        item: FeedModel,
        onItemClickListener: (FeedModel) -> Unit
    ) {
   loadImageCircle(
       (profilePhoto as CircleImageView),
       item.user?.avatar,
       getProgressDrawable(profilePhoto.context)
   )
        btnEdit.setSafeOnClickListener {
            FeedEditBottomDialog(item) {
                item.isDeleteFunWork = true
                onItemClickListener.invoke(item)

            }.show((itemView.context as MainActivity).supportFragmentManager,"Detail")
        }
        userName.text = item.user?.username
        date.text =  DateManager.formatDate(item.postDate,"MMM dd.mm.yyyy")
        txtText.text = item.title
        likeState.text = "   ${item.likes.toString()}"
        commentState.text = "   123"

        if (item.user?.id != UserManager.instance.user?.id){
            btnEdit.gone()
        }else{
             btnEdit.visible()
        }

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