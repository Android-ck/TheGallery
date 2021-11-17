package com.zerir.thegallery.base.ui

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import javax.inject.Inject

class KeyboardUtils @Inject constructor() {
    private var imm: InputMethodManager? = null
    private var view: View? = null

    fun hideKeyboard(activity: Activity) {
        imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm?.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}