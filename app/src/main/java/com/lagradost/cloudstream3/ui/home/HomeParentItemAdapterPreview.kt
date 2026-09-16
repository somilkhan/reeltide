package com.lagradost.cloudstream3.ui.home

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.navigation.NavigationBarItemView
import com.lagradost.cloudstream3.CloudStreamApp.Companion.getActivity
import com.lagradost.cloudstream3.CommonActivity.activity
import com.lagradost.cloudstream3.HomePageList
import com.lagradost.cloudstream3.LoadResponse
import com.lagradost.cloudstream3.MainActivity
import com.lagradost.cloudstream3.R
import com.lagradost.cloudstream3.SearchResponse
import com.lagradost.cloudstream3.databinding.FragmentHomeHeadBinding
import com.lagradost.cloudstream3.databinding.FragmentHomeHeadTvBinding
import com.lagradost.cloudstream3.mvvm.Resource
import com.lagradost.cloudstream3.mvvm.debugException
import com.lagradost.cloudstream3.mvvm.logError
import com.lagradost.cloudstream3.mvvm.observe
import com.lagradost.cloudstream3.ui.ViewHolderState
import com.lagradost.cloudstream3.ui.WatchType
import com.lagradost.cloudstream3.ui.account.AccountHelper.showAccountEditDialog
import com.lagradost.cloudstream3.ui.account.AccountHelper.showAccountSelectLinear
import com.lagradost.cloudstream3.ui.result.FOCUS_SELF
import com.lagradost.cloudstream3.ui.result.START_ACTION_RESUME_LATEST
import com.lagradost.cloudstream3.ui.result.getId
import com.lagradost.cloudstream3.ui.result.setLinearListLayout
import com.lagradost.cloudstream3.ui.search.SEARCH_ACTION_LOAD
import com.lagradost.cloudstream3.ui.search.SEARCH_ACTION_SHOW_METADATA
import com.lagradost.cloudstream3.ui.search.SearchClickCallback
import com.lagradost.cloudstream3.ui.settings.Globals.EMULATOR
import com.lagradost.cloudstream3.ui.settings.Globals.TV
import com.lagradost.cloudstream3.ui.settings.Globals.isLayout
import com.lagradost.cloudstream3.utils.AppContextUtils.html
import com.lagradost.cloudstream3.utils.AppContextUtils.setDefaultFocus
import com.lagradost.cloudstream3.utils.DataStoreHelper
import com.lagradost.cloudstream3.utils.ImageLoader.loadImage
import com.lagradost.cloudstream3.utils.SingleSelectionHelper.showOptionSelectStringRes
import com.lagradost.cloudstream3.utils.UIHelper.fixPaddingStatusbarMargin
import com.lagradost.cloudstream3.utils.UIHelper.fixPaddingStatusbarView
import com.lagradost.cloudstream3.utils.UIHelper.populateChips
import com.lagradost.cloudstream3.ui.setRecycledViewPool
import androidx.core.graphics.toColorInt

