package ru.impression.state_machine.example.things_managing.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_things_managing.*
import ru.impression.state_machine.FlowManager
import ru.impression.state_machine.FlowPerformer
import ru.impression.state_machine.R
import ru.impression.state_machine.example.things_managing.ThingsManagingFlow

class ThingsManagingActivity : AppCompatActivity(),
    FlowPerformer<ThingsManagingFlow.Event, ThingsManagingFlow.State> {

    init {
        FlowManager.startFlow(ThingsManagingFlow::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        attachToFlow(ThingsManagingFlow::class.java)

        recommended_things_loader_button.setOnClickListener {
            makeEvent(ThingsManagingFlow.Event.RECOMMENDED_THINGS_REQUESTED)
        }
    }

    override fun onNewState(oldState: ThingsManagingFlow.State?, newState: ThingsManagingFlow.State) {
        when (newState) {
            ThingsManagingFlow.State.LOADING_FAVOURITE_THINGS -> showFragment(
                R.id.top_container,
                FavouriteThingsFragment.newInstance()
            )
           /* ThingsManagingFlow.State.LOADING_RECOMMENDED_THINGS -> showFragment(
                R.id.bottom_container,
                RecommendedThingsFragment.newInstance()
            )*/
            else -> Unit
        }
    }

    private fun showFragment(container: Int, fragment: Fragment) =
        supportFragmentManager
            .beginTransaction()
            .add(container, fragment)
            .commit()

    override fun finish() {
        FlowManager.finishFlow(ThingsManagingFlow::class.java)
        super.finish()
    }
}