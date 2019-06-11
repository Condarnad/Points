package ru.test.points.ui.activities.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    }

    override fun renderPointInfo(depositionPointFullInfo: DepositionPointFullInfo) {
        details_collapsing_toolbar.title = depositionPointFullInfo.partner?.name.orEmpty()

        GlideWrapper
            .load(this, depositionPointFullInfo.partner?.picture.orEmpty())
            .into(details_appbar_image)
    }

    companion object {
        fun prepareIntent(context: Context, detailsPointFullInfo: DepositionPointFullInfo) =
            Intent(context, DetailsActivity::class.java)
                .putExtra(Extra.FIRST, detailsPointFullInfo)
    }
}