package ru.impression.state_machine

import android.support.v4.app.Fragment

abstract class BusinessProcessFragment : Fragment() {
    var primaryState: BusinessProcess.State =
        BusinessProcess.InitialState()

    var currentState: BusinessProcess.State =
        BusinessProcess.InitialState()

    abstract fun onStateUpdated(state: BusinessProcess.State)

    fun makeEvent(event: BusinessProcess.Event) {

    }
}