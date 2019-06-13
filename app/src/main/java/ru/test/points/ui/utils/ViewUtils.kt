package ru.test.points.ui.utils

import android.view.View
import android.widget.TextView

fun View.setVisibleOrGone(isVisibile: Boolean) {
    this.visibility =
        if (isVisibile) View.VISIBLE
        else View.GONE
}

fun TextView.setTextOrGone(text: String?) {
    setVisibleOrGone(!text.isNullOrEmpty())
    text?.let(this::setText)
}