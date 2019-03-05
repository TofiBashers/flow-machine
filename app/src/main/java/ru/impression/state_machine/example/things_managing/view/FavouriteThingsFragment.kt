package ru.impression.state_machine.example.things_managing.view

import android.view.View
import android.widget.ListView
import ru.impression.state_machine.Flow
import ru.impression.state_machine.example.things_managing.AddFavouriteThing
import ru.impression.state_machine.example.things_managing.FavouriteThingsUnliked
import ru.impression.state_machine.example.things_managing.RemoveFavouriteThing
import ru.impression.state_machine.example.things_managing.ShowFavouriteThings

class FavouriteThingsFragment : ThingsFragment() {
    override fun performAction(action: Flow.Action) {
        when (action) {
            is ShowFavouriteThings -> {
                adapterData.addAll(action.things)
                updateAdapter()
            }
            is RemoveFavouriteThing -> {
                adapterData.remove(action.thing)
                updateAdapter()
            }
            is AddFavouriteThing -> {
                adapterData.add(action.thing)
                updateAdapter()
            }
        }
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        performEvent(FavouriteThingsUnliked(adapterData[position]))
    }

    companion object {
        fun newInstance() = FavouriteThingsFragment()
    }
}