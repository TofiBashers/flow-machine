package ru.impression.state_machine.example.things_managing.view.model

import android.arch.lifecycle.ViewModel
import ru.impression.state_machine.FlowPerformer
import ru.impression.state_machine.example.things_managing.ThingsManagingFlow
import kotlin.concurrent.thread

class ThingsManagingModel : ViewModel(),
    FlowPerformer<ThingsManagingFlow, ThingsManagingFlow.Event, ThingsManagingFlow.State> {

    override val flow = ThingsManagingFlow::class.java

    override fun onNewStateReceived(oldState: ThingsManagingFlow.State?, newState: ThingsManagingFlow.State) {
        when (newState) {
            ThingsManagingFlow.State.LOADING_FAVOURITE_THINGS -> loadFavouriteThings()
            ThingsManagingFlow.State.DELETING_FAVOURITE_THING -> deleteFavouriteThing()
            ThingsManagingFlow.State.LOADING_RECOMMENDED_THINGS -> loadRecommendedThings()
            ThingsManagingFlow.State.MAKING_THING_FAVOURITE -> makeThingFavourite()
            else -> Unit
        }
    }

    lateinit var favouriteThings: ArrayList<String>

    lateinit var recommendedThings: ArrayList<String>

    lateinit var favouriteThingToDelete: String

    lateinit var thingToMakeFavourite: String

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

    private fun loadRecommendedThings() = thread {
        Thread.sleep(1000)
        recommendedThings = arrayListOf("money", "black-jack", "cocaine")
        makeEvent(ThingsManagingFlow.Event.RECOMMENDED_THINGS_LOADED)
    }

    private fun makeThingFavourite() = thread {
        Thread.sleep(1000)
        favouriteThings.add(thingToMakeFavourite)
        makeEvent(ThingsManagingFlow.Event.THING_BECAME_FAVOURITE)
    }

}