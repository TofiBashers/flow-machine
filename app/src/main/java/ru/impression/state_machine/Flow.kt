package ru.impression.state_machine

import io.reactivex.schedulers.Schedulers

abstract class Flow<S : Flow.State>(val state: S) {

    protected val subscribedOnEvents = ArrayList<String>()

    abstract fun start()

    protected inline fun <reified E : Event> subscribeOnEvent(crossinline onEvent: (E) -> Unit) =
        E::class.java.canonicalName?.let { eventName ->
            if (!subscribedOnEvents.contains(eventName)) {
                javaClass.canonicalName?.let { thisName ->
                    EVENT_SUBJECTS[thisName]
                        ?.subscribeOn(Schedulers.newThread())
                        ?.observeOn(Schedulers.newThread())
                        ?.subscribe({ event -> if (event is E) onEvent(event) }) { throw  it }
                        ?.let { disposable ->
                            DISPOSABLES[thisName]?.addAll(disposable)
                            subscribedOnEvents.add(eventName)
                        }
                }
            }
        }

    protected fun performAction(action: Action) = javaClass.canonicalName?.let { thisName ->
        ACTION_SUBJECTS[thisName]?.onNext(action)
    }

    abstract class Event

    abstract class State

    abstract class Action
}