package com.lagradost.cloudstream3.ui.home

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lagradost.cloudstream3.ui.account.AccountViewModel as CloudStreamAccountViewModel
import com.lagradost.cloudstream3.ui.result.ResultFragment

/** Compatibility aliases kept local to the Home presentation package. */
typealias AccountViewModel = CloudStreamAccountViewModel

/** Bridges the existing ResultFragment logo binding without changing its implementation. */
fun bindLogo(
    url: String?,
    headers: Map<String, String>?,
    titleView: TextView,
    logoView: ImageView
) {
    ResultFragment.bindLogo(
        url = url,
        headers = headers,
        logoView = logoView,
        titleView = titleView
    )
}

/** Explicit RTL-safe marginEnd bridge for this package's legacy adapter code. */
var ViewGroup.MarginLayoutParams.marginEnd: Int
    get() = getMarginEnd()
    set(value) = setMarginEnd(value)
