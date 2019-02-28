package ru.impression.state_machine

import android.app.Application
import android.arch.lifecycle.AndroidViewModel

abstract class BusinessProcessViewModel(application: Application) : AndroidViewModel(application) {

    var primaryState: BusinessProcess.State =
        BusinessProcess.InitialState()

    var currentState: BusinessProcess.State =
        BusinessProcess.InitialState()

    abstract fun onStateUpdated(state: BusinessProcess.State)

    fun makeEvent(event: BusinessProcess.Event) {

    }

}