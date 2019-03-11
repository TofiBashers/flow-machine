package ru.impression.flow

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

abstract class Flow<S : Flow.State>(val state: S) {

    abstract fun start()

    protected inline fun <reified E : Event> subscribeOnEvent(crossinline onEvent: (E) -> Unit) =
        javaClass.canonicalName?.let { thisName ->
            EVENT_SUBJECTS[thisName]?.let { eventSubject ->
                eventSubject
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(Schedulers.newThread())
                    .subscribe({ event -> if (event is E) onEvent(event) }) { throw  it }
                    .let { disposable -> DISPOSABLES[thisName]?.addAll(disposable) }
            }
        }

    protected inline fun <reified E1 : Event, reified E2 : Event> subscribeOnSeriesOfEvents(
        crossinline onSeriesOfEvents: (E1, E2) -> Unit
    ) = javaClass.canonicalName?.let { thisName ->
        EVENT_SUBJECTS[thisName]?.let { eventSubject ->
            Observable
                .zip(
                    eventSubject
                        .filter { it is E1 }
                        .map { it as E1 },
                    eventSubject
                        .filter { it is E2 }
                        .map { it as E2 },
                    BiFunction<E1, E2, Unit> { e1, e2 -> onSeriesOfEvents(e1, e2) }
                )
                .doOnError { throw it }
                .subscribe()
                .let { disposable -> DISPOSABLES[thisName]?.addAll(disposable) }
        }
    }

    protected fun performAction(action: Action) = javaClass.canonicalName?.let { thisName ->
        ACTION_SUBJECTS[thisName]?.onNext(action)
    }

    abstract class Event

    abstract class State

    abstract class Action
}