package com.lagradost.cloudstream3.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.lagradost.cloudstream3.SearchResponse
import com.lagradost.cloudstream3.databinding.HomeTrendingGridBinding
import com.lagradost.cloudstream3.ui.ViewHolderState
import com.lagradost.cloudstream3.ui.search.SEARCH_ACTION_LOAD
import com.lagradost.cloudstream3.ui.search.SearchClickCallback
import com.lagradost.cloudstream3.utils.ImageLoader.loadImage

class HomeTrendingItemAdapter(
    id: Int,
    nextFocusUp: Int? = null,
    nextFocusDown: Int? = null,
    clickCallback: (SearchClickCallback) -> Unit,
) : HomeChildItemAdapter(
    id = id,
    nextFocusUp = nextFocusUp,
    nextFocusDown = nextFocusDown,
    clickCallback = clickCallback,
) {
    override fun onCreateContent(parent: ViewGroup): ViewHolderState<Boolean> {
        val binding = HomeTrendingGridBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return HomeScrollViewHolderState(binding)
    }

    override fun onBindContent(
        holder: ViewHolderState<Boolean>,
        item: SearchResponse,
        position: Int,
    ) {
        val binding = holder.view as? HomeTrendingGridBinding ?: return
        binding.trendingRank.text = (position + 1).toString()
        binding.trendingImage.loadImage(item.posterUrl)
        holder.itemView.setOnClickListener { view ->
            clickCallback(
                SearchClickCallback(
                    SEARCH_ACTION_LOAD,
                    view,
                    position,
                    item,
                )
            )
        }
        holder.itemView.tag = position
    }

    override fun onClearView(holder: ViewHolderState<Boolean>) {
        val binding = holder.view as? HomeTrendingGridBinding ?: return
        clearImage(binding.trendingImage)
    }
}
