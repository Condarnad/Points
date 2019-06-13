package ru.test.points.ui.fragments.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.support.AndroidSupportInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.fragment_list.*
import ru.test.points.R
import ru.test.points.base.androidx.MvpAppCompatFragment
import ru.test.points.model.points.DepositionPoint
import ru.test.points.model.common.GeoPoint
import ru.test.points.model.points.DepositionPointFullInfo
import ru.test.points.ui.activities.details.DetailsActivity
import ru.test.points.ui.widget.pointshortinfo.PointShortInfoAdapter
import javax.inject.Inject

class ListFragment : MvpAppCompatFragment(), ListFragmentView {

    val adapter = PointShortInfoAdapter { point, bundle ->
        startActivity(DetailsActivity.prepareIntent(context!!, point), bundle)
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: ListPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_list, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list_recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        list_recycler.adapter = adapter
    }

    override fun renderPoints(points: List<DepositionPointFullInfo>) {
        adapter.submitList(points)
    }

    override fun updatePoint(index: Int, newPoint: DepositionPointFullInfo) {
        adapter.notifyItemChanged(index,newPoint)
    }
}