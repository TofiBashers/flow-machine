package ru.impression.state_machine.example.things_managing_process.view.model

import android.app.Application
import ru.impression.state_machine.BusinessProcessViewModel
import ru.impression.state_machine.example.things_managing_process.Event
import ru.impression.state_machine.example.things_managing_process.State
import kotlin.concurrent.thread

class ThingsManagingModel(application: Application) : BusinessProcessViewModel<Event, State>(application) {

    lateinit var favouriteThings: ArrayList<String>

    lateinit var thingToDelete: String

    override fun onStateUpdated() {
        when (currentState) {
            State.LOADING_FAVOURITE_THINGS -> loadFavouriteThings()
            State.DELETING_FAVOURITE_THING -> deleteFavouriteThing()
            else -> Unit
        }
    }

    private fun loadFavouriteThings() = thread {
        Thread.sleep(1000)
        favouriteThings = arrayListOf("vodka", "spice", "bitches")
        makeEvent(Event.FAVOURITE_THINGS_LOADED)
    }

    private fun deleteFavouriteThing() = thread {
        Thread.sleep(1000)
        favouriteThings.remove(thingToDelete)
        makeEvent(Event.FAVOURITE_THING_DELETED)
    }

}