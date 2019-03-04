package ru.impression.state_machine.example.things_managing.view.model

import android.arch.lifecycle.ViewModel
import com.maximeroussy.invitrode.WordGenerator
import ru.impression.state_machine.FlowPerformer
import ru.impression.state_machine.example.things_managing.ThingsManagingFlow
import java.util.*
import kotlin.concurrent.thread

class ThingsManagingModel : ViewModel(),
    FlowPerformer<ThingsManagingFlow, ThingsManagingFlow.Event, ThingsManagingFlow.State> {

    override val flow = ThingsManagingFlow::class.java

    init {
        attachToFlow()
    }

    override fun onNewStateReceived(oldState: ThingsManagingFlow.State?, newState: ThingsManagingFlow.State) {
        when (newState) {
            ThingsManagingFlow.State.LOADING_FAVOURITE_THINGS -> loadFavouriteThings()
            ThingsManagingFlow.State.ADDING_FAVOURITE_THING -> addFavouriteThing()
            ThingsManagingFlow.State.REMOVING_FAVOURITE_THING -> removeFavouriteThing()
            ThingsManagingFlow.State.LOADING_RECOMMENDED_THINGS -> loadRecommendedThings()
            ThingsManagingFlow.State.REFRESHING_ALL_THINGS -> refreshRecommendedThings()
            else -> Unit
        }
    }

    lateinit var favouriteThings: ArrayList<String>

    lateinit var unlikedFavouriteThing: String

    lateinit var recommendedThings: ArrayList<String>

    lateinit var likedRecommendedThing: String

    private fun loadFavouriteThings() = thread {
        Thread.sleep(1000)
        favouriteThings = arrayListOf("vodka", "spice", "bitches", "money", "black-jack", "cocaine")
        makeEvent(ThingsManagingFlow.Event.FAVOURITE_THINGS_LOADED)
    }

    private fun addFavouriteThing() = thread {
        favouriteThings.add(likedRecommendedThing)
        makeEvent(ThingsManagingFlow.Event.FAVOURITE_THING_ADDED)
    }

    private fun removeFavouriteThing() = thread {
        favouriteThings.remove(unlikedFavouriteThing)
        makeEvent(ThingsManagingFlow.Event.FAVOURITE_THING_REMOVED)
    }

    private fun loadRecommendedThings() = thread {
        Thread.sleep(1000)
        recommendedThings = ArrayList()
        for (i in 0..6) {
            recommendedThings.add(WordGenerator().newWord(Random().nextInt(10) + 3))
        }
        makeEvent(ThingsManagingFlow.Event.RECOMMENDED_THINGS_LOADED)
    }

    private fun refreshRecommendedThings() = thread {
        Thread.sleep(1000)
        recommendedThings = ArrayList()
        for (i in 0..6) {
            recommendedThings.add(WordGenerator().newWord(Random().nextInt(10) + 3))
        }
        makeEvent(ThingsManagingFlow.Event.ALL_THINGS_REFRESHED)
    }
}