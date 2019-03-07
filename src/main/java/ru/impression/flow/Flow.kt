package ru.impression.flow

import io.reactivex.schedulers.Schedulers

abstract class Flow<S : Flow.State>(val state: S) {

    abstract fun start()

    protected inline fun <reified E : Event> subscribeOnEvent(crossinline onEvent: (E) -> Unit) =
        javaClass.canonicalName?.let { thisName ->
            EVENT_SUBJECTS[thisName]
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(Schedulers.newThread())
                ?.subscribe({ event -> if (event is E) onEvent(event) }) { throw  it }
                ?.let { disposable ->
                    DISPOSABLES[thisName]?.addAll(disposable)
                }
        }

    protected inline fun <reified E1 : Event, reified E2 : Event> subscribeOnSeriesOfEvents(
        crossinline onSeriesOfEvents: (E1, E2) -> Unit
    ) =
        javaClass.canonicalName?.let { thisName ->
            EVENT_SUBJECTS[thisName]?.let { eventSubject ->
                DISPOSABLES[thisName]?.addAll(
                    eventSubject
                        .filter { it is E1 || it is E2 }
                        .buffer(2)
                        .filter {
                            it.distinct()
                            it.size == 2
                        }
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribe({ onSeriesOfEvents(it[0] as E1, it[1] as E2) }) { throw  it }

                )
            }
        }

    protected fun performAction(action: Action) = javaClass.canonicalName?.let { thisName ->
        ACTION_SUBJECTS[thisName]?.onNext(action)
    }

    abstract class Event

    abstract class State

    abstract class Action
}