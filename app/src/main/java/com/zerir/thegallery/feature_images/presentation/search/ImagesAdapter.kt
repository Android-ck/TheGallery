package com.zerir.thegallery.feature_images.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zerir.thegallery.databinding.RowImageItemBinding
import com.zerir.thegallery.feature_images.domain.model.Image
import javax.inject.Inject

class ImageAdapter @Inject constructor() : ListAdapter<Image, RecyclerView.ViewHolder>(ImageDiffUtils()) {

    private var onImageClickListener: OnImageClickListener? = null

    fun registerListener(onImageClickListener: OnImageClickListener) {
        this.onImageClickListener = onImageClickListener
    }

    fun unRegisterListener() { this.onImageClickListener = null }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val item = getItem(position)
                holder.bind(item, onImageClickListener)
            }
        }
    }

    class ViewHolder(private val binding: RowImageItemBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup) : ViewHolder {
                val context = parent.context
                val layoutInflater = LayoutInflater.from(context)
                val binding = RowImageItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(image: Image, onImageClickListener: OnImageClickListener?) {
            binding.nameTv.text = image.user
            binding.tagsIv.text = image.tags

            image.previewURL.isNotBlank().let {
                Glide.with(binding.root.context.applicationContext)
                    .load(image.previewURL)
                    .dontTransform()
                    .into(binding.thumbnailIv)
            }

            binding.root.setOnClickListener { onImageClickListener?.onImageClicked(image) }
        }
    }

}

class ImageDiffUtils @Inject constructor(): DiffUtil.ItemCallback<Image>() {

    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem.user == newItem.user &&
                oldItem.tags == newItem.tags &&
                oldItem.previewURL == newItem.previewURL
    }

}

interface OnImageClickListener {
    fun onImageClicked(image: Image)
}