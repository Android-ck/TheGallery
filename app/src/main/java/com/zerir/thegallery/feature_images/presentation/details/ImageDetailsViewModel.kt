package com.zerir.thegallery.feature_images.presentation.details

import androidx.lifecycle.*
import com.zerir.thegallery.base.network.Resource
import com.zerir.thegallery.feature_images.data.remote.response.RetrieveResponse
import com.zerir.thegallery.feature_images.domain.use_case.GetImageByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageDetailsViewModel @Inject constructor(
    private val getImageByIdUseCase: GetImageByIdUseCase,
    state: SavedStateHandle,
) : ViewModel() {

    private val _resource = MutableLiveData<Resource<RetrieveResponse>?>()
    val resource: LiveData<Resource<RetrieveResponse>?> get() = _resource

    init {
        val query = state.get<String>("imageQuery") ?: ""
        val id = state.get<String>("imageId") ?: ""
        getImageById(query = query, id = id)
    }

    private fun getImageById(query: String, id: String) = viewModelScope.launch {
        val q = query.lowercase().trim()
        getImageByIdUseCase(query = q, id = id).collect { resource ->
            _resource.value = resource
        }
    }

}