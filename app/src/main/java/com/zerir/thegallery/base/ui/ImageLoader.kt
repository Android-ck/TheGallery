package com.zerir.thegallery.base.ui

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.zerir.thegallery.R

fun ImageView.loadImage(
    url: String?,
    @DrawableRes loading: Int = R.drawable.ic_download_large,
    @DrawableRes error: Int = R.drawable.ic_download_failed_large,
) {
    Glide.with(context)
        .load(url)
        .placeholder(ActivityCompat.getDrawable(context, loading))
        .error(ActivityCompat.getDrawable(context, error))
        .into(this)
}