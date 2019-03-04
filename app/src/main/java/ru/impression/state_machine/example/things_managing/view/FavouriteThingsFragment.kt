package ru.impression.state_machine.example.things_managing.view

import android.view.View
import android.widget.ListView
import ru.impression.state_machine.example.things_managing.ThingsManagingFlow

class FavouriteThingsFragment : ThingsFragment() {

    override fun onNewStateReceived(oldState: ThingsManagingFlow.State?, newState: ThingsManagingFlow.State) {
        when (newState) {
            ThingsManagingFlow.State.REFRESHING_FAVOURITE_THINGS -> {
                updateAdapter()
                makeEvent(ThingsManagingFlow.Event.FAVOURITE_THINGS_REFRESHED)
            }
            ThingsManagingFlow.State.REFRESHING_ALL_THINGS -> updateAdapter()
            else -> Unit
        }
    }

    override val thingsListAdapterData: ArrayList<String>
        get() = model.favouriteThings

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        model.unlikedFavouriteThing = thingsListAdapterData[position]
        makeEvent(ThingsManagingFlow.Event.FAVOURITE_THING_UNLIKED)
    }

    companion object {
        fun newInstance() = FavouriteThingsFragment()
    }
}