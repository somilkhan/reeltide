package com.lagradost.cloudstream3.ui.home

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton
import com.lagradost.cloudstream3.R

/**
 * Preserves the existing MaterialButton binding contract for the phone hero CTA.
 * Geometry is owned by fragment_home_head.xml / configurePhoneHeroActions().
 */
class HomeHeroActionButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : MaterialButton(context, attrs, defStyleAttr) {

    override fun setText(text: CharSequence?, type: BufferType?) {
        val value = if (id == R.id.home_preview_play && TextUtils.isEmpty(text)) "Play" else text
        super.setText(value, type)
    }
}
