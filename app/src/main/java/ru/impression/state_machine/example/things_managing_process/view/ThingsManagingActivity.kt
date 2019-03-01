package ru.impression.state_machine.example.things_managing_process.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_things_managing.*
import ru.impression.state_machine.FlowManager
import ru.impression.state_machine.FlowPerformer
import ru.impression.state_machine.R
import ru.impression.state_machine.example.things_managing_process.ThingsManagingFlow

class ThingsManagingActivity : AppCompatActivity(),
    FlowPerformer<ThingsManagingFlow, ThingsManagingFlow.Event, ThingsManagingFlow.State> {

    override val flowClass = ThingsManagingFlow::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FlowManager.startFlow(ThingsManagingFlow())

        recommended_things_loader_button.setOnClickListener {
            makeEvent(ThingsManagingFlow.Event.RECOMMENDED_THINGS_REQUESTED)
        }
    }

    override fun onStateUpdated(oldState: ThingsManagingFlow.State, newState: ThingsManagingFlow.State) {
        when (newState) {
            ThingsManagingFlow.State.LOADING_FAVOURITE_THINGS -> showThingsLoadingFragment(R.id.top_container)
            ThingsManagingFlow.State.SHOWING_ONLY_FAVOURITE_THINGS -> showFavouriteThingsFragment()
            ThingsManagingFlow.State.LOADING_RECOMMENDED_THINGS -> showThingsLoadingFragment(R.id.bottom_container)
            ThingsManagingFlow.State.SHOWING_ALL_THINGS -> showRecommendedThingsFragment()
            else -> Unit
        }
    }

    private fun showThingsLoadingFragment(container: Int) =
        supportFragmentManager
            .beginTransaction()
            .add(container, ThingsLoadingFragment.newInstance())
            .commit()

    private fun showFavouriteThingsFragment() =
        supportFragmentManager
            .beginTransaction()
            .add(R.id.top_container, RecommendedThingsFragment.newInstance())
            .commit()

    private fun showRecommendedThingsFragment() =
        supportFragmentManager
            .beginTransaction()
            .add(R.id.bottom_container, RecommendedThingsFragment.newInstance())
            .commit()

}