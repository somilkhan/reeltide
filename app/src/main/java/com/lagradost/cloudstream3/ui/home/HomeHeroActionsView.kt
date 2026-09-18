package com.lagradost.cloudstream3.ui.home

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class HeroActionState(
    val itemCount: Int,
    val position: Int,
)

private val HeroWhite = Color(0xFFFFFFFF)
private val HeroBlack = Color(0xFF090909)
private val HeroGlass = Color(0xE61B1B1B)
private val HeroGlassBorder = Color(0x407BA6FF)

class HomeHeroActionsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : AbstractComposeView(context, attrs) {

    private var state by mutableStateOf(HeroActionState(itemCount = 0, position = 0))
    private var onPlay: (() -> Unit)? = null
    private var onDetails: (() -> Unit)? = null

    init {
        setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnDetachedFromWindowOrReleasedFromPool
        )
        importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_YES
    }

    fun bind(
        itemCount: Int,
        position: Int,
        onPlay: () -> Unit,
        onDetails: () -> Unit,
    ) {
        state = HeroActionState(
            itemCount = itemCount,
            position = position.coerceAtLeast(0),
        )
        this.onPlay = onPlay
        this.onDetails = onDetails
    }

    fun updatePosition(position: Int) {
        state = state.copy(
            itemCount = state.itemCount,
            position = position.coerceAtLeast(0),
        )
    }

    fun clearActions() {
        onPlay = null
        onDetails = null
        state = HeroActionState(0, 0)
    }

    @Composable
    override fun Content() {
        val itemCount = state.itemCount
        if (itemCount <= 0) return

        val visibleCount = minOf(5, itemCount)
        val active = (state.position % 5).coerceIn(0, visibleCount - 1)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                HeroActionButton(
                    modifier = Modifier.weight(1f),
                text = "Play",
                icon = com.lagradost.cloudstream3.R.drawable.ic_baseline_play_arrow_24,
                containerColor = HeroWhite,
                contentColor = HeroBlack,
                onClick = { onPlay?.invoke() },
            )

                HeroActionButton(
                    modifier = Modifier.width(116.dp),
                text = "Details",
                icon = com.lagradost.cloudstream3.R.drawable.ic_outline_info_24,
                containerColor = HeroGlass,
                contentColor = HeroWhite,
                borderColor = HeroGlassBorder,
                    onClick = { onDetails?.invoke() },
                )
            }

            Spacer(Modifier.height(4.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                repeat(visibleCount) { index ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .width(if (index == active) 16.dp else 5.dp)
                        .height(5.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(
                            if (index == active) HeroWhite
                            else HeroWhite.copy(alpha = 0.35f)
                        )
                        .semantics {
                            contentDescription =
                                if (index == active) "Current hero page"
                                else "Hero page " + (index + 1)
                        },
                    )
                }
            }
        }
    }

    @Composable
    private fun HeroActionIcon(
        icon: Int,
        color: Color,
    ) {
        androidx.compose.foundation.Canvas(
            modifier = Modifier.width(20.dp).height(20.dp),
        ) {
            val strokeWidth = 2.dp.toPx()
            when (icon) {
                com.lagradost.cloudstream3.R.drawable.ic_baseline_play_arrow_24 -> {
                    val path = androidx.compose.ui.graphics.Path().apply {
                        moveTo(size.width * 0.34f, size.height * 0.22f)
                        lineTo(size.width * 0.78f, size.height * 0.5f)
                        lineTo(size.width * 0.34f, size.height * 0.78f)
                        close()
                    }
                    drawPath(path, color)
                }
                else -> {
                    drawCircle(
                        color = color,
                        radius = size.minDimension * 0.39f,
                        style = Stroke(width = strokeWidth),
                    )
                    drawLine(
                        color = color,
                        start = androidx.compose.ui.geometry.Offset(size.width * 0.5f, size.height * 0.47f),
                        end = androidx.compose.ui.geometry.Offset(size.width * 0.5f, size.height * 0.72f),
                        strokeWidth = strokeWidth,
                    )
                    drawCircle(
                        color = color,
                        radius = strokeWidth * 0.72f,
                        center = androidx.compose.ui.geometry.Offset(size.width * 0.5f, size.height * 0.31f),
                    )
                }
            }
        }
    }

    @Composable
    private fun HeroActionButton(
        modifier: Modifier,
        text: String,
        icon: Int,
        containerColor: Color,
        contentColor: Color,
        borderColor: Color = Color.Transparent,
        onClick: () -> Unit,
    ) {
        val shape = RoundedCornerShape(100.dp)
        Row(
            modifier = modifier
                .height(48.dp)
                .clip(shape)
                .background(containerColor)
                .border(1.dp, borderColor, shape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick,
                )
                .padding(horizontal = 18.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            HeroActionIcon(
                icon = icon,
                color = contentColor,
            )
            Spacer(Modifier.width(8.dp))
            BasicText(
                text = text,
                style = androidx.compose.ui.text.TextStyle(
                    color = contentColor,
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.Bold,
                ),
            )
        }
    }
}
