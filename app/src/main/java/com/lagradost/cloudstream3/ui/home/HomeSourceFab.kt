package com.lagradost.cloudstream3.ui.home

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.lagradost.cloudstream3.R
import com.lagradost.cloudstream3.utils.AppContextUtils.filterProviderByPreferredMedia

/**
 * Home source control. Keep the existing source-picker action, but present it as a
 * compact floating control that belongs to the functional/glass layer rather than
 * a large opaque Material FAB.
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

    private fun hasSelectableProviders(): Boolean =
        context.filterProviderByPreferredMedia().isNotEmpty()

    override fun setText(text: CharSequence?, type: TextView.BufferType?) {
        super.setText(normalize(text), type)
    }

    override fun setVisibility(visibility: Int) {
        if (visibility == VISIBLE && !hasSelectableProviders()) {
            super.setVisibility(GONE)
        } else {
            super.setVisibility(visibility)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val density = resources.displayMetrics.density
        val dp = { value: Float -> (value * density).toInt() }

        backgroundTintList = ColorStateList.valueOf(0x1AFFFFFF)
        setStrokeColor(ColorStateList.valueOf(0x26FFFFFF))
        strokeWidth = dp(1f)
        iconTint = ColorStateList.valueOf(0xFFFFFFFF.toInt())
        textColor = ColorStateList.valueOf(0xFFFFFFFF.toInt())
        rippleColor = ColorStateList.valueOf(0x24FFFFFF)
        cornerRadius = dp(22f)
        elevation = dp(8f).toFloat()
        iconSize = dp(18f)
        setPadding(dp(14f), 0, dp(14f), 0)

        layoutParams = layoutParams?.apply {
            width = LayoutParams.WRAP_CONTENT
            height = dp(44f)
            if (this is MarginLayoutParams) {
                marginEnd = dp(16f)
                bottomMargin = dp(84f)
            }
        }

        post {
            isVisible = hasSelectableProviders()
        }
        super.onAttachedToWindow()
    }
}
