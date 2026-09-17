package com.lagradost.cloudstream3.ui.home

import android.view.ViewGroup
import androidx.core.view.MarginLayoutParamsCompat

/** Compatibility helper for RTL-aware end margins on Android ViewGroup layout params. */
private fun ViewGroup.MarginLayoutParams.setMarginEnd(value: Int) {
    MarginLayoutParamsCompat.setMarginEnd(this, value)
}
