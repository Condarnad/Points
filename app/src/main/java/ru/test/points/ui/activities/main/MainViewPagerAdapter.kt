package ru.test.points.ui.activities.main

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MainViewPagerAdapter(val context: Context, fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int) =
        MainTabs.values()[position].fragment.newInstance()

    override fun getCount() =
        MainTabs.values().size

    override fun getPageTitle(position: Int) =
        context.getString(MainTabs.values()[position].title).orEmpty()
}