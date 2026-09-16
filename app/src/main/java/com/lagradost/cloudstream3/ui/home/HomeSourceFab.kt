package com.lagradost.cloudstream3.ui.home

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.lagradost.cloudstream3.R

/**
 * Keeps the Home source control user-facing even when the provider sentinel is selected.
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

    override fun setText(text: CharSequence?, type: TextView.BufferType?) {
        super.setText(normalize(text), type)
    }

    override fun setText(text: CharSequence?) {
        super.setText(normalize(text))
    }
}
