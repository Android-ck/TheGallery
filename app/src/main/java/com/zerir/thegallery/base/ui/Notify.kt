package com.zerir.thegallery.base.ui

import android.view.View
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class Notify @Inject constructor() {

    private var snackBar: Snackbar? = null

    fun showSnackBar(message: String, view: View, time: Int = Snackbar.LENGTH_LONG) {
        snackBar?.dismiss()

        //to make sure time is correct
        val t: Int = if(time != Snackbar.LENGTH_LONG || time != Snackbar.LENGTH_SHORT) Snackbar.LENGTH_LONG
        else time

        snackBar = Snackbar.make(view, message, t)
        snackBar?.show()
    }

    fun showSnackBar(message: String, view: View, time: Int = Snackbar.LENGTH_INDEFINITE,
                     actionName: String, action: () -> Unit) {
        snackBar?.dismiss()

        //to make sure time is correct
        val t: Int = if(time != Snackbar.LENGTH_LONG
            || time != Snackbar.LENGTH_SHORT
            || time != Snackbar.LENGTH_INDEFINITE
        ) Snackbar.LENGTH_INDEFINITE
        else time

        snackBar = Snackbar.make(view, message, t)
        snackBar?.setAction(actionName) {
            action()
            snackBar?.dismiss()
        }
        snackBar?.setAction(actionName) {
            action()
            snackBar?.dismiss()
        }
        snackBar?.show()
    }

}