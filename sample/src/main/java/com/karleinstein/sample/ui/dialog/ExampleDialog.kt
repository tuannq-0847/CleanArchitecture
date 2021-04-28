package com.karleinstein.sample.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import com.karleinstein.basemvvm.base.BaseDialog
import com.karleinstein.basemvvm.utils.viewBinding
import com.karleinstein.sample.R
import com.karleinstein.sample.databinding.DialogABinding

class ExampleDialog(context: Context) : BaseDialog(context) {

    fun updateText(data: String) {
        viewBinding.textView.text = data
    }

    override val viewBinding: DialogABinding by viewBinding(DialogABinding::inflate)
}
