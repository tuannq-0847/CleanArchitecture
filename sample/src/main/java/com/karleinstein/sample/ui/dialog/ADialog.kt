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

class ADialog(context: Context) : BaseDialog(context) {

    fun updateText(data: String) {
        findViewById<TextView>(R.id.textView)?.text = data
    }

    override val viewBinding: ViewBinding by viewBinding(DialogABinding::inflate)
}
