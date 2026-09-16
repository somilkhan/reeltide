package com.lagradost.cloudstream3.ui.home

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import androidx.recyclerview.widget.RecyclerView

/**
 * Hero actions belong to the hero itself and should not remain floating on an
 * empty/loading hero. Visibility follows the hero ViewPager's item count.
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
        val count = pager?.adapter?.itemCount ?: 0
        isVisible = count > 0
    }
}
