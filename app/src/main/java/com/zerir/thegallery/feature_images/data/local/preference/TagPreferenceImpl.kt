package com.zerir.thegallery.feature_images.data.local.preference

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TagPreferenceImpl @Inject constructor(
    @ApplicationContext context: Context
) : TagPreference {

    private val applicationContext = context.applicationContext
    private val sharedPreferences = applicationContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    override fun saveLastSearchTag(tag: String) {
        sharedPreferences.edit().apply {
            putString(TAG_KEY, tag)
            apply()
        }
    }

    override fun loadLastSearchTag(): String? {
        return sharedPreferences.getString(TAG_KEY, null)
    }

    companion object {
        private const val PREFERENCE_NAME = "tag-shared-preference"
        private const val TAG_KEY = "tag-key"
    }
}