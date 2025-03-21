package com.voxcom.noteitup

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat

class FilledButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {

    init {
        applyCustomStyle()
    }

    private fun applyCustomStyle() {
        setTextColor(ContextCompat.getColor(context, android.R.color.white))
        textSize = 16f
        isAllCaps = false
        isClickable = true
        isFocusable = true
        gravity = Gravity.CENTER // Ensure text is centered
    }
}