package ru.impression.state_machine.example.things_managing.view

import android.os.Bundle
import android.support.v4.app.ListFragment
import android.view.View
import android.widget.ArrayAdapter
import ru.impression.state_machine.FlowPerformer
import ru.impression.state_machine.example.things_managing.ThingsManagingFlow

abstract class ThingsFragment : ListFragment(), FlowPerformer<ThingsManagingFlow> {

    override val flow = ThingsManagingFlow::class.java

    protected var adapterData: ArrayList<String> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachToFlow()
    }

    protected fun updateAdapter() {
        listAdapter = ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1, adapterData)
    }
}