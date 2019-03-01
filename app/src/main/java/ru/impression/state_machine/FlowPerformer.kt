package ru.impression.state_machine

interface FlowPerformer<E : Enum<E>, S : Enum<S>> {

    fun <F : Flow<E, S>> attachToFlow(flow: Class<F>) {
        REGISTERED_FLOW_PERFORMERS[this] = flow as Class<Flow<*, *>>
    }

    fun onNewState(oldState: S?, newState: S)

    fun makeEvent(event: E) {

    }
}