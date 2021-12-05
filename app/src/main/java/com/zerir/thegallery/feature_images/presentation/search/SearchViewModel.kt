package com.zerir.thegallery.feature_images.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zerir.thegallery.base.network.NetworkConnection
import com.zerir.thegallery.base.network.Resource
import com.zerir.thegallery.feature_images.domain.model.Image
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
    networkConnection: NetworkConnection,
    val imageAdapter: ImageAdapter,
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState?>()
    val uiState: LiveData<UiState?> get() = _uiState

    private var retrieveImagesJob: Job? = null

    val isConnection: LiveData<Boolean> = networkConnection.connectionState

    private val _isListIsEmpty = MutableLiveData<Boolean?>()
    val isListIsEmpty: LiveData<Boolean?> get() = _isListIsEmpty

    lateinit var query: String

    private val _onImageClicked = MutableLiveData<Image?>()
    val onImageClicked: LiveData<Image?> get() = _onImageClicked

    init {
        viewModelScope.launch {
            query = getLastSearchTagUseCase() ?: UiState.DEFAULT_QUERY_SEARCH
            searchImages()
        }

        imageAdapter.registerListener(object: OnImageClickListener {
            override fun onImageClicked(image: Image) {
                _onImageClicked.value = image
            }
        })
    }

    fun searchImages() {
        _uiState.value = UiState(isSearching = true, false, null)
        retrieveImagesJob?.cancel()
        retrieveImagesJob = retrieveImagesUseCase(query).onEach { resource ->
            _uiState.value = UiState(
                isSearching = false,
                loading = resource is Resource.Loading && resource.data.isNullOrEmpty(),
                throwable = resource.throwable,
            )
            //submit list
            imageAdapter.submitList(resource.data)
            //update ui with list size
            _isListIsEmpty.value = resource.data.isNullOrEmpty()
        }.launchIn(viewModelScope)
    }

    fun clearUiState() { _uiState.value = null }

    fun clearImageClicked() { _onImageClicked.value = null }

    override fun onCleared() {
        super.onCleared()
        imageAdapter.unRegisterListener()
    }

}