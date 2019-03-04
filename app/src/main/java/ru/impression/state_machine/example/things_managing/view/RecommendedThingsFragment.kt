package ru.impression.state_machine.example.things_managing.view

import ru.impression.state_machine.example.things_managing.ThingsManagingFlow

class RecommendedThingsFragment : ThingsFragment() {

    override fun onNewState(oldState: ThingsManagingFlow.State?, newState: ThingsManagingFlow.State) {
        when (newState) {
            ThingsManagingFlow.State.SHOWING_FAVOURITE_AND_RECOMMENDED_THINGS -> updateAdapter()
            else -> Unit
        }
    }

    override val thingsListAdapterData: List<String>
        get() = model.recommendedThings

    companion object {
        fun newInstance() = RecommendedThingsFragment()
    }
}