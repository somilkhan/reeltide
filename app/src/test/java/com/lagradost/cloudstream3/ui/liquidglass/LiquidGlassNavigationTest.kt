package com.lagradost.cloudstream3.ui.liquidglass

import com.lagradost.cloudstream3.R
import org.junit.Assert.assertEquals
import org.junit.Test

class LiquidGlassNavigationTest {
    @Test
    fun downloadSecondaryRoutesResolveToDownloads() {
        assertEquals(
            R.id.navigation_downloads,
            toLiquidGlassTopLevelId(R.id.navigation_download_child),
        )
        assertEquals(
            R.id.navigation_downloads,
            toLiquidGlassTopLevelId(R.id.navigation_download_queue),
        )
    }

    @Test
    fun settingsSecondaryRoutesResolveToSettings() {
        val routes = intArrayOf(
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
            R.id.navigation_test_providers,
        )

        routes.forEach { route ->
            assertEquals(R.id.navigation_settings, toLiquidGlassTopLevelId(route))
        }
    }

    @Test
    fun primaryRoutesRemainStable() {
        val routes = intArrayOf(
            R.id.navigation_home,
            R.id.navigation_search,
            R.id.navigation_library,
            R.id.navigation_downloads,
            R.id.navigation_settings,
        )

        routes.forEach { route ->
            assertEquals(route, toLiquidGlassTopLevelId(route))
        }
    }
}
