package com.krause.cleanarchitecture.ui.animscroll

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.karleinstein.karlrecy.listener.KarlRecycleListener
import com.karleinstein.karlrecy.overscroll.OverScrollOnTouchHandler
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.math.abs


@SuppressLint("ClickableViewAccessibility")
class RecyclerScrollAnimBG(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs),
    KarlRecycleListener {

    var drawableResource: Drawable? = null
    private val res = IntArray(3)
    var stateAppBarShrink: MutableStateFlow<StateAppBarShrink> =
        MutableStateFlow(StateAppBarShrink.IDLE)
    private val shape = GradientDrawable()
    private var currentValue = 0
    private var rawValue = 0F
    private var isScrollingDown = false
    private var isScrollingUp = false
    private var isIDLE = false
    private val overScrollOnTouchHandler =
        OverScrollOnTouchHandler(isEnableOverScroll = false, isEnableSwipeToOptions = false)

    init {
        overScrollOnTouchHandler.setOnKarlRecycleViewListener(this)
        this.setOnTouchListener(overScrollOnTouchHandler)
        shape.shape = GradientDrawable.RECTANGLE
        shape.setColor(Color.BLACK)
        shape.alpha = 0
        changeRadiusBackground(0F)
    }

    fun resetCornerRadius() {
        shape.shape = GradientDrawable.RECTANGLE
        shape.setColor(Color.BLACK)
        changeRadiusBackground(0F)
        shape.alpha=255
        this.background = shape
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
//        handleAppBarShrinkWhenScrolling()
        when (state) {
            SCROLL_STATE_IDLE -> {
                isIDLE = true
                Log.d("RecyclerScrollAnimBG", "The RecyclerView is not scrolling")
            }
            SCROLL_STATE_DRAGGING -> {
                isIDLE = false
                Log.d("RecyclerScrollAnimBG", "onScrollStateChanged: x: $x y: $y")
                Log.d("RecyclerScrollAnimBG", "Scrolling now")

            }
            SCROLL_STATE_SETTLING -> {
                isIDLE = false
                Log.d("RecyclerScrollAnimBG", "Scroll Settling")

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (!isFirstCompletelyVisiblePosition() && !isRadiusDefault()) {
            changeRadiusBackground(0F)
        }
        Log.d("RecyclerScrollAnimBG", "l: $l t: $t oldl: $oldl oldt: $oldt");
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("ClickableViewAccessibility")
    override fun onScrolled(dx: Int, dy: Int) {
        super.onScrolled(dx, dy)
        overScrollOnTouchHandler.isVisibleFirstPosition =
            isFirstCompletelyVisiblePosition()
        overScrollOnTouchHandler.isVisibleLastPosition =
            (layoutManager as StaggeredGridLayoutManager).findLastCompletelyVisibleItemPositions(res)[0] == adapter?.itemCount?.minus(
                1
            )
//        handleAppBarShrinkWhenScrolling()
        when {
            dx > 0 -> {
                Log.d("RecyclerScrollAnimBG", "Scrolled Right");
            }
            dx < 0 -> {
                Log.d("RecyclerScrollAnimBG", "Scrolled Left");
            }
            else -> {
                Log.d("RecyclerScrollAnimBG", "No Horizontal Scrolled");
            }
        }

        when {
            dy > 0 -> {
                isScrollingUp = false
                if (!isScrollingDown) {
                    isScrollingDown = true
                }
                Log.d("RecyclerScrollAnimBG", "Scrolled Downwards: $currentValue");
            }
            dy < 0 -> {
                isScrollingDown = false
                if (!isScrollingUp) {
                    isScrollingUp = true
                }
                val job = CoroutineScope(Dispatchers.Main).launch {
                    stateAppBarShrink.collectLatest {
                        Log.d(
                            "TAG",
                            "stateAppBarShrink: a: $it rawValue: $rawValue ${abs(rawValue)}"
                        )
                        when (it) {
                            StateAppBarShrink.COLLAPSED -> {
                                changeRadiusBackground(0F)
                                this@RecyclerScrollAnimBG.background.alpha = 0
                            }
                            else -> {

                            }
                        }
                    }
                }
                if (!isFirstCompletelyVisiblePosition()) job.cancel()
                Log.d("RecyclerScrollAnimBG", "Scrolled Upwards");
            }
            else -> {
                Log.d("RecyclerScrollAnimBG", "No Vertical Scrolled");
            }
        }
    }

    override fun onAnimateTranslationY(translationY: Float) {

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun handleAppBarShrinkWhenScrolling(event: MotionEvent? = null) {
        val job = CoroutineScope(Dispatchers.Main).launch {
            stateAppBarShrink.collectLatest {
                Log.d("TAG", "stateAppBarShrink: a: $it rawValue: $rawValue ${abs(rawValue)}")
                when (it) {
                    StateAppBarShrink.COLLAPSED -> {
                        when {
                            isIDLE -> {
                                changeRadiusBackground(dpToPx(0))
                            }
                            rawValue < 0 -> {
                                if (event?.action == MotionEvent.ACTION_UP) {
                                    changeRadiusBackground(0F)
                                } else if (abs(rawValue) > dpToPx(32) && isFirstCompletelyVisiblePosition()) {
                                    changeRadiusBackground(dpToPx(32))
                                } else if (abs(rawValue) <= dpToPx(32) && isFirstCompletelyVisiblePosition()) {
                                    changeRadiusBackground(rawValue)
                                }
                                this@RecyclerScrollAnimBG.background.alpha = 255
                            }
                            event?.action == MotionEvent.ACTION_UP -> {
                                changeRadiusBackground(0F)
                                this@RecyclerScrollAnimBG.background.alpha = 255
                            }
                            else -> {
                                this@RecyclerScrollAnimBG.background.alpha = currentValue
                            }
                        }
                    }
                    else -> {
                        if (event?.action == MotionEvent.ACTION_UP || rawValue > 0) {
                            this@RecyclerScrollAnimBG.background.alpha = 0
                        } else {
                            this@RecyclerScrollAnimBG.background.alpha = currentValue
                        }
                    }
                }
            }
        }
        if (!isFirstCompletelyVisiblePosition()) job.cancel()
    }

    private fun changeRadiusBackground(value: Float) {
        shape.cornerRadii = floatArrayOf(
            abs(value),
            abs(value),
            abs(value),
            abs(value),
            0F,
            0F,
            0F,
            0F
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onMotionEventListener(event: MotionEvent) {
        Log.d("TAG", "onMotionEventListener: event action: ${event.action}")
        handleAppBarShrinkWhenScrolling(event)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun isRadiusDefault(): Boolean {
        return if (shape.cornerRadii == null) {
            false
        } else (shape.cornerRadii?.get(0) == 0F || shape.cornerRadii?.get(1) == 0F
                || shape.cornerRadii?.get(2) == 0F || shape.cornerRadii?.get(3) == 0F
                )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onScrollingListener(currentValue: Float, event: MotionEvent) {
        this.background = shape
        rawValue = currentValue
    }

    private fun isFirstCompletelyVisiblePosition() =
        (layoutManager as StaggeredGridLayoutManager).findFirstCompletelyVisibleItemPositions(res)[0] == 0

    private fun isFirstVisiblePosition() =
        (layoutManager as StaggeredGridLayoutManager).findFirstVisibleItemPositions(res)[0] == 0

    companion object {

        fun dpToPx(dp: Int): Float {
            return (dp * Resources.getSystem().displayMetrics.density)
        }
    }
}
