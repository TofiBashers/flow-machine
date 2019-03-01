package ru.impression.state_machine.example.things_managing_process.view.model

import android.app.Application
import ru.impression.state_machine.BusinessProcessViewModel
import ru.impression.state_machine.example.things_managing_process.ThingsManagingFlow
import kotlin.concurrent.thread

class ThingsManagingModel(application: Application) :
    BusinessProcessViewModel<ThingsManagingFlow.Event, ThingsManagingFlow.State>(application) {

    lateinit var favouriteThings: ArrayList<String>

    lateinit var favouriteThingToDelete: String

    override fun onStateUpdated(oldState: ThingsManagingFlow.State, newState: ThingsManagingFlow.State) {
        when (newState) {
            ThingsManagingFlow.State.LOADING_FAVOURITE_THINGS -> loadFavouriteThings()
            ThingsManagingFlow.State.DELETING_FAVOURITE_THING -> deleteFavouriteThing()
            else -> Unit
        }
    }

    private fun loadFavouriteThings() = thread {
        Thread.sleep(1000)
        favouriteThings = arrayListOf("vodka", "spice", "bitches")
        makeEvent(ThingsManagingFlow.Event.FAVOURITE_THINGS_LOADED)
    }

    private fun deleteFavouriteThing() = thread {
        Thread.sleep(1000)
        favouriteThings.remove(favouriteThingToDelete)
        makeEvent(ThingsManagingFlow.Event.FAVOURITE_THING_DELETED)
    }

}