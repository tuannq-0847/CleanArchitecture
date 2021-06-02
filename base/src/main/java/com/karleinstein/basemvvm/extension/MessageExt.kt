package com.karleinstein.basemvvm.extension

import android.content.Context
import android.widget.Toast
import java.util.*

private val stackToast = Stack<Toast>()

//toast extensions
fun Context?.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}
