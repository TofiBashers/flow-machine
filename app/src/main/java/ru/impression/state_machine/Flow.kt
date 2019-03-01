package ru.impression.state_machine


abstract class Flow<E : Enum<E>, S : Enum<S>> {

    protected abstract fun start()

    internal fun startInternal() = start()

    protected lateinit var currentState: S

    protected lateinit var lastState: S

    protected lateinit var primaryState: S

    fun subscribeOnEvent(event: E, onEvent: () -> Unit) {
    }

    fun updateState(state: S, makePrimary: Boolean = false) {
        lastState = currentState
        currentState = state
        if (makePrimary) {
            primaryState = state
        }
    }
}