package ru.impression.flow

import android.util.Log
import io.reactivex.schedulers.Schedulers
import java.util.*

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
    ) = internalSubscribeOnSeriesOfEvents(
        E1::class.java,
        E2::class.java
    ) { onSeriesOfEvents(it[0] as E1, it[1] as E2) }

    protected inline fun <reified E1 : Event, reified E2 : Event, reified E3 : Event> subscribeOnSeriesOfEvents(
        crossinline onSeriesOfEvents: (E1, E2, E3) -> Unit
    ) = internalSubscribeOnSeriesOfEvents(
        E1::class.java,
        E2::class.java,
        E3::class.java
    ) { onSeriesOfEvents(it[0] as E1, it[1] as E2, it[2] as E3) }

    protected inline fun <reified E1 : Event, reified E2 : Event, reified E3 : Event, reified E4 : Event> subscribeOnSeriesOfEvents(
        crossinline onSeriesOfEvents: (E1, E2, E3, E4) -> Unit
    ) = internalSubscribeOnSeriesOfEvents(
        E1::class.java,
        E2::class.java,
        E3::class.java,
        E4::class.java
    ) { onSeriesOfEvents(it[0] as E1, it[1] as E2, it[2] as E3, it[3] as E4) }

    protected fun internalSubscribeOnSeriesOfEvents(
        vararg classes: Class<*>,
        onSeriesOfEvents: (events: List<Event>) -> Unit
    ) = javaClass.canonicalName?.let { thisName ->
        EVENT_SUBJECTS[thisName]?.let { eventSubject ->
            eventSubject
                .filter { classes.contains(it::class.java) }
                .buffer(classes.size)
                .map { it.distinctBy { it::class.java } }
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe({
                    if (it.size == classes.size) {
                        with(ArrayList<Event>()) {
                            for (i in 0 until classes.size) {
                                add(i, it.find { it::class.java == classes[i] }!!)
                            }
                            onSeriesOfEvents(this)
                        }
                    }
                }) { throw  it }
                .let { DISPOSABLES[thisName]?.addAll(it) }
        }
    }

    protected fun performAction(action: Action) = javaClass.canonicalName?.let { thisName ->
        ACTION_SUBJECTS[thisName]?.onNext(action)
    }

    abstract class Event

    abstract class State

    abstract class Action
}