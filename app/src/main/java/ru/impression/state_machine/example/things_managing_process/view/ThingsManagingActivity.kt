package ru.impression.state_machine.example.things_managing_process.view

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_things_managing.*
import ru.impression.state_machine.*
import ru.impression.state_machine.example.things_managing_process.event.RecommendedThingsLoadRequested
import ru.impression.state_machine.example.things_managing_process.state.DisplayingAllThings
import ru.impression.state_machine.example.things_managing_process.state.DisplayingOnlyFavouriteThings
import ru.impression.state_machine.BusinessProcessActivity

class ThingsManagingActivity : BusinessProcessActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recommended_things_loader_button.setOnClickListener {
            makeEvent(RecommendedThingsLoadRequested())
        }
    }

    override fun onStateUpdated(state: BusinessProcess.State) {
        when (currentState) {
            is DisplayingOnlyFavouriteThings -> {
                showFavouriteThingsFragment()
                showRecommendedThingsLoaderButton()
            }
            is DisplayingAllThings -> {
                showFavouriteThingsFragment()
                showRecommendedThingsFragment()
            }
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