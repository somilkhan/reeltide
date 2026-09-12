package com.lagradost.cloudstream3.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lagradost.cloudstream3.R
import com.lagradost.cloudstream3.SearchResponse
import com.lagradost.cloudstream3.databinding.HomeTrendingGridBinding
import com.lagradost.cloudstream3.ui.BaseDiffCallback
import com.lagradost.cloudstream3.ui.NoStateAdapter
import com.lagradost.cloudstream3.ui.ViewHolderState
import com.lagradost.cloudstream3.ui.search.SEARCH_ACTION_LOAD
import com.lagradost.cloudstream3.ui.search.SearchClickCallback
import com.lagradost.cloudstream3.utils.ImageLoader.loadImage

class HomeTrendingItemAdapter(
    private val clickCallback: (SearchClickCallback) -> Unit,
) : NoStateAdapter<SearchResponse>(
    diffCallback = BaseDiffCallback(
        itemSame = { a, b -> a.url == b.url && a.name == b.name },
        contentSame = { a, b -> a == b },
    )
) {
    override fun onCreateContent(parent: ViewGroup): ViewHolderState<Any> {
        val binding = HomeTrendingGridBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return ViewHolderState(binding)
    }

    override fun onBindContent(
        holder: ViewHolderState<Any>,
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
    }

    override fun onClearView(holder: ViewHolderState<Any>) {
        val binding = holder.view as? HomeTrendingGridBinding ?: return
        clearImage(binding.trendingImage)
    }
}
