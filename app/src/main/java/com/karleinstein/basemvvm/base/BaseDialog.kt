package com.karleinstein.basemvvm.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.viewbinding.ViewBinding

abstract class BaseDialog(context: Context) : Dialog(context), BaseDialogView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
    }
}
