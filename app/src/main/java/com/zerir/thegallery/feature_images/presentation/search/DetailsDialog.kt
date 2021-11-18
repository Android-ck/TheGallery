package com.zerir.thegallery.feature_images.presentation.search

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.zerir.thegallery.R
import com.zerir.thegallery.base.ui.bindingAdapters.loadImage
import com.zerir.thegallery.databinding.ViewImageDetailsDialogBinding
import java.io.Serializable
import javax.inject.Inject

class DetailsDialog @Inject constructor() : DialogFragment() {

    private lateinit var binding: ViewImageDetailsDialogBinding

    private lateinit var imageUrl: String
    private lateinit var onAction: OnDetailsClicked

    private val args: DetailsDialogArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ViewImageDetailsDialogBinding.inflate(inflater, container, false)

        isCancelable = false

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageUrl = args.imageUrl
        onAction = args.onAction

        binding.imageIv.loadImage(
            imageUrl = imageUrl,
            null, null
        )

        binding.closeBt.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).popBackStack()
        }

        binding.detailsBt.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).popBackStack()
            onAction.onDetails()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window!!.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}

interface OnDetailsClicked : Serializable {
    fun onDetails()
}