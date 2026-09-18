package com.lagradost.cloudstream3.ui.liquidglass

import androidx.navigation.NavDestination
import com.lagradost.cloudstream3.R

/** Maps every reachable secondary route to the primary destination that owns it. */
internal fun toLiquidGlassTopLevelId(destinationId: Int): Int = when (destinationId) {
    R.id.navigation_download_child,
    R.id.navigation_download_queue -> R.id.navigation_downloads
    R.id.navigation_subtitles,
    R.id.navigation_chrome_subtitles,
    R.id.navigation_settings_player,
    R.id.navigation_settings_updates,
    R.id.navigation_settings_ui,
    R.id.navigation_settings_account,
    R.id.navigation_settings_providers,
    R.id.navigation_settings_general,
    R.id.navigation_settings_extensions,
    R.id.navigation_settings_plugins,
    R.id.navigation_test_providers -> R.id.navigation_settings
    else -> destinationId
}

internal fun NavDestination.toLiquidGlassTopLevelId(): Int = toLiquidGlassTopLevelId(id)
