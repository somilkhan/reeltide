package com.lagradost.cloudstream3.ui.setup

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.lagradost.cloudstream3.R

/** Keeps the main navigation shell out of the first-run setup flow. */
internal fun Fragment.setSetupNavigationVisible(visible: Boolean) {
    activity?.findViewById<android.view.View>(R.id.liquid_glass_nav_container)?.apply {
        if (visible) isVisible = true else isGone = true
    }
}
