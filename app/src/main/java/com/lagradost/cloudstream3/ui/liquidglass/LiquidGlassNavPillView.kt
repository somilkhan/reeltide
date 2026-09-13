package com.lagradost.cloudstream3.ui.liquidglass

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.lagradost.cloudstream3.R
import com.lagradost.cloudstream3.ui.settings.Globals.PHONE
import com.lagradost.cloudstream3.ui.settings.Globals.isLayout

private enum class NavInteractionState {
    Idle,
    Pressed,
    Selected,
    Transitioning,
    Disabled,
    Scrolling,
}

private data class NavItem(
    val destinationId: Int,
    val title: String,
    @DrawableRes val iconRes: Int,
)

private val navItems = listOf(
    NavItem(R.id.navigation_home, "Home", R.drawable.home_icon_selector),
    NavItem(R.id.navigation_search, "Search", R.drawable.search_icon),
    NavItem(R.id.navigation_library, "Library", R.drawable.library_icon_selector),
    NavItem(R.id.navigation_downloads, "Downloads", R.drawable.netflix_download),
    NavItem(R.id.navigation_settings, "Settings", R.drawable.settings_icon_selector),
)

private val GlassBackground = Color(0xD90B0B0D)
private val GlassBorder = Color(0x20FFFFFF)
private val GlassSurfaceSelected = Color(0x24FFFFFF)
private val PrimaryText = Color(0xFFF7F7F8)
private val SecondaryText = Color(0xFF9B9BA1)

class LiquidGlassNavPillView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AbstractComposeView(context, attrs, defStyleAttr) {

    @Composable
    override fun Content() {
        if (!isLayout(PHONE)) return

        val activity = LocalContext.current as? FragmentActivity ?: return
        val navHost = remember(activity) {
            activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        } ?: return
        val navController = navHost.navController
        var selectedId by remember { mutableIntStateOf(R.id.navigation_home) }
        var transitioningId by remember { mutableIntStateOf(0) }

        LaunchedEffect(selectedId) {
            transitioningId = selectedId
            kotlinx.coroutines.delay(240)
            if (transitioningId == selectedId) transitioningId = 0
        }

        DisposableEffect(navController) {
            val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
                selectedId = destination.topLevelDestinationId()
            }
            selectedId = navController.currentDestination?.topLevelDestinationId() ?: R.id.navigation_home
            navController.addOnDestinationChangedListener(listener)
            onDispose { navController.removeOnDestinationChangedListener(listener) }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp),
            contentAlignment = Alignment.BottomCenter,
        ) {
            Row(
                modifier = Modifier
                    .shadow(16.dp, RoundedCornerShape(28.dp), clip = false)
                    .clip(RoundedCornerShape(28.dp))
                    .background(GlassBackground)
                    .border(1.dp, GlassBorder, RoundedCornerShape(28.dp))
                    .padding(6.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                navItems.forEach { item ->
                    LiquidGlassNavItem(
                        item = item,
                        selected = selectedId == item.destinationId,
                        transitioning = transitioningId == item.destinationId,
                        onClick = {
                            activity.findViewById<android.view.View>(R.id.nav_view)
                                ?.let { navView ->
                                    navView.findViewById<android.view.View>(item.destinationId)?.performClick()
                                        ?: navView.menu.findItem(item.destinationId)?.let { navView.selectedItemId = it.itemId }
                                }
                        },
                        onLongClick = {
                            activity.findViewById<android.view.View>(R.id.nav_view)
                                ?.findViewById<android.view.View>(item.destinationId)
                                ?.performLongClick()
                        },
                    )
                }
            }
        }
    }

    private fun NavDestination.topLevelDestinationId(): Int = when (id) {
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
        else -> id
    }
}

@Composable
private fun LiquidGlassNavItem(
    item: NavItem,
    selected: Boolean,
    transitioning: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val state = when {
        pressed -> NavInteractionState.Pressed
        transitioning -> NavInteractionState.Transitioning
        selected -> NavInteractionState.Selected
        else -> NavInteractionState.Idle
    }
    val scale by animateFloatAsState(
        targetValue = when (state) {
            NavInteractionState.Pressed -> 0.96f
            NavInteractionState.Selected -> 1f
            NavInteractionState.Transitioning -> 1.02f
            NavInteractionState.Scrolling -> 0.98f
            NavInteractionState.Disabled -> 0.90f
            NavInteractionState.Idle -> 0.92f
        },
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "nav-icon-scale",
    )

    Row(
        modifier = Modifier
            .height(52.dp)
            .animateContentSize(spring(stiffness = Spring.StiffnessMediumLow))
            .clip(CircleShape)
            .background(if (selected) GlassSurfaceSelected else Color.Transparent)
            .semantics {
                contentDescription = item.title
                role = Role.Tab
                this.selected = selected
                if (state == NavInteractionState.Disabled) disabled()
            }
            .combinedClickable(
                enabled = state != NavInteractionState.Disabled,
                onClickLabel = "Open ${item.title}",
                onLongClickLabel = "Scroll ${item.title} to top",
                interactionSource = interactionSource,
                onClick = onClick,
                onLongClick = onLongClick,
            )
            .padding(horizontal = if (selected) 14.dp else 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(item.iconRes),
            contentDescription = null,
            modifier = Modifier
                .size(22.dp)
                .graphicsLayer { scaleX = scale; scaleY = scale },
            tint = if (selected) PrimaryText else SecondaryText,
        )
        AnimatedVisibility(
            visible = selected,
            enter = fadeIn(spring()) + expandHorizontally(spring()),
            exit = fadeOut(spring()) + shrinkHorizontally(spring()),
        ) {
            Text(
                text = item.title,
                modifier = Modifier.padding(start = 7.dp),
                color = PrimaryText,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
            )
        }
    }
}
