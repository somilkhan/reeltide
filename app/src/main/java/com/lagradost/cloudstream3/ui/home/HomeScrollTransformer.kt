package com.lagradost.cloudstream3.ui.home

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class HomeScrollTransformer : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        val distance = abs(position).coerceIn(0f, 1f)

        // The hero is intentionally edge-to-edge. Do not scale pages down: even a small
        // scale exposes the black parent around the artwork during a swipe. Let ViewPager2
        // handle the physical slide and use alpha only for restrained depth.
        page.scaleX = 1f
        page.scaleY = 1f
        page.alpha = 1f - (0.25f * distance)
        page.translationX = 0f
    }
}
