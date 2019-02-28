package ru.impression.state_machine

import android.support.v4.app.Fragment

abstract class BusinessProcessFragment<E : Enum<E>, S : Enum<S>> : Fragment() {

    protected lateinit var primaryState: S

    protected lateinit var currentState: S

    abstract fun onStateUpdated()

    fun makeEvent(event: E) {

    }

}