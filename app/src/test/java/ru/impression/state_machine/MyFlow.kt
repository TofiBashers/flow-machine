package ru.impression.state_machine


class MyFlow : Flow<MyFlow.Event, MyFlow.State>() {
    override fun start() {
        updateState(State.MY_FIRST_STATE)
        subscribeOnEvent(Event.MY_FIRST_EVENT) {
            updateState(State.MY_SECOND_STATE)
            subscribeOnEvent(Event.MY_SECOND_EVENT) {
                print("OK")
            }
        }
    }

    enum class Event {
        MY_FIRST_EVENT,
        MY_SECOND_EVENT
    }

    enum class State {
        MY_FIRST_STATE,
        MY_SECOND_STATE,
    }
}