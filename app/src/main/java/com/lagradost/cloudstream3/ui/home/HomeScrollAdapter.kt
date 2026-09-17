package com.lagradost.cloudstream3.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.lagradost.cloudstream3.LoadResponse
import com.lagradost.cloudstream3.databinding.HomeScrollViewBinding
import com.lagradost.cloudstream3.databinding.HomeScrollViewTvBinding
import com.lagradost.cloudstream3.ui.BaseDiffCallback
import com.lagradost.cloudstream3.ui.NoStateAdapter
import com.lagradost.cloudstream3.ui.ViewHolderState
import com.lagradost.cloudstream3.ui.result.ResultFragment.bindLogo
import com.lagradost.cloudstream3.ui.settings.Globals.EMULATOR
import com.lagradost.cloudstream3.ui.settings.Globals.TV
import com.lagradost.cloudstream3.ui.settings.Globals.isLayout
import com.lagradost.cloudstream3.utils.AppContextUtils.html
import com.lagradost.cloudstream3.utils.ImageLoader.loadImage

class HomeScrollAdapter(
    val callback: ((View, Int, LoadResponse) -> Unit)
) : NoStateAdapter<LoadResponse>(diffCallback = BaseDiffCallback(itemSame = { a, b ->
    a.uniqueUrl == b.uniqueUrl && a.name == b.name
})) {
    var hasMoreItems: Boolean = false

    override fun onCreateContent(parent: ViewGroup): ViewHolderState<Any> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = if (isLayout(TV or EMULATOR)) {
            HomeScrollViewTvBinding.inflate(inflater, parent, false)
        } else {
            HomeScrollViewBinding.inflate(inflater, parent, false)
        }

        return ViewHolderState(binding)
    }

    override fun onClearView(holder: ViewHolderState<Any>) {
        when (val binding = holder.view) {
            is HomeScrollViewBinding -> clearImage(binding.homeScrollPreview)
            is HomeScrollViewTvBinding -> clearImage(binding.homeScrollPreview)
        }
    }

    override fun onBindContent(
        holder: ViewHolderState<Any>,
        item: LoadResponse,
        position: Int,
    ) {
        val binding = holder.view
        val posterUrl = item.backgroundPosterUrl ?: item.posterUrl

        when (binding) {
            is HomeScrollViewBinding -> {
                binding.homeScrollPreview.loadImage(posterUrl, item.posterHeaders)

                binding.homeScrollPreviewTags.apply {
                    text = item.tags?.joinToString(" • ") ?: ""
                    isGone = item.tags.isNullOrEmpty()
                    maxLines = 1
                }

                binding.homeScrollPreviewTitle.text = item.name.html()
                binding.homePreviewScore.isGone = true

                binding.homePreviewYear.text = item.year?.toString() ?: ""
                binding.homePreviewYear.isGone = item.year == null

                val duration = item.duration?.takeIf { it > 0 }
                binding.homePreviewDuration.text = duration?.let {
                    binding.homePreviewDuration.context.getString(
                        com.lagradost.cloudstream3.R.string.duration_format,
                        it
                    )
                } ?: ""
                binding.homePreviewDuration.isGone = duration == null

                val rating = item.score?.toString()
                binding.homePreviewRating.text = rating ?: ""
                binding.homePreviewRating.isGone = rating.isNullOrBlank()

                val hasYear = item.year != null
                val hasDuration = duration != null
                val hasRating = !rating.isNullOrBlank()
                binding.homePreviewYearSeparator.isVisible = hasYear && (hasDuration || hasRating)
                binding.homePreviewDurationSeparator.isVisible = hasDuration && hasRating
                binding.homePreviewRatingSeparator.isGone = true

                val plot = item.plot?.html()?.trim()
                binding.homePreviewDescription.text = plot ?: ""
                binding.homePreviewDescription.isGone = plot.isNullOrBlank()

                bindLogo(
                    url = item.logoUrl,
                    headers = item.posterHeaders,
                    titleView = binding.homeScrollPreviewTitle,
                    logoView = binding.homePreviewLogo
                )
            }

            is HomeScrollViewTvBinding -> {
                binding.homeScrollPreview.isFocusable = false
                binding.homeScrollPreview.setOnClickListener { view ->
                    callback.invoke(view ?: return@setOnClickListener, position, item)
                }
                binding.homeScrollPreview.loadImage(posterUrl, item.posterHeaders)
            }
        }
    }
}
