package ru.impression.state_machine.example.things_managing.view

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_things_managing.*
import ru.impression.state_machine.FlowManager
import ru.impression.state_machine.FlowPerformer
import ru.impression.state_machine.R
import ru.impression.state_machine.example.things_managing.ThingsManagingFlow
import ru.impression.state_machine.example.things_managing.view.model.ThingsManagingModel

class ThingsManagingActivity : AppCompatActivity(),
    FlowPerformer<ThingsManagingFlow, ThingsManagingFlow.Event, ThingsManagingFlow.State> {

    override val flow = ThingsManagingFlow::class.java

    lateinit var model: ThingsManagingModel

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
            makeEvent(ThingsManagingFlow.Event.RECOMMENDED_THINGS_REQUESTED)
        }

        attachToFlow()
    }

    override fun onNewStateReceived(oldState: ThingsManagingFlow.State?, newState: ThingsManagingFlow.State) {
        when (newState) {
            ThingsManagingFlow.State.LOADING_FAVOURITE_THINGS,
            ThingsManagingFlow.State.DELETING_FAVOURITE_THING -> showFragment(
                R.id.top_container,
                ThingsLoadingFragment.newInstance()
            )
            ThingsManagingFlow.State.LOADING_RECOMMENDED_THINGS -> showFragment(
                R.id.bottom_container,
                ThingsLoadingFragment.newInstance()
            )
            ThingsManagingFlow.State.MAKING_THING_FAVOURITE -> {
                showFragment(
                    R.id.top_container,
                    ThingsLoadingFragment.newInstance()
                )
                showFragment(
                    R.id.bottom_container,
                    ThingsLoadingFragment.newInstance()
                )
            }
            ThingsManagingFlow.State.SHOWING_ONLY_FAVOURITE_THINGS -> showFragment(
                R.id.top_container,
                FavouriteThingsFragment.newInstance()
            )
            ThingsManagingFlow.State.SHOWING_FAVOURITE_AND_RECOMMENDED_THINGS -> {
                showFragment(R.id.top_container, FavouriteThingsFragment.newInstance())
                showFragment(R.id.bottom_container, RecommendedThingsFragment.newInstance())
            }
        }
    }

    private fun showFragment(container: Int, fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(container, fragment, fragment.javaClass.canonicalName)
            .commit()
    }

    private fun <F : Fragment> removeFragment(fragmentClass: Class<F>) =
        supportFragmentManager
            .beginTransaction()
            .remove(supportFragmentManager.findFragmentByTag(fragmentClass.canonicalName)!!)
            .commit()

    override fun finish() {
        FlowManager.finishFlow(flow)
        super.finish()
    }
}