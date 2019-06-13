package ru.test.points.ui.widget.twolinetextview

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_two_line_text.view.*
import ru.test.points.R

class TwoLineTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var firstLine: String? = null
    private var secondLine: String? = null

    init {
        FrameLayout.inflate(context, R.layout.view_two_line_text, this)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.TwoLineTextView,
            0, 0
        ).apply {

            try {
                firstLine = getString(R.styleable.TwoLineTextView_firstLine)
                secondLine = getString(R.styleable.TwoLineTextView_secondLine)
            } finally {
                recycle()
            }
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        updateView()
    }

    fun setFirstLine(text: String?) {
        firstLine = text
        updateView()
    }

    fun setSecondLine(text: String?) {
        secondLine = text
        updateView()
    }

    private fun updateView() {
        if (firstLine.isNullOrBlank() || secondLine.isNullOrBlank())
            visibility = View.GONE
        else {
            visibility = View.VISIBLE
            first_line.text = firstLine

            second_line.text =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    Html.fromHtml(secondLine!!, Html.FROM_HTML_MODE_LEGACY)
                else
                    Html.fromHtml(secondLine)

            second_line.movementMethod = LinkMovementMethod.getInstance()

        }
    }
}