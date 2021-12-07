package com.zerir.thegallery.feature_images.data.local.preference

interface TagPreference {

    fun saveLastSearchTag(tag: String)

    fun loadLastSearchTag(): String?

}