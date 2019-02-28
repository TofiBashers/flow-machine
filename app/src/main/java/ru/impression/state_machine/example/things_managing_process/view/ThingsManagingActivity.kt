package ru.impression.state_machine.example.things_managing_process.view

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_things_managing.*
import ru.impression.state_machine.BusinessProcessActivity
import ru.impression.state_machine.R
import ru.impression.state_machine.example.things_managing_process.Event
import ru.impression.state_machine.example.things_managing_process.State

class ThingsManagingActivity : BusinessProcessActivity<Event, State>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recommended_things_loader_button.setOnClickListener {
            makeEvent(Event.RECOMMENDED_THINGS_REQUESTED)
        }
    }

    override fun onStateUpdated(state: State) {
        when (state) {
            State.DISPLAY_ONLY_FAVOURITE_THINGS -> {
                showFavouriteThingsFragment()
                showRecommendedThingsLoaderButton()
            }
            State.DISPLAY_ALL_THINGS -> {
                showFavouriteThingsFragment()
                showRecommendedThingsFragment()
            }
            else -> Unit
        }
    }

    private fun showFavouriteThingsFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.top_container, RecommendedThingsFragment.newInstance())
            .commit()
    }

    private fun showRecommendedThingsLoaderButton() {
        recommended_things_loader_button.visibility = View.VISIBLE
    }

    private fun showRecommendedThingsFragment() =
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.bottom_container, RecommendedThingsFragment.newInstance())
            .commit()

}