class HomeParentItemAdapterPreview(
    private val viewModel: HomeViewModel,
    private val accountViewModel: AccountViewModel
) : ParentItemAdapter(
    id = "HomeParentItemAdapterPreview".hashCode(),
    clickCallback = {
        viewModel.click(it)
    }, moreInfoClickCallback = {
        viewModel.popup(it)
    }, expandCallback = {
        viewModel.expand(it)
    }) {
    override val headers = 1
    override fun onCreateHeader(parent: ViewGroup): ViewHolderState<Bundle> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = if (isLayout(TV or EMULATOR)) FragmentHomeHeadTvBinding.inflate(
            inflater,
            parent,
            false
        ) else FragmentHomeHeadBinding.inflate(inflater, parent, false)

        if (binding is FragmentHomeHeadTvBinding && isLayout(EMULATOR)) {
            binding.homeBookmarkParentItemMoreInfo.isVisible = true

            val marginInDp = 50
            val density = binding.horizontalScrollChips.context.resources.displayMetrics.density
            val marginInPixels = (marginInDp * density).toInt()

            val params = binding.horizontalScrollChips.layoutParams as ViewGroup.MarginLayoutParams
            params.marginEnd = marginInPixels
            binding.horizontalScrollChips.layoutParams = params
            binding.homeWatchParentItemTitle.setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                ContextCompat.getDrawable(
                    parent.context,
                    R.drawable.ic_baseline_arrow_forward_24
                ),
                null
            )
        }

        return HeaderViewHolder(binding, viewModel, accountViewModel)
    }

    override fun onBindHeader(holder: ViewHolderState<Bundle>) {
        (holder as? HeaderViewHolder)?.bind()
    }

    override fun onViewDetachedFromWindow(holder: ViewHolderState<Bundle>) {
        when (holder) {
            is HeaderViewHolder -> holder.onViewDetachedFromWindow()
        }
    }

    override fun onViewAttachedToWindow(holder: ViewHolderState<Bundle>) {
        when (holder) {
            is HeaderViewHolder -> holder.onViewAttachedToWindow()
        }
    }

    private class HeaderViewHolder(
        val binding: ViewBinding,
        val viewModel: HomeViewModel,
        accountViewModel: AccountViewModel,
    ) : ViewHolderState<Bundle>(binding) {

        override fun save(): Bundle = Bundle().apply {
            putParcelable("resumeRecyclerView", resumeRecyclerView.layoutManager?.onSaveInstanceState())
            putParcelable("bookmarkRecyclerView", bookmarkRecyclerView.layoutManager?.onSaveInstanceState())
        }

        override fun restore(state: Bundle) {
            state.getSafeParcelable<Parcelable>("resumeRecyclerView")?.let { resumeRecyclerView.layoutManager?.onRestoreInstanceState(it) }
            state.getSafeParcelable<Parcelable>("bookmarkRecyclerView")?.let { bookmarkRecyclerView.layoutManager?.onRestoreInstanceState(it) }
        }

        val previewAdapter = HomeScrollAdapter { view, position, item ->
            viewModel.click(LoadClickCallback(0, view, position, item))
        }

        private val resumeAdapter = ResumeItemAdapter(
            nextFocusUp = itemView.nextFocusUpId,
            nextFocusDown = itemView.nextFocusDownId,
            removeCallback = { v ->
                try {
                    val context = v.context ?: return@ResumeItemAdapter
                    AlertDialog.Builder(context).apply {
                        setTitle(R.string.clear_history)
                        setMessage(context.getString(R.string.delete_message).format(context.getString(R.string.continue_watching)))
                        setNegativeButton(R.string.cancel) { _, _ -> }
                        setPositiveButton(R.string.delete) { _, _ ->
                            DataStoreHelper.deleteAllResumeStateIds()
                            viewModel.reloadStored()
                        }
                        show().setDefaultFocus()
                    }
                } catch (t: Throwable) {
                    logError(t)
                }
            },
            clickCallback = { callback ->
                if (callback.action != SEARCH_ACTION_SHOW_METADATA) {
                    viewModel.click(callback)
                    return@ResumeItemAdapter
                }
                callback.view.context?.getActivity()?.showOptionSelectStringRes(
                    callback.view,
                    callback.card.posterUrl,
                    listOf(R.string.action_open_watching, R.string.action_remove_watching),
                    listOf(R.string.action_open_play, R.string.action_open_watching, R.string.action_remove_watching)
                ) { (isTv, actionId) ->
                    when (actionId + if (isTv) 0 else 1) {
                        0 -> viewModel.click(SearchClickCallback(START_ACTION_RESUME_LATEST, callback.view, -1, callback.card))
                        1 -> viewModel.click(SearchClickCallback(SEARCH_ACTION_LOAD, callback.view, -1, callback.card))
                        2 -> {
                            if (callback.card is DataStoreHelper.ResumeWatchingResult) {
                                DataStoreHelper.removeLastWatched(callback.card.parentId)
                                viewModel.reloadStored()
                            }
                        }
                    }
                }
            })

        private val bookmarkAdapter = HomeChildItemAdapter(
            id = "bookmarkAdapter".hashCode(),
            nextFocusUp = itemView.nextFocusUpId,
            nextFocusDown = itemView.nextFocusDownId
        ) { callback ->
            if (callback.action != SEARCH_ACTION_SHOW_METADATA) {
                viewModel.click(callback)
                return@HomeChildItemAdapter
            }
            (callback.view.context?.getActivity() as? MainActivity)?.loadPopup(callback.card, load = false)
        }

        private val previewViewpager: ViewPager2 = itemView.findViewById(R.id.home_preview_viewpager)
        private val previewViewpagerText: ViewGroup = itemView.findViewById(R.id.home_preview_viewpager_text)
        private val resumeHolder: View = itemView.findViewById(R.id.home_watch_holder)
        private val resumeRecyclerView: RecyclerView = itemView.findViewById(R.id.home_watch_child_recyclerview)
        private val bookmarkHolder: View = itemView.findViewById(R.id.home_bookmarked_holder)
        private val bookmarkRecyclerView: RecyclerView = itemView.findViewById(R.id.home_bookmarked_child_recyclerview)
        private val headProfilePic: ImageView? = itemView.findViewById(R.id.home_head_profile_pic)
        private val headProfilePicCard: View? = itemView.findViewById(R.id.home_head_profile_padding)
        private val alternateHeadProfilePic: ImageView? = itemView.findViewById(R.id.alternate_home_head_profile_pic)
        private val alternateHeadProfilePicCard: View? = itemView.findViewById(R.id.alternate_home_head_profile_padding)
        private val topPadding: View? = itemView.findViewById(R.id.home_padding)
        private val alternativeAccountPadding: View? = itemView.findViewById(R.id.alternative_account_padding)
        private val homeNonePadding: View = itemView.findViewById(R.id.home_none_padding)

        private fun configurePhoneHeroActions(binding: FragmentHomeHeadBinding) {
            // The hero has exactly two actions: primary play and navigation to details.
            // Bookmark/save belongs to the details model and is intentionally absent here.
            binding.homePreviewBookmark.isGone = true

            val density = binding.root.resources.displayMetrics.density
            val circleSize = (56f * density).toInt()
            val detailsWidth = (116f * density).toInt()
            val gap = (10f * density).toInt()

            binding.homePreviewPlay.apply {
                text = ""
                icon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_play_arrow_24)
                iconTint = ColorStateList.valueOf(Color.BLACK)
                iconSize = (24f * density).toInt()
                iconPadding = 0
                insetTop = 0
                insetBottom = 0
                cornerRadius = (circleSize / 2)
                backgroundTintList = ColorStateList.valueOf(Color.WHITE)
                layoutParams = layoutParams.apply {
                    width = circleSize
                    height = circleSize
                    marginEnd = gap
                }
                contentDescription = context.getString(R.string.home_play)
            }

            binding.homePreviewInfo.apply {
                text = "Details"
                setTextColor(Color.WHITE)
                gravity = android.view.Gravity.CENTER
                compoundDrawablePadding = (8f * density).toInt()
                setCompoundDrawablesWithIntrinsicBounds(
                    ContextCompat.getDrawable(context, R.drawable.ic_outline_info_24),
                    null,
                    null,
                    null
                )
                background = ContextCompat.getDrawable(context, R.drawable/home_action_pill)
                layoutParams = layoutParams.apply {
                    width = detailsWidth
                    height = circleSize
                }
                contentDescription = context.getString(R.string.home_more_info)
            }
        }

        fun onSelect(item: LoadResponse, position: Int) {
            (binding as? FragmentHomeHeadTvBinding)?.apply {
                homePreviewDescription.isGone = item.plot.isNullOrBlank()
                homePreviewDescription.text = item.plot?.html() ?: ""

                val scoreText = item.score?.toStringNull(0.1, 10, 1, false)
                scoreText?.let { score ->
                    homePreviewScore.text = homePreviewScore.context.getString(R.string.extension_rating, score)
                    val rating = score.toDoubleOrNull() ?: item.score?.toDouble() ?: 0.0
                    val color = when {
                        rating < 5.0 -> "#eb2f2f".toColorInt()
                        rating < 8.0 -> "#eda009".toColorInt()
                        else -> "#3bb33b".toColorInt()
                    }
                    homePreviewScore.backgroundTintList = ColorStateList.valueOf(color)
                }
                homePreviewScore.isGone = scoreText == null
                item.year?.let { year -> homePreviewYear.text = year.toString() }
                homePreviewYear.isGone = item.year == null
                val duration = item.duration
                duration?.let { min -> homePreviewDuration.text = homePreviewDuration.context.getString(R.string.duration_format, min) }
                homePreviewDuration.isGone = duration == null || duration <= 0
                val castText = item.actors?.take(3)?.joinToString(", ") { it.actor.name }
                if (!castText.isNullOrBlank()) {
                    homePreviewCast.text = homePreviewCast.context.getString(R.string.cast_format, castText)
                    homePreviewCast.isVisible = true
                } else homePreviewCast.isVisible = false
                homePreviewText.text = item.name.html()
                populateChips(homePreviewTags, item.tags?.take(6) ?: emptyList(), R.style.ChipFilledSemiTransparent, null)
                bindLogo(url = item.logoUrl, headers = item.posterHeaders, titleView = homePreviewText, logoView = homeBackgroundPosterWatermarkBadgeHolder)
                homePreviewTags.isGone = item.tags.isNullOrEmpty()
                homePreviewInfoBtt.setOnClickListener { view -> viewModel.click(LoadClickCallback(0, view, position, item)) }
            }

            (binding as? FragmentHomeHeadBinding)?.apply {
                configurePhoneHeroActions(this)
                homePreviewPlay.setOnClickListener { view ->
                    viewModel.click(LoadClickCallback(START_ACTION_RESUME_LATEST, view, position, item))
                }
                homePreviewInfo.setOnClickListener { view ->
                    viewModel.click(LoadClickCallback(0, view, position, item))
                }
            }
        }

        private val previewCallback: ViewPager2.OnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                previewAdapter.apply {
                    if (position >= itemCount - 1 && hasMoreItems) {
                        hasMoreItems = false
                        viewModel.loadMoreHomeScrollResponses()
                    }
                }
                previewAdapter.getItemOrNull(position)?.let { onSelect(it, position) }
            }
        }

        fun onViewDetachedFromWindow() {
            previewViewpager.unregisterOnPageChangeCallback(previewCallback)
        }

        private val toggleList = listOf(
            Pair(itemView.findViewById<Chip>(R.id.home_type_watching_btt), WatchType.WATCHING),
            Pair(itemView.findViewById<Chip>(R.id.home_type_completed_btt), WatchType.COMPLETED),
            Pair(itemView.findViewById<Chip>(R.id.home_type_dropped_btt), WatchType.DROPPED),
            Pair(itemView.findViewById<Chip>(R.id.home_type_on_hold_btt), WatchType.ONHOLD),
            Pair(itemView.findViewById<Chip>(R.id.home_plan_to_watch_btt), WatchType.PLANTOWATCH),
        )

        private val toggleListHolder: ChipGroup? = itemView.findViewById(R.id.home_type_holder)

        fun bind() = Unit

        init {
            previewViewpager.setPageTransformer(HomeScrollTransformer())
            previewViewpager.adapter = previewAdapter
            resumeRecyclerView.adapter = resumeAdapter
            bookmarkRecyclerView.setRecycledViewPool(HomeChildItemAdapter.sharedPool)
            bookmarkRecyclerView.adapter = bookmarkAdapter
            resumeRecyclerView.setLinearListLayout(nextLeft = R.id.nav_rail_view, nextRight = FOCUS_SELF)
            bookmarkRecyclerView.setLinearListLayout(nextLeft = R.id.nav_rail_view, nextRight = FOCUS_SELF)
            fixPaddingStatusbarMargin(topPadding)

            for ((chip, watch) in toggleList) {
                chip.isChecked = false
                chip.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) viewModel.loadStoredData(setOf(watch))
                    else if (toggleList.all { !it.first.isChecked }) viewModel.loadStoredData(emptySet())
                }
            }

            headProfilePicCard?.isGone = isLayout(TV or EMULATOR)
            alternateHeadProfilePicCard?.isGone = isLayout(TV or EMULATOR)
            (headProfilePic ?: alternateHeadProfilePic)?.observe(viewModel.currentAccount) { currentAccount ->
                headProfilePic?.loadImage(currentAccount?.image)
                alternateHeadProfilePic?.loadImage(currentAccount?.image)
            }
            headProfilePicCard?.setOnClickListener { activity?.showAccountSelectLinear() }

            fun showAccountEditBox(context: Context): Boolean {
                val currentAccount = DataStoreHelper.getCurrentAccount()
                return if (currentAccount != null) {
                    showAccountEditDialog(
                        context = context,
                        account = currentAccount,
                        isNewAccount = false,
                        accountEditCallback = { accountViewModel.handleAccountUpdate(it, context) },
                        accountDeleteCallback = { accountViewModel.handleAccountDelete(it, context) }
                    )
                    true
                } else false
            }

            alternateHeadProfilePicCard?.setOnLongClickListener { showAccountEditBox(it.context) }
            headProfilePicCard?.setOnLongClickListener { showAccountEditBox(it.context) }
            alternateHeadProfilePicCard?.setOnClickListener { activity?.showAccountSelectLinear() }

            (binding as? FragmentHomeHeadTvBinding)?.apply {
                var lastFocusTimeoutMs = 0L
                homePreviewInfoBtt.setOnFocusChangeListener { view, hasFocus ->
                    if (!hasFocus) return@setOnFocusChangeListener
                    val lastFocusMs = lastFocusTimeoutMs
                    lastFocusTimeoutMs = System.currentTimeMillis()
                    if (lastFocusMs + 500L < System.currentTimeMillis()) MainActivity.centerView(view)
                }
                homePreviewHiddenNextFocus.setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) return@setOnFocusChangeListener
                    previewViewpager.setCurrentItem(previewViewpager.currentItem + 1, true)
                    homePreviewInfoBtt.requestFocus()
                }
                homePreviewHiddenPrevFocus.setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) return@setOnFocusChangeListener
                    if (previewViewpager.currentItem <= 0) {
                        (activity as? MainActivity)?.binding?.navRailView?.findViewById<NavigationBarItemView>(R.id.navigation_home)?.requestFocus()
                    } else {
                        previewViewpager.setCurrentItem(previewViewpager.currentItem - 1, true)
                        binding.homePreviewInfoBtt.requestFocus()
                    }
                }
            }

            (binding as? FragmentHomeHeadBinding)?.apply {
                homeSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        viewModel.queryTextSubmit(query)
                        return true
                    }
                    override fun onQueryTextChange(newText: String): Boolean {
                        viewModel.queryTextChange(newText)
                        return true
                    }
                })
            }
        }

        private fun updatePreview(preview: Resource<Pair<Boolean, List<LoadResponse>>>) {
            if (preview is Resource.Success) {
                homeNonePadding.apply {
                    val params = layoutParams
                    params.height = 0
                    layoutParams = params
                }
            } else fixPaddingStatusbarView(homeNonePadding)

            val items = (preview as? Resource.Success)?.value?.second.orEmpty()
            val hasHeroContent = items.isNotEmpty()

            when (preview) {
                is Resource.Success -> {
                    previewAdapter.submitList(items)
                    previewAdapter.hasMoreItems = preview.value.first
                    previewViewpager.isVisible = hasHeroContent
                    previewViewpagerText.isVisible = hasHeroContent
                    alternativeAccountPadding?.isVisible = !hasHeroContent
                    (binding as? FragmentHomeHeadTvBinding)?.homePreviewInfoBtt?.isVisible = hasHeroContent

                    if (hasHeroContent) {
                        val currentPos = previewViewpager.currentItem.coerceIn(0, items.lastIndex)
                        previewViewpager.setCurrentItem(currentPos, false)
                        onSelect(items[currentPos], currentPos)
                    } else {
                        previewViewpager.setCurrentItem(0, false)
                    }
                }
                else -> {
                    previewAdapter.submitList(emptyList())
                    previewViewpager.setCurrentItem(0, false)
                    previewViewpager.isVisible = false
                    previewViewpagerText.isVisible = false
                    alternativeAccountPadding?.isVisible = true
                    (binding as? FragmentHomeHeadTvBinding)?.homePreviewInfoBtt?.isVisible = false
                }
            }
        }

        private fun updateResume(resumeWatching: List<SearchResponse>) {
            resumeHolder.isVisible = resumeWatching.isNotEmpty()
            resumeAdapter.submitList(resumeWatching)
            if (binding is FragmentHomeHeadBinding || binding is FragmentHomeHeadTvBinding && isLayout(EMULATOR)) {
                val title = (binding as? FragmentHomeHeadBinding)?.homeWatchParentItemTitle ?: (binding as? FragmentHomeHeadTvBinding)?.homeWatchParentItemTitle
                title?.setOnClickListener {
                    viewModel.popup(HomeViewModel.ExpandableHomepageList(HomePageList(title.text.toString(), resumeWatching, false), 1, false), deleteCallback = { viewModel.deleteResumeWatching() })
                }
            }
        }

        private fun updateBookmarks(data: Pair<Boolean, List<SearchResponse>>) {
            val (visible, list) = data
            bookmarkHolder.isVisible = visible
            bookmarkAdapter.submitList(list)
            if (binding is FragmentHomeHeadBinding || binding is FragmentHomeHeadTvBinding && isLayout(EMULATOR)) {
                val title = (binding as? FragmentHomeHeadBinding)?.homeBookmarkParentItemTitle ?: (binding as? FragmentHomeHeadTvBinding)?.homeBookmarkParentItemTitle
                title?.setOnClickListener {
                    val items = toggleList.map { it.first }.filter { it.isChecked }
                    if (items.isEmpty()) return@setOnClickListener
                    val textSum = items.mapNotNull { it.text }.joinToString()
                    viewModel.popup(HomeViewModel.ExpandableHomepageList(HomePageList(textSum, list, false), 1, false), deleteCallback = { viewModel.deleteBookmarks(list) })
                }
            }
        }

        fun onViewAttachedToWindow() {
            previewViewpager.registerOnPageChangeCallback(previewCallback)
            previewViewpager.apply {
                observe(viewModel.preview) { updatePreview(it) }
                observe(viewModel.resumeWatching) { updateResume(it) }
                observe(viewModel.bookmarks) { updateBookmarks(it) }
                observe(viewModel.availableWatchStatusTypes) { (checked, visible) ->
                    for ((chip, watch) in toggleList) chip.apply {
                        isVisible = visible.contains(watch)
                        isChecked = checked.contains(watch)
                    }
                    toggleListHolder?.isGone = visible.isEmpty()
                }
            }
        }
    }
}
