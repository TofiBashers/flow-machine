package ru.impression.state_machine.example.things_managing.view

import android.view.View
import android.widget.ListView
import ru.impression.state_machine.example.things_managing.ThingsManagingFlow

class FavouriteThingsFragment : ThingsFragment() {

    override val thingsListAdapterData: List<String>
        get() = model.favouriteThings

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        model.unlikedFavouriteThing = thingsListAdapterData[position]
        makeEvent(ThingsManagingFlow.Event.FAVOURITE_THING_UNLIKED)
    }

    companion object {
        fun newInstance() = FavouriteThingsFragment()
    }
}