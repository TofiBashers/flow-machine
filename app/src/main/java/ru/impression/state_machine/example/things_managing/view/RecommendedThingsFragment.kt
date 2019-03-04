package ru.impression.state_machine.example.things_managing.view

import android.view.View
import android.widget.ListView
import ru.impression.state_machine.example.things_managing.ThingsManagingFlow

class RecommendedThingsFragment : ThingsFragment() {

    override fun onNewStateReceived(oldState: ThingsManagingFlow.State?, newState: ThingsManagingFlow.State) {
        if (newState == ThingsManagingFlow.State.LOADING_RECOMMENDED_THINGS ||
            newState == ThingsManagingFlow.State.MAKING_THING_FAVOURITE
        ) removeAdapted()
        else if (oldState == ThingsManagingFlow.State.LOADING_RECOMMENDED_THINGS ||
            oldState == ThingsManagingFlow.State.MAKING_THING_FAVOURITE
        ) updateAdapter()
    }

    override val thingsListAdapterData: List<String>
        get() = model.recommendedThings

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        model.thingToMakeFavourite = thingsListAdapterData[position]
        makeEvent(ThingsManagingFlow.Event.RECOMMENDED_THING_LIKED)
    }

    companion object {
        fun newInstance() = RecommendedThingsFragment()
    }

}