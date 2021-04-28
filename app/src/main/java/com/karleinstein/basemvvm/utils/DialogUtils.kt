package com.karleinstein.basemvvm.utils

import android.util.Log
import com.karleinstein.basemvvm.base.BaseDialog
import java.util.*

object DialogUtils {
    private val dialogsQueue: Queue<BaseDialog> = LinkedList()

    fun showDialog(baseDialog: BaseDialog, onClickListener: (id: Int) -> Unit) {
        Log.d("DialogUtils", "dialogs queue: ${dialogsQueue.size}")
        dialogsQueue.offer(baseDialog)
        val first = dialogsQueue.peek()
        if (first != null && !first.isShowing) {
            dialogsQueue.poll()?.run {
                show()
                setOnDismissListener {
                    dialogsQueue.poll()?.show()
                }
            }
        }
    }
}
