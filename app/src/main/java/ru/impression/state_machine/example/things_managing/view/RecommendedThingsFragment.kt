package ru.impression.state_machine.example.things_managing.view

import ru.impression.state_machine.example.things_managing.view.ThingsFragment

class RecommendedThingsFragment : ThingsFragment() {
    override val thingsListAdapterData: List<String>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    companion object {
        fun newInstance() = RecommendedThingsFragment()
    }
}