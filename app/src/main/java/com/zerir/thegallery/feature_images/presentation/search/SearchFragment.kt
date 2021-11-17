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
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.zerir.thegallery.base.ui.LoadingDialog
import com.zerir.thegallery.base.ui.KeyboardUtils
import com.zerir.thegallery.base.ui.Notify

@AndroidEntryPoint
class SearchFragment : Fragment(), OnImageClickListener {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel by viewModels<SearchViewModel>()

    @Inject
    lateinit var adapter: ImageAdapter

    @Inject
    lateinit var loadingDialog: LoadingDialog

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

        adapter.registerListener(this)
        binding.imagesRv.setHasFixedSize(true)
        binding.imagesRv.adapter = adapter

        binding.searchEt.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search()
                true
            } else false
        }

        binding.searchIb.setOnClickListener {
            search()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            uiState?.let {
                binding.searchEt.setText(uiState.query)

                updateImagesList(uiState.images)

                val showLoader = uiState.loading && uiState.images.isNullOrEmpty()
                showLoader(showLoader)

                handleError(uiState.throwable)
            }
        }

        viewModel.isConnection.observe(viewLifecycleOwner) { isConnection ->
            updateConnectionStatus(isConnection)
        }
    }

    private fun search() {
        keyboardUtils.hideKeyboard(requireActivity())
        binding.searchEt.clearFocus()
        val query = binding.searchEt.text.toString()
        viewModel.searchImages(query)
    }

    private fun updateImagesList(images: List<Image>?) {
        if (images.isNullOrEmpty()) {
            binding.emptyTv.visibility = VISIBLE
        } else {
            binding.emptyTv.visibility = GONE
        }
        adapter.submitList(images)
    }

    private fun updateConnectionStatus(isConnection: Boolean?) {
        if(isConnection == true) binding.connectionStatus.visibility = GONE
        else binding.connectionStatus.visibility = VISIBLE
    }

    private fun showLoader(showLoader: Boolean) {
        if (showLoader) loadingDialog.show(requireActivity().supportFragmentManager, "search-tag")
        else if (loadingDialog.isAdded) loadingDialog.dismiss()
    }

    private fun handleError(throwable: Throwable?) {
        if (throwable == null) return

        viewModel.clearError()

        val message = getString(R.string.something_went_wrong)
        notify.showSnackBar(
            message = message,
            view = binding.root,
            time = Snackbar.LENGTH_SHORT,
        )
    }

    override fun onImageClicked(image: Image) {
        val query = binding.searchEt.text.toString()
        val action =
            SearchFragmentDirections.actionSearchFragmentToImageDetailsFragment(query, image.id)
        Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
            .navigate(action)
    }

    private fun getConnectivityManager() =
        requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun onResume() {
        super.onResume()
        getConnectivityManager()
            .registerNetworkCallback(viewModel.networkRequest, viewModel.networkCallback)
    }

    override fun onPause() {
        super.onPause()
        getConnectivityManager()
            .registerNetworkCallback(viewModel.networkRequest, viewModel.networkCallback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.unRegisterListener()
    }
}