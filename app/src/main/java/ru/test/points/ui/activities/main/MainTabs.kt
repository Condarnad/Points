package ru.test.points.ui.activities.main

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import ru.test.points.R
import ru.test.points.ui.fragments.list.ListFragment
import ru.test.points.ui.fragments.map.MapFragment

enum class MainTabs(val fragment: Class<out Fragment>, @StringRes val title: Int) {
    MAP(MapFragment::class.java, R.string.main_tab_map),
    LIST(ListFragment::class.java, R.string.main_tab_list)
}