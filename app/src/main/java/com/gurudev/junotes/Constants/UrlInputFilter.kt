package com.gurudev.junotes.Constants

import android.text.InputFilter
import android.text.Spanned
import android.util.Patterns

class UrlInputFilter : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val newText = dest?.subSequence(0, dstart).toString() + source?.subSequence(start, end).toString() + dest?.subSequence(dend, dest.length).toString()
        return if (Patterns.WEB_URL.matcher(newText).matches()) {
            null // Accept the input
        } else {
            ""
        }
    }
}