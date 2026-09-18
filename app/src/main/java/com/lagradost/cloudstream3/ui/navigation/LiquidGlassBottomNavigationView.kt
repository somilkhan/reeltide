package com.lagradost.cloudstream3.ui.navigation

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import kotlin.math.max
import kotlin.math.min

/**
 * Bottom navigation renderer for the liquid-glass island design.
 *
 * Navigation remains owned by Material's BottomNavigationView/menu. This class only changes
 * how the five existing menu item views are measured and positioned:
 * - inactive item: fixed 40dp
 * - selected item: content-width pill
 * - 4dp visual gap between items
 *
 * NavigationBarMenuView already wraps selection changes in a ChangeBounds-capable transition,
 * so these width/position changes animate together with the selected label.
 */
class LiquidGlassBottomNavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = com.google.android.material.R.attr.bottomNavigationStyle,
) : BottomNavigationView(context, attrs, defStyleAttr) {

    override fun createNavigationBarMenuView(context: Context): LiquidGlassBottomNavigationMenuView {
        return LiquidGlassBottomNavigationMenuView(context)
    }
}

class LiquidGlassBottomNavigationMenuView(
    context: Context,
) : BottomNavigationMenuView(context) {

    private val inactiveItemWidth =
        resources.getDimensionPixelSize(com.lagradost.cloudstream3.R.dimen.home_nav_item_width)
    private val itemGap =
        resources.getDimensionPixelSize(com.lagradost.cloudstream3.R.dimen.home_nav_item_gap)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val availableWidth = MeasureSpec.getSize(widthMeasureSpec)
        val parentHeight = MeasureSpec.getSize(heightMeasureSpec)
        val childHeightSpec = MeasureSpec.makeMeasureSpec(parentHeight, MeasureSpec.AT_MOST)

        val visibleChildren = (0 until childCount)
            .map { getChildAt(it) }
            .filter { it.visibility != View.GONE }

        if (visibleChildren.isEmpty()) {
            setMeasuredDimension(0, max(suggestedMinimumHeight, 0))
            return
        }

        val selectedPosition = getSelectedItemPosition()
        val selectedChild = visibleChildren.getOrNull(selectedPosition)

        var activeWidth = inactiveItemWidth
        if (selectedChild != null) {
            selectedChild.measure(
                MeasureSpec.makeMeasureSpec(
                    max(availableWidth, inactiveItemWidth),
                    MeasureSpec.AT_MOST
                ),
                childHeightSpec
            )
            activeWidth = max(inactiveItemWidth, selectedChild.measuredWidth)
        }

        val inactiveCount = visibleChildren.size - if (selectedChild != null) 1 else 0
        val gapsWidth = itemGap * max(0, visibleChildren.size - 1)
        val inactiveWidth = inactiveItemWidth
        val maxActiveWidth = max(
            inactiveItemWidth,
            availableWidth - (inactiveWidth * inactiveCount) - gapsWidth
        )
        activeWidth = min(activeWidth, maxActiveWidth)

        val totalWidth = (inactiveWidth * inactiveCount) +
            (if (selectedChild != null) activeWidth else 0) +
            gapsWidth

        var maxHeight = 0
        visibleChildren.forEach { child ->
            val width = if (child === selectedChild) activeWidth else inactiveWidth
            child.measure(
                MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                childHeightSpec
            )
            child.layoutParams.width = width
            maxHeight = max(maxHeight, child.measuredHeight)
        }

        val finalWidth = when (MeasureSpec.getMode(widthMeasureSpec)) {
            MeasureSpec.EXACTLY -> min(availableWidth, totalWidth)
            MeasureSpec.AT_MOST -> min(availableWidth, totalWidth)
            else -> totalWidth
        }

        setMeasuredDimension(
            finalWidth,
            max(maxHeight, suggestedMinimumHeight)
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val width = right - left
        val height = bottom - top
        var used = 0

        if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
            for (index in 0 until childCount) {
                val child = getChildAt(index)
                if (child.visibility == View.GONE) continue
                val childLeft = width - used - child.measuredWidth
                child.layout(childLeft, 0, childLeft + child.measuredWidth, height)
                used += child.measuredWidth + itemGap
            }
        } else {
            for (index in 0 until childCount) {
                val child = getChildAt(index)
                if (child.visibility == View.GONE) continue
                child.layout(used, 0, used + child.measuredWidth, height)
                used += child.measuredWidth + itemGap
            }
        }
    }
}
