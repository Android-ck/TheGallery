package com.zerir.thegallery.feature_images.domain.preference

interface TagPreference {

    fun saveLastSearchTag(tag: String)

    fun loadLastSearchTag(): String?

}