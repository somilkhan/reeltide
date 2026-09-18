package com.lagradost.cloudstream3.ui.home

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.lagradost.cloudstream3.R

/**
 * Home source picker.
 *
 * This remains a persistent Home control. Its job is to choose/reload the current
 * source; it is not coupled to whether extensions are currently installed.
 */
class HomeSourceFab @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ExtendedFloatingActionButton(context, attrs, defStyleAttr) {

    private fun normalize(text: CharSequence?): CharSequence? {
        return if (text?.toString() == context.getString(R.string.none)) {
            context.getString(R.string.home_source)
        } else {
            text
        }
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(normalize(text), type)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        val density = resources.displayMetrics.density
        fun dp(value: Float): Int = (value * density).toInt()

        // Do not let the Material FAB theme tint this drawable away. The source
        // selector intentionally uses an opaque dark surface so its label remains
        // readable above the bottom navigation island.
        backgroundTintList = null
        setBackgroundResource(R.drawable.home_source_fab_background)
        setStrokeColor(ColorStateList.valueOf(0x00FFFFFF))
        strokeWidth = 0
        iconTint = ColorStateList.valueOf(0xFFFFFFFF.toInt())
        setTextColor(ColorStateList.valueOf(0xFFFFFFFF.toInt()))
        rippleColor = ColorStateList.valueOf(0x35FFFFFF)
        cornerRadius = dp(22f)
        elevation = dp(5f).toFloat()
        stateListAnimator = null
        iconSize = dp(20f)
        iconPadding = dp(7f)
        minHeight = dp(44f)
        minWidth = 0
        setPadding(dp(13f), 0, dp(14f), 0)
        setIconResource(R.drawable.ic_baseline_tune_24)
        setExtended(true)

        val params = layoutParams as? FrameLayout.LayoutParams ?: return
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT
        params.height = dp(44f)
        // Keep the source selector at the bottom-right, above the glass navigation
        // island instead of floating over the hero artwork.
        params.gravity = Gravity.BOTTOM or Gravity.END
        params.marginStart = dp(16f)
        params.marginEnd = dp(22f)
        params.topMargin = 0
        params.bottomMargin = dp(110f)
        layoutParams = params
    }

    // Home intentionally keeps the source selector stable while the content rail scrolls.
    override fun shrink() = setExtended(true)
    override fun extend() = setExtended(true)
}
