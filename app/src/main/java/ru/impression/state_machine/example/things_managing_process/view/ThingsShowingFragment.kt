package ru.impression.state_machine.example.things_managing_process.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.ListFragment
import android.view.View
import android.widget.ArrayAdapter
import ru.impression.state_machine.FlowPerformer
import ru.impression.state_machine.example.things_managing_process.ThingsManagingFlow
import ru.impression.state_machine.example.things_managing_process.view.model.ThingsManagingModel

abstract class ThingsShowingFragment : ListFragment(),
    FlowPerformer<ThingsManagingFlow, ThingsManagingFlow.Event, ThingsManagingFlow.State> {

    protected lateinit var model: ThingsManagingModel

    abstract val things: List<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProviders.of(activity!!).get(ThingsManagingModel::class.java)
        listAdapter = ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1, things)
    }
}