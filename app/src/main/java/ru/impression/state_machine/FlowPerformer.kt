package ru.impression.state_machine

interface FlowPerformer<F : Flow<E, S>, E : Enum<E>, S : Enum<S>> {

    val flowClass: Class<F>

    fun onStateUpdated(oldState: S, newState: S)

    fun makeEvent(event: E) {
        REGISTERED_FLOWS[flowClass.canonicalName!!]
    }
}