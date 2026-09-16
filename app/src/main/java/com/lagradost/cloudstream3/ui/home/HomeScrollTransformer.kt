package com.lagradost.cloudstream3.ui.home

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class HomeScrollTransformer : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        val distance = abs(position).coerceIn(0f, 1f)

        // Keep the hero genuinely full-bleed while giving adjacent pages a restrained,
        // depth-based transition. The old padding-based transform exposed black gutters
        // during swipes and visually broke the cinematic hero composition.
        val scale = 1f - (0.04f * distance)
        page.scaleX = scale
        page.scaleY = scale
        page.alpha = 1f - (0.35f * distance)
        page.translationX = 0f
    }
}
