package ru.test.points.base.image

import android.content.Context
import android.util.DisplayMetrics
import com.bumptech.glide.Glide
import ru.test.points.R

//Обертка для получения url картинки партнера на основе разрешения экрана
object GlideWrapper {

    private val staticBaseUrl = "https://static.tinkoff.ru/icons/deposition-partners-v3"

    fun load(context: Context, partnerName: String) =
        Glide
            .with(context)
            .load("$staticBaseUrl/${getDensityString(context)}/$partnerName")
            .placeholder(R.drawable.bg_palceholder)
            .error(R.drawable.bg_palceholder)
            .centerCrop()

    private fun getDensityString(context: Context) =
        when (context.resources.displayMetrics.density) {
            0.75F, 1F -> "mdpi"
            1.5F -> "hdpi"
            2.0F -> "xhdpi"
            else -> "xxhdpi"
        }
}