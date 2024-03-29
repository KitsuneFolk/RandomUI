package com.pandacorp.randomui.presentation.utils.dialogs

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.preference.PreferenceManager

abstract class CustomDialog(private val context: Context) : Dialog(context) {
    protected val sp: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // remove the default background so that dialog can be rounded
            clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND) // remove the shadow
        }
    }
}