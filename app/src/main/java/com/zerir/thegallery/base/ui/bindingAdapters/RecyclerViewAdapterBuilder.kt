package com.zerir.thegallery.base.ui.bindingAdapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter(value = ["adapter", "isFixedSize"])
fun RecyclerView.setupAdapter(
    adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?,
    isFixedSize: Boolean? = null
) {
    this.adapter = adapter
    this.setHasFixedSize(isFixedSize ?: true)
}