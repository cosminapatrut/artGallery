package com.orange.artgallery.gallery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.orange.artgallery.R
import com.orange.domain.model.GalleryItem
import kotlinx.android.synthetic.main.gallery_item.view.*

class GalleryAdapter(
    private val context: Context,
    private val items: List<GalleryItem>,
    private val itemClicked: (Long) -> Unit
) : RecyclerView.Adapter<GalleryImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryImageViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val galleryItemView = layoutInflater.inflate(
            R.layout.gallery_item,
            parent,
            false
        )
        return GalleryImageViewHolder(galleryItemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: GalleryImageViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.gallery_item_title.text = item.title
        Glide.with(context)
            .load(item.image)
            .placeholder(R.drawable.ic_placeholder)
            .into(holder.itemView.gallery_item_image)
        holder.itemView.setOnClickListener {
            item.objectID?.let {
                itemClicked.invoke(it)
            }
        }
    }

}

class GalleryImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)