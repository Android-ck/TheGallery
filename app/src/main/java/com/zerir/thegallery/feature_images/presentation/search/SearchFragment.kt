package com.zerir.thegallery.feature_images.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.zerir.thegallery.R
import com.zerir.thegallery.base.network.Resource
import com.zerir.thegallery.databinding.FragmentSearchBinding
import com.zerir.thegallery.feature_images.domain.model.Image
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels

@AndroidEntryPoint
class SearchFragment : Fragment(), OnImageClickListener {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel by viewModels<SearchViewModel>()

    @Inject
    lateinit var adapter: ImageAdapter

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
                when (val resource = uiState.resource) {
                    is Resource.Success -> {
                        val images = resource.data?.hits ?: listOf()
                        adapter.submitList(images)
                    }
                    is Resource.Failure -> {
                        //TODO: handle errors
                    }
                    is Resource.Loading -> { }
                }
            }
        }
    }

    private fun search() {
        hideKeyboard()
        binding.searchEt.clearFocus()
        val query = binding.searchEt.text.toString()
        viewModel.searchImages(query)
    }

    override fun onImageClicked(image: Image) {
        val query = binding.searchEt.text.toString()
        val action =
            SearchFragmentDirections.actionSearchFragmentToImageDetailsFragment(query, image.id)
        Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
            .navigate(action)
    }

    private fun hideKeyboard() {
        val imm =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = requireActivity().currentFocus
        if (view == null) {
            view = View(requireActivity())
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.unRegisterListener()
    }
}