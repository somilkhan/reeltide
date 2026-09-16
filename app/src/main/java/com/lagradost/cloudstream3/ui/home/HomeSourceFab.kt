package com.lagradost.cloudstream3.ui.home

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.lagradost.cloudstream3.R
import com.lagradost.cloudstream3.utils.AppContextUtils.filterProviderByPreferredMedia

/**
 * Keeps the Home source control user-facing when providers exist, while avoiding
 * an orphaned floating control when there are no installed/eligible providers.
 * The underlying provider value and click behavior remain owned by HomeFragment.
 */
class HomeSourceFab @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ExtendedFloatingActionButton(context, attrs, defStyleAttr) {

    private fun normalize(text: CharSequence?): CharSequence? {
        return if (text?.toString() == context.getString(R.string.none)) {
            context.getString(R.string.home_source)
        } else {
            text
        }
    }

    private fun hasSelectableProviders(): Boolean =
        context.filterProviderByPreferredMedia().isNotEmpty()

    override fun setText(text: CharSequence?, type: TextView.BufferType?) {
        super.setText(normalize(text), type)
    }

    override fun setVisibility(visibility: Int) {
        if (visibility == VISIBLE && !hasSelectableProviders()) {
            super.setVisibility(GONE)
        } else {
            super.setVisibility(visibility)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        post {
            isVisible = hasSelectableProviders()
        }
    }
}
