package ru.impression.state_machine

import android.app.Application
import android.arch.lifecycle.AndroidViewModel

abstract class BusinessProcessViewModel<E : Enum<E>, S : Enum<S>>(application: Application) :
    AndroidViewModel(application) {

    protected lateinit var primaryState: S

    protected lateinit var currentState: S

    abstract fun onStateUpdated()

    fun makeEvent(event: E) {

    }

}