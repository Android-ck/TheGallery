package com.zerir.thegallery.feature_images.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zerir.thegallery.base.network.NetworkConnection
import com.zerir.thegallery.base.network.Resource
import com.zerir.thegallery.feature_images.domain.use_case.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val retrieveImagesUseCase: RetrieveImagesUseCase,
    private val getLastSearchTagUseCase: GetLastSearchTagUseCase,
    private val networkConnection: NetworkConnection
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState?>()
    val uiState: LiveData<UiState?> get() = _uiState

    private var retrieveImagesJob: Job? = null

    val isConnection: LiveData<Boolean> get() = networkConnection.connected

    val networkRequest get() = networkConnection.getNetworkRequest()
    val networkCallback get() = networkConnection.getNetworkCallBack()

    init {
        viewModelScope.launch {
            val query = getLastSearchTagUseCase()
            searchImages(query)
        }
    }

    fun searchImages(query: String) {
        retrieveImagesJob?.cancel()
        retrieveImagesJob = retrieveImagesUseCase(query).onEach { resource ->
            _uiState.value = UiState(
                query = query,
                images = resource.data ?: listOf(),
                loading = resource is Resource.Loading,
                throwable = resource.throwable,
            )
        }.launchIn(viewModelScope)
    }

    fun clearError() { _uiState.value?.throwable = null }

}