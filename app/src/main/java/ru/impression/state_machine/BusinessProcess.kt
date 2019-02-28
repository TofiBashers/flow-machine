package ru.impression.state_machine

abstract class BusinessProcess {

    protected var primaryState: State =
        InitialState()

    protected var currentState: State =
        InitialState()

    abstract fun begin()

    fun <E : Event> awaitEvent(awaiter: (E) -> Unit) {
    }

    fun updateState(state: State, setAsPrimary: Boolean = false) {
        currentState = state
        if (setAsPrimary) {
            primaryState = state
        }
    }

    fun end() {

    }

    abstract class Event

    abstract class State

    class InitialState : State()
}