package com.zerir.thegallery.feature_images.presentation.search

data class UiState(
    val isSearching: Boolean,
    val loading: Boolean,
    val throwable: Throwable?,
) {

    companion object {
        const val DEFAULT_QUERY_SEARCH = "fruits"
    }

}
