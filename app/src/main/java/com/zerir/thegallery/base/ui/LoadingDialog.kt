package com.zerir.thegallery.base.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.zerir.thegallery.R
import javax.inject.Inject

class LoadingDialog @Inject constructor() : DialogFragment(R.layout.view_loading) {

    override fun onStart() {
        super.onStart()
        dialog?.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

}