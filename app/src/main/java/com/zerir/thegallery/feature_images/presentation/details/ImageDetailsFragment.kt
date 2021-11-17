package com.zerir.thegallery.feature_images.presentation.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.zerir.thegallery.R
import com.zerir.thegallery.databinding.FragmentImageDetailsBinding
import com.zerir.thegallery.feature_images.domain.model.Image
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageDetailsFragment : Fragment() {

    private lateinit var binding: FragmentImageDetailsBinding
    private val viewModel by viewModels<ImageDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.image.observe(viewLifecycleOwner) { image ->
            image?.let { updateUi(image) }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUi(image: Image) {
        binding.nameTv.text = image.user
        binding.tagsTv.text = image.tags
        binding.likesTv.text = "${getString(R.string.likes)}\n${image.likes}"
        binding.commentsTv.text = "${getString(R.string.comments)}\n${image.likes}"
        binding.downloadTv.text = "${getString(R.string.downloads)}\n${image.likes}"

        Glide.with(requireActivity().applicationContext)
            .load(image.largeImageURL)
            .placeholder(ActivityCompat.getDrawable(requireActivity(), R.drawable.ic_download_large))
            .error(ActivityCompat.getDrawable(requireActivity(), R.drawable.ic_download_failed_large))
            .into(binding.imageIv)
    }

}