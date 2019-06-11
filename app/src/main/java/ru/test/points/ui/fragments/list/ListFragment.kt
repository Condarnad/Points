package ru.test.points.ui.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_list.*
import ru.test.points.R
import ru.test.points.model.points.DepositionPoint
import ru.test.points.model.common.GeoPoint
import ru.test.points.ui.widget.pointshortinfo.PointShortInfoAdapter

class ListFragment : Fragment() {

    val adapter = PointShortInfoAdapter {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_list, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list_recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        list_recycler.adapter = adapter


        adapter.submitList(
            mutableListOf(
                DepositionPoint(
                    partnerName = "KEK",
                    location = GeoPoint(.0, .0),
                    workHours = "Пн-Пт 08:00-20:00, Сб 08:00-18:00, Вс - Вых.",
                    addressInfo = "Магазин Продукты на Кузнецком",
                    fullAddress = "г Москва, ул Кузнецкий Мост, д 9/10 стр 2",
                    phones = ""
                ),
                DepositionPoint(
                    partnerName = "KEK",
                    location = GeoPoint(.0, .0),
                    workHours = "Пн-Пт 08:00-20:00, Сб 08:00-18:00, Вс - Вых.",
                    addressInfo = "Магазин Продукты на Кузнецком",
                    fullAddress = "г Москва, ул Кузнецкий Мост, д 9/10 стр 2",
                    phones = ""
                ),
                DepositionPoint(
                    partnerName = "KEK",
                    location = GeoPoint(.0, .0),
                    workHours = "Пн-Пт 08:00-20:00, Сб 08:00-18:00, Вс - Вых.",
                    addressInfo = "Магазин Продукты на Кузнецком",
                    fullAddress = "г Москва, ул Кузнецкий Мост, д 9/10 стр 2",
                    phones = ""
                ),
                DepositionPoint(
                    partnerName = "KEK",
                    location = GeoPoint(.0, .0),
                    workHours = "Пн-Пт 08:00-20:00, Сб 08:00-18:00, Вс - Вых.",
                    addressInfo = "Магазин Продукты на Кузнецком",
                    fullAddress = "г Москва, ул Кузнецкий Мост, д 9/10 стр 2",
                    phones = ""
                ),
                DepositionPoint(
                    partnerName = "KEK",
                    location = GeoPoint(.0, .0),
                    workHours = "Пн-Пт 08:00-20:00, Сб 08:00-18:00, Вс - Вых.",
                    addressInfo = "Магазин Продукты на Кузнецком",
                    fullAddress = "г Москва, ул Кузнецкий Мост, д 9/10 стр 2",
                    phones = ""
                )
            )
        )
    }
}