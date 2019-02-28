package ru.impression.state_machine

abstract class BusinessProcess<E : Enum<E>, S : Enum<S>> {

    protected lateinit var primaryState: S

    protected lateinit var currentState: S

    abstract fun begin()

    fun awaitEvent(event: E, awaiter: () -> Unit) {
    }

    fun updateState(state: S, setAsPrimary: Boolean = false) {
        currentState = state
        if (setAsPrimary) {
            primaryState = state
        }
    }

    fun end() {

    }
}