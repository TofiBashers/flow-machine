package ru.impression.state_machine.example.things_managing.view.model

import android.arch.lifecycle.ViewModel
import ru.impression.state_machine.FlowPerformer
import ru.impression.state_machine.example.things_managing.ThingsManagingFlow
import kotlin.concurrent.thread

class ThingsManagingModel : ViewModel(), FlowPerformer<ThingsManagingFlow.Event, ThingsManagingFlow.State> {

    lateinit var favouriteThings: ArrayList<String>

    lateinit var recommendedThings: ArrayList<String>

    lateinit var favouriteThingToDelete: String

    lateinit var thingToMakeFavourite: String

    override fun onNewState(oldState: ThingsManagingFlow.State?, newState: ThingsManagingFlow.State) {
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