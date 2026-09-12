package com.lagradost.cloudstream3.ui.home

import androidx.appcompat.widget.SearchView
import com.lagradost.cloudstream3.databinding.FragmentHomeHeadBinding

/**
 * The phone Home header no longer renders the legacy top SearchView, but the
 * shared preview holder still contains legacy listener wiring. Keep that
 * wiring harmless without reintroducing the hidden control into the UI.
 */
val FragmentHomeHeadBinding.homeSearch: SearchView
    get() = SearchView(root.context)
