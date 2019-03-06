package ru.impression.state_machine.example.things_managing.view

import android.view.View
import android.widget.ListView
import ru.impression.state_machine.Flow
import ru.impression.state_machine.example.things_managing.RecommendedThingsLiked
import ru.impression.state_machine.example.things_managing.ShowRecommendedThings

class RecommendedThingsManagingFragment : ThingsManagingFragment() {

    override fun performAction(action: Flow.Action) {
        when (action) {
            is ShowRecommendedThings -> {
                adapterData.addAll(action.things)
                updateAdapter()
            }
        }
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        onEvent(RecommendedThingsLiked(adapterData[position]))
    }

    companion object {
        fun newInstance() = RecommendedThingsManagingFragment()
    }
}