package ru.impression.state_machine.example.things_managing_process.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import ru.impression.state_machine.BusinessProcess
import ru.impression.state_machine.BusinessProcessFragment
import ru.impression.state_machine.R
import ru.impression.state_machine.example.things_managing_process.event.FavouriteThingDeleted
import ru.impression.state_machine.example.things_managing_process.event.FavouriteThingUnliked
import ru.impression.state_machine.example.things_managing_process.state.DeletingFavouriteThing

private const val KEY_ITEMS = "ITEMS"

class FavouriteThingsFragment : BusinessProcessFragment() {

    private lateinit var favouriteThingsListView: ListView

    override fun onStateUpdated(state: BusinessProcess.State) {
        when (state) {
            is DeletingFavouriteThing -> deleteFavouriteThing(state.thing)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        View.inflate(context, R.layout.fragment_favourite_things, null).also {
            favouriteThingsListView = it.findViewById<ListView>(R.id.favourite_things_list_view).apply {
                val adapter = ThingsAdapter(context!!).apply {
                    addAll(arguments!!.getStringArrayList(KEY_ITEMS)!!)
                }
                this.adapter = adapter
                onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                    makeEvent(FavouriteThingUnliked(adapter.getItem(position)!!))
                }
            }
        }

    private fun deleteFavouriteThing(thing: String) {
        makeEvent(FavouriteThingDeleted())
    }

    companion object {
        fun newInstance() = FavouriteThingsFragment()
    }

}