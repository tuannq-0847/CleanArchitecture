package com.karleinstein.karlrecy

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.core.view.forEachIndexed
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karleinstein.karlrecy.listener.RecyclerAdapterListener
import java.util.concurrent.Executors
import kotlin.math.abs

abstract class BaseRecyclerAdapter<Item : Any>(
    callBack: DiffUtil.ItemCallback<Item> = BaseDiffUtil(),
    @LayoutRes var swipeForOptionsLayout: Int? = null
) : ListAdapter<Item, BaseViewHolder>(
    AsyncDifferConfig.Builder<Item>(callBack)
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
        .build()
), RecyclerAdapterListener<Item> {

    protected var dataRaw: List<Item> = listOf()
    private val states = mutableMapOf<Item, Boolean>()
    private var swipedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = swipeForOptionsLayout?.let {
            LayoutInflater.from(parent.context).inflate(it, parent, false)
        }
        val frameLayout = FrameLayout(parent.context)
        frameLayout.layoutParams =
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
        frameLayout.addView(view)
        frameLayout.addView(
            LayoutInflater.from(parent.context)
                .inflate(viewType, parent, false)
        )
        return BaseViewHolder(
            itemView = frameLayout,
            parent = parent,
            onSwiped = onSwiped
        ).apply {
            bindFirstTime(this)
            dataRaw.forEach {
                states[it] = false
            }
        }
    }

    open fun bindFirstTime(baseViewHolder: BaseViewHolder) {}

    protected fun onClicked(baseViewHolder: BaseViewHolder) {
        if (baseViewHolder.absoluteAdapterPosition != swipedPosition && swipedPosition != -1) {
            baseViewHolder.visiblePreviousVH()
            notifyItemChanged(swipedPosition)
            swipedPosition = -1
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (swipedPosition != -1) {
            holder.visiblePreviousVH()
        }
        val item = getItem(position)
        onBind(holder, item, position)
    }

    override fun getItemViewType(position: Int): Int {
        return buildLayoutRes(position)
    }

    protected fun stateClickedHandler(
        isStateChanged: Boolean,
        baseViewHolder: BaseViewHolder
    ): Boolean {
        if (baseViewHolder.absoluteAdapterPosition < states.keys.size) {
            val key = states.keys.toList()[baseViewHolder.absoluteAdapterPosition]
            states[key] = isStateChanged
        }
        return isStateChanged
    }

    private val onSwiped = fun(pos: Int) {
        if (swipedPosition == -1) {
            swipedPosition = pos
            return
        }
        if (swipedPosition != pos) {
            notifyItemChanged(swipedPosition)
            swipedPosition = pos
        }
    }
}

class BaseDiffUtil<Item : Any> : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem === newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}

@SuppressLint("ClickableViewAccessibility")
open class BaseViewHolder(
    itemView: View,
    val parent: ViewGroup,
    val onSwiped: (pos: Int) -> Unit = {}
) : RecyclerView.ViewHolder(itemView) {

    private var touchSwipeList = mutableListOf<Float>()

    private fun setLayoutSwipe(@LayoutRes swipeLayout: Int?) {

    }

    init {
        itemView.setOnTouchListener { v, event ->
            Log.d("TAG", ": motion event action: ${event.action}")
            if (event.action == MotionEvent.ACTION_MOVE) {
                handleTouchSwipeToOptions(event.rawX)
                if (touchSwipeList.size == 2) {
                    if (isShouldAnimate(touchSwipeList[1] - touchSwipeList[0])) {
                        animateTopLayer(touchSwipeList[1] - touchSwipeList[0])
                    } else {
                        touchSwipeList.clear()
                    }
//                    animateTopLayer(touchSwipeList[1] - touchSwipeList[0])
                }
            }
            return@setOnTouchListener true
        }
        itemView.setOnTouchListener(null)
    }

    fun visiblePreviousVH() {
        touchSwipeList.clear()
        getTopLayers().forEach {
            it.translationX = 0F
            it.visibility = View.VISIBLE
        }
    }

    private fun isShouldAnimate(animatedValue: Float): Boolean {
        Log.d(
            "AnimatedValue",
            "isShouldAnimate: itemView width: ${convert(itemView.width)} animatedValue: $animatedValue"
        )
        if (abs(animatedValue) < convert(itemView.width) / 6) {
            return false
        }
        return true
    }

    private fun convert(width: Int): Float {
        return width / Resources.getSystem().displayMetrics.density
    }

    private fun handleAnimateSwipe(rawX: Float) {
        ObjectAnimator.ofFloat(rawX, 0F).apply {
            addUpdateListener {
                val viewGroup = itemView as? ViewGroup
                viewGroup?.forEachIndexed { index, view ->
                    Log.d("handleAnimateSwipe", "view: $view")
                    if (index > 0) {
                        view.translationX = it.animatedValue as Float
                    }
                }
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    touchSwipeList.clear()
                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationStart(animation: Animator?) {

                }
            })
            duration = 1000
            start()
        }
    }

    private fun handleTouchSwipeToOptions(rawX: Float) {
        if (touchSwipeList.firstOrNull() == null) {
            touchSwipeList.add(0, rawX)
            return
        }
        if (touchSwipeList.size == 1)
            touchSwipeList.add(1, rawX)
        else {
            touchSwipeList.removeAt(1)
            touchSwipeList.add(rawX)
        }
    }

    private fun animateTopLayer(animatedValue: Float) {
        getTopLayers().forEach {
            it.translationX = animatedValue
            when {
                animatedValue < -convert(itemView.width) / 2 -> {
                    onSwiped(absoluteAdapterPosition)
                    it.visibility = View.GONE
                    touchSwipeList.clear()
                }
                animatedValue > convert(itemView.width) / 2 -> {
                    onSwiped(absoluteAdapterPosition)
                    it.visibility = View.GONE
                    touchSwipeList.clear()
                }
            }
        }
    }

    private fun getTopLayers(): MutableList<View> {
        val topLayers = mutableListOf<View>()
        val viewGroup = itemView as? ViewGroup
        viewGroup?.forEachIndexed { index, view ->
            if (index > 0) {
                topLayers.add(view)
            }
        }
        return topLayers
    }
}
