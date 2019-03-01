package ru.impression.state_machine.example.things_managing_process.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import ru.impression.state_machine.FlowPerformer
import ru.impression.state_machine.R
import ru.impression.state_machine.example.things_managing_process.ThingsManagingFlow
import ru.impression.state_machine.example.things_managing_process.view.model.ThingsManagingModel

class FavouriteThingsFragment : Fragment(),
    FlowPerformer<ThingsManagingFlow, ThingsManagingFlow.Event, ThingsManagingFlow.State>() {
    override val flowClass: Class<ThingsManagingFlow>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override val businessProcessActivity: AppCompatActivity get() = activity

    private lateinit var model: ThingsManagingModel
    private lateinit var favouriteThingsListView: ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        View.inflate(context, R.layout.fragment_favourite_things, null).also {
            favouriteThingsListView = it.findViewById(R.id.favourite_things_list_view)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ThingsAdapter(context!!).apply {
            addAll(model.favouriteThings)
        }
        favouriteThingsListView.adapter = adapter
        favouriteThingsListView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            adapter.getItem(position)!!
            makeEvent(ThingsManagingFlow.Event.FAVOURITE_THING_UNLIKED)
        }
    }

    override fun onStateUpdated(oldState: ThingsManagingFlow.State, newState: ThingsManagingFlow.State) {

    }

    companion object {
        fun newInstance() = FavouriteThingsFragment()
    }

}