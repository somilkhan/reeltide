package com.lagradost.cloudstream3.ui.home

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.ViewGroup
import com.google.android.material.button.MaterialButton
import com.lagradost.cloudstream3.R

/**
 * Keeps the redesigned phone hero CTA geometry authoritative while preserving
 * the existing adapter's MaterialButton binding contract.
 */
class HomeHeroActionButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : MaterialButton(context, attrs, defStyleAttr) {

    override fun setText(text: CharSequence?, type: BufferType?) {
        val value = if (id == R.id.home_preview_play && TextUtils.isEmpty(text)) "Play" else text
        super.setText(value, type)
    }

    override fun setLayoutParams(params: ViewGroup.LayoutParams?) {
        if (params is ViewGroup.MarginLayoutParams) {
            when (id) {
                R.id.home_preview_play -> {
                    params.width = 0
                    params.height = dp(48)
                    if (params is ViewGroup.MarginLayoutParams) {
                        params.marginStart = 0
                        params.marginEnd = dp(10)
                    }
                    if (params is android.widget.LinearLayout.LayoutParams) {
                        params.weight = 1f
                    }
                }
                R.id.home_preview_info -> {
                    params.width = dp(116)
                    params.height = dp(48)
                    if (params is ViewGroup.MarginLayoutParams) {
                        params.marginStart = dp(10)
                        params.marginEnd = 0
                    }
                    if (params is android.widget.LinearLayout.LayoutParams) {
                        params.weight = 0f
                    }
                }
            }
        }
        super.setLayoutParams(params)
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()
}
