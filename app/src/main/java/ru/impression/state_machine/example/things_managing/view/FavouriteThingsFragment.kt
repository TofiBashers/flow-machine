package ru.impression.state_machine.example.things_managing.view

import android.view.View
import android.widget.ListView
import ru.impression.state_machine.example.things_managing.ThingsManagingFlow

class FavouriteThingsFragment : ThingsFragment() {

    override fun onNewStateReceived(oldState: ThingsManagingFlow.State?, newState: ThingsManagingFlow.State) {
        if (newState == ThingsManagingFlow.State.LOADING_FAVOURITE_THINGS ||
            newState == ThingsManagingFlow.State.DELETING_FAVOURITE_THING ||
            newState == ThingsManagingFlow.State.MAKING_THING_FAVOURITE
        ) removeAdapted()
        else if (oldState == ThingsManagingFlow.State.LOADING_FAVOURITE_THINGS ||
            oldState == ThingsManagingFlow.State.DELETING_FAVOURITE_THING ||
            oldState == ThingsManagingFlow.State.MAKING_THING_FAVOURITE
        ) updateAdapter()
    }

    override val thingsListAdapterData: List<String>
        get() = model.favouriteThings

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        model.favouriteThingToDelete = thingsListAdapterData[position]
        makeEvent(ThingsManagingFlow.Event.FAVOURITE_THING_UNLIKED)
    }

    companion object {
        fun newInstance() = FavouriteThingsFragment()
    }

}