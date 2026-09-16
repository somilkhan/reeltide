package com.lagradost.cloudstream3.ui.home

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
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

        // Restrained liquid-glass treatment: one compact functional layer above content.
        backgroundTintList = ColorStateList.valueOf(0x1AFFFFFF)
        setStrokeColor(ColorStateList.valueOf(0x30FFFFFF))
        strokeWidth = dp(1f)
        iconTint = ColorStateList.valueOf(0xFFFFFFFF.toInt())
        textColor = ColorStateList.valueOf(0xFFFFFFFF.toInt())
        rippleColor = ColorStateList.valueOf(0x28FFFFFF)
        cornerRadius = dp(22f)
        elevation = dp(6f).toFloat()
        iconSize = dp(18f)
        setPadding(dp(14f), 0, dp(14f), 0)

        layoutParams = layoutParams?.apply {
            width = LayoutParams.WRAP_CONTENT
            height = dp(44f)
            if (this is MarginLayoutParams) {
                marginStart = dp(16f)
                marginEnd = dp(16f)
                bottomMargin = dp(92f)
            }
        }
    }
}
