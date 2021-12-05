package com.zerir.thegallery.feature_images.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.zerir.thegallery.R
import com.zerir.thegallery.databinding.FragmentSearchBinding
import com.zerir.thegallery.feature_images.domain.model.Image
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import android.content.Context
import android.net.ConnectivityManager
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.zerir.thegallery.base.ui.utils.KeyboardUtils
import com.zerir.thegallery.base.ui.utils.Notify

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel by viewModels<SearchViewModel>()

    @Inject
    lateinit var keyboardUtils: KeyboardUtils

    @Inject
    lateinit var notify: Notify

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.searchEt.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchImages()
                true
            } else false
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            uiState?.let {
                onSearch(uiState.isSearching)

                val showLoader = uiState.loading
                showLoader(showLoader)

                handleError(uiState.throwable)

                viewModel.clearUiState()
            }
        }

        viewModel.onImageClicked.observe(viewLifecycleOwner) { image ->
            image?.let {
                moveToDetailsDialog(image)

                viewModel.clearImageClicked()
            }
        }
    }

    private fun onSearch(isSearching: Boolean?) {
        if(isSearching == true) {
            keyboardUtils.hideKeyboard(requireActivity())
            binding.searchEt.clearFocus()
        }
    }

    private fun showLoader(showLoader: Boolean) {
        val navigator = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
        if (showLoader) {
            val action = SearchFragmentDirections.actionSearchFragmentToLoadingDialog()
            navigator.navigate(action)
        }
        else {
            val currentDestination = navigator.currentDestination?.id
            if(currentDestination == R.id.loadingDialog) {
                navigator.popBackStack()
            }
        }
    }

    private fun handleError(throwable: Throwable?) {
        if (throwable == null) return
        val cause = if(throwable.message.isNullOrBlank()) "" else "\n-> ${throwable.message}"
        val message = "${getString(R.string.something_went_wrong)}$cause"
        notify.showSnackBar(
            message = message,
            view = binding.root,
            time = Snackbar.LENGTH_INDEFINITE,
            actionName = getString(R.string.dismiss),
        ) {}
    }

    private fun moveToDetailsDialog(image: Image) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailsDialog(
            image.largeImageURL, object: OnDetailsClicked {
                override fun onDetails() {
                    moveToImageDetails(image)
                }
            }
        )
        Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
            .navigate(action)
    }

    private fun moveToImageDetails(image: Image) {
        if(isAdded) {
            val action =
                SearchFragmentDirections.actionSearchFragmentToImageDetailsFragment(image.id)
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                .navigate(action)
        }
    }

    private fun getConnectivityManager() =
        requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun onResume() {
        super.onResume()
        getConnectivityManager().registerNetworkCallback(viewModel.networkRequest, viewModel.networkCallback)
    }

    override fun onPause() {
        super.onPause()
        try {
            getConnectivityManager().unregisterNetworkCallback(viewModel.networkCallback)
        } catch (e: Exception){ }

        // close dialog when rotate
        if(Navigation.findNavController(binding.root).currentDestination?.id == R.id.detailsDialog) {
            Navigation.findNavController(binding.root).popBackStack()
        }
    }
}