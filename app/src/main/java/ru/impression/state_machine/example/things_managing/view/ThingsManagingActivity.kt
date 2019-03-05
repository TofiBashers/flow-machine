package ru.impression.state_machine.example.things_managing.view

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_things_managing.*
import ru.impression.state_machine.Flow
import ru.impression.state_machine.FlowManager
import ru.impression.state_machine.FlowPerformer
import ru.impression.state_machine.R
import ru.impression.state_machine.example.things_managing.*
import ru.impression.state_machine.example.things_managing.view.model.ThingsManagingModel

class ThingsManagingActivity : AppCompatActivity(), FlowPerformer<ThingsManagingFlow> {

    override val flow = ThingsManagingFlow::class.java

    private lateinit var model: ThingsManagingModel

    init {
        FlowManager.startFlow(flow)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_things_managing)
        model = ViewModelProviders
            .of(this, ViewModelProvider.NewInstanceFactory())
            .get(ThingsManagingModel::class.java)

        recommended_things_loader_button.setOnClickListener {
            if (it.tag == "SHOW") {
                it.tag = "HIDE"
                performEvent(RecommendedThingsRequested())
            } else {
                it.tag = "SHOW"
                performEvent(RecommendedThingsHideRequested())
            }
        }

        attachToFlow()
    }

    override fun performAction(action: Flow.Action) {
        when (action) {
            is LoadFavouriteThings -> showFragment(R.id.top_container, ThingsLoadingFragment.newInstance())
            is ShowFavouriteThings -> showFragment(R.id.top_container, FavouriteThingsFragment.newInstance())
            is LoadRecommendedThings, is RefreshRecommendedThings ->
                showFragment(R.id.bottom_container, ThingsLoadingFragment.newInstance())
            is ShowRecommendedThings -> showFragment(R.id.bottom_container, RecommendedThingsFragment.newInstance())
        }
    }

    private fun showFragment(container: Int, fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(container, fragment, fragment.javaClass.canonicalName)
            .commit()
    }

    override fun finish() {
        FlowManager.finishFlow(flow)
        super.finish()
    }
}