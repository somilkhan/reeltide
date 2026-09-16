package com.lagradost.cloudstream3.ui.home

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.lagradost.cloudstream3.R

/**
 * Hero actions belong to the hero itself. They and the hero shell disappear when the
 * ViewPager has no content, preventing a blank 628dp hero from occupying the Home screen.
 */
class HomeHeroActionBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var pager: ViewPager2? = null
    private val adapterObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() = syncVisibility()
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) = syncVisibility()
        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) = syncVisibility()
        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) = syncVisibility()
        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) = syncVisibility()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        pager = findPager()
        pager?.adapter?.registerAdapterDataObserver(adapterObserver)
        post { syncVisibility() }
    }

    override fun onDetachedFromWindow() {
        pager?.adapter?.unregisterAdapterDataObserver(adapterObserver)
        pager = null
        super.onDetachedFromWindow()
    }

    private fun findPager(): ViewPager2? {
        var current: View? = parent as? View
        while (current != null) {
            if (current is ViewPager2) return current
            current = current.parent as? View
        }
        return (parent as? View)?.findViewById(R.id.home_preview_viewpager)
    }

    private fun syncVisibility() {
        val hasItems = (pager?.adapter?.itemCount ?: 0) > 0
        isVisible = hasItems

        // updatePreview() normally owns the hero shell visibility. If a successful provider
        // response contains an empty hero list, however, there is no content callback to hide
        // the shell. Collapse only that specific shell here; non-empty states remain untouched.
        if (!hasItems) {
            (parent as? View)?.takeIf { it.id == R.id.home_preview_viewpager_text }?.isVisible = false
        }
    }
}
