package com.zerir.thegallery.feature_images.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zerir.thegallery.feature_images.Constants
import com.zerir.thegallery.feature_images.domain.use_case.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val retrieveImagesUseCase: RetrieveImagesUseCase,
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState?>()
    val uiState: LiveData<UiState?> get() = _uiState

    private var retrieveImagesJob: Job? = null

    init {
        searchImages(Constants.DEFAULT_QUERY_SEARCH)
    }

    fun searchImages(query: String) {
        retrieveImagesJob?.cancel()
        retrieveImagesJob = retrieveImagesUseCase(query).onEach { resource ->
            _uiState.value = UiState(query = query, resource = resource)
        }.launchIn(viewModelScope)
    }

}