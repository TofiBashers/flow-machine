package ru.impression.state_machine.example.things_managing.view

import ru.impression.state_machine.example.things_managing.ThingsManagingFlow

class FavouriteThingsFragment : ThingsFragment() {
    override val thingsListAdapterData: List<String>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun onNewState(oldState: ThingsManagingFlow.State?, newState: ThingsManagingFlow.State) {
    }

    companion object {
        fun newInstance() = FavouriteThingsFragment()
    }

}