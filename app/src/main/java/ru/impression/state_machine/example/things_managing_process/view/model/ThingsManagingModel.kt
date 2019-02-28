package ru.impression.state_machine.example.things_managing_process.view.model

import android.app.Application
import ru.impression.state_machine.BusinessProcess
import ru.impression.state_machine.BusinessProcessViewModel
import ru.impression.state_machine.example.things_managing_process.event.FavouriteThingDeleted
import ru.impression.state_machine.example.things_managing_process.event.FavouriteThingsLoaded
import ru.impression.state_machine.example.things_managing_process.state.DeletingFavouriteThing
import ru.impression.state_machine.example.things_managing_process.state.LoadingFavouriteThings
import kotlin.concurrent.thread

class ThingsManagingModel(application: Application) : BusinessProcessViewModel(application) {

    override fun onStateUpdated(state: BusinessProcess.State) {
        when (state) {
            is LoadingFavouriteThings -> loadFavouriteThings()
            is DeletingFavouriteThing -> deleteFavouriteThing(state.thing)
        }
    }

    private fun loadFavouriteThings() = thread {
        Thread.sleep(1000)
        makeEvent(FavouriteThingsLoaded(listOf("vodka", "spice", "bitches")))
    }

    private fun deleteFavouriteThing(thing: String) = thread {
        Thread.sleep(1000)
    }

}