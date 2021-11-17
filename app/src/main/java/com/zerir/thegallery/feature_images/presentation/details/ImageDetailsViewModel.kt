package com.zerir.thegallery.feature_images.presentation.details

import androidx.lifecycle.*
import com.zerir.thegallery.feature_images.domain.model.Image
import com.zerir.thegallery.feature_images.domain.use_case.GetImageByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageDetailsViewModel @Inject constructor(
    private val getImageByIdUseCase: GetImageByIdUseCase,
    state: SavedStateHandle,
) : ViewModel() {

    private val _image = MutableLiveData<Image?>()
    val image: LiveData<Image?> get() = _image

    init {
        val id = state.get<String>("imageId") ?: ""
        getImageById(id = id)
    }

    private fun getImageById(id: String) = viewModelScope.launch {
        _image.value = getImageByIdUseCase(id = id)
    }

}