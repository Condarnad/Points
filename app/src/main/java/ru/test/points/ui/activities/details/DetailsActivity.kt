package ru.test.points.ui.activities.details

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionSet
import android.view.Gravity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_details.*
import ru.test.points.R
import ru.test.points.base.androidx.MvpAppCompatActivity
import ru.test.points.base.image.GlideWrapper
import ru.test.points.model.points.DepositionPointFullInfo
import ru.test.points.ui.utils.Extra
import javax.inject.Inject


class DetailsActivity : MvpAppCompatActivity(), DetailsView {


    @Inject
    @InjectPresenter
    lateinit var detailsPresenter: DetailsPresenter

    @ProvidePresenter
    fun providePresenter() = detailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_details)

        details_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        configTransition()
    }

    override fun renderPointInfo(depositionPointFullInfo: DepositionPointFullInfo) {
        details_partner_name.text = depositionPointFullInfo.partner?.name.orEmpty()

        details_address.setSecondLine(depositionPointFullInfo.depositionPoint.fullAddress)
        details_address_info.setSecondLine(depositionPointFullInfo.depositionPoint.addressInfo)
        details_phones.setSecondLine(depositionPointFullInfo.depositionPoint.phones?.let { "<a href=tel:$it>$it</a>" })
        details_work_hours.setSecondLine(depositionPointFullInfo.depositionPoint.workHours)

        details_duration.setSecondLine(depositionPointFullInfo.partner?.depositionDuration)
        details_description.setSecondLine(depositionPointFullInfo.partner?.description)
        details_limitations.setSecondLine(depositionPointFullInfo.partner?.limitations)
        details_point_type.setSecondLine(depositionPointFullInfo.partner?.pointType)
        details_url.setSecondLine(depositionPointFullInfo.partner?.url?.let { "<a href=$it>$it</a>" })
        details_momentary.setSecondLine(
            if (depositionPointFullInfo.partner?.isMomentary == true) "Да"
            else "Нет"
        )

        GlideWrapper
            .load(this, depositionPointFullInfo.partner?.picture.orEmpty())
            .circleCrop()
            .into(details_image)
    }

    private fun configTransition(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val transitionSet = android.transition.Fade()
            //transitionSet.excludeTarget(R.id.appbar, true)
            transitionSet.excludeTarget(android.R.id.statusBarBackground, true)
            transitionSet.excludeTarget(android.R.id.navigationBarBackground, true)

            window.enterTransition = transitionSet
            window.exitTransition = transitionSet
        }
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
        super.onBackPressed()
    }

    companion object {
        fun prepareIntent(context: Context, detailsPointFullInfo: DepositionPointFullInfo) =
            Intent(context, DetailsActivity::class.java)
                .putExtra(Extra.FIRST, detailsPointFullInfo)
    }
}