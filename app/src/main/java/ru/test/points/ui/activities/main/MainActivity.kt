package ru.test.points.ui.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import ru.test.points.R
import ru.test.points.base.dagger.scope.ActivityScope
import javax.inject.Inject
import android.os.Build
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionSet
import android.view.Gravity
import androidx.transition.Fade


@ActivityScope
class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() =
        supportFragmentInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_tabs.setupWithViewPager(main_viewpager)
        main_viewpager.adapter = MainViewPagerAdapter(this, supportFragmentManager)

        configTransition()
    }

    private fun configTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val transitionSet = android.transition.Fade()
            transitionSet.excludeTarget(android.R.id.statusBarBackground, true)
            transitionSet.excludeTarget(android.R.id.navigationBarBackground, true)

            window.enterTransition = transitionSet
            window.exitTransition = transitionSet
        }
    }

    override fun onDestroy() {
        main_viewpager.adapter = null
        super.onDestroy()
    }
}

