package com.gurudev.junotes.Constants

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.gurudev.junotes.R


class CustomProgressDialog(context: Context) : Dialog(context,R.style.TransparentDialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.custom_progress_dialog)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }
}