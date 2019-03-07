package ru.impression.flow

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
                    .let { disposable ->
                        DISPOSABLES[thisName]?.addAll(disposable)
                    }
            }
        }

    protected inline fun <reified E1 : Event, reified E2 : Event> subscribeOnSeriesOfEvents(
        crossinline onSeriesOfEvents: (E1, E2) -> Unit
    ) = internalSubscribeOnSeriesOfEvents(
        { onSeriesOfEvents(it[0] as E1, it[1] as E2) },
        E1::class.java,
        E2::class.java
    )

    protected inline fun <reified E1 : Event, reified E2 : Event, reified E3 : Event> subscribeOnSeriesOfEvents(
        crossinline onSeriesOfEvents: (E1, E2, E3) -> Unit
    ) = internalSubscribeOnSeriesOfEvents(
        { onSeriesOfEvents(it[0] as E1, it[1] as E2, it[2] as E3) },
        E1::class.java,
        E2::class.java,
        E3::class.java
    )

    protected inline fun <reified E1 : Event, reified E2 : Event, reified E3 : Event, reified E4 : Event> subscribeOnSeriesOfEvents(
        crossinline onSeriesOfEvents: (E1, E2, E3, E4) -> Unit
    ) = internalSubscribeOnSeriesOfEvents(
        { onSeriesOfEvents(it[0] as E1, it[1] as E2, it[2] as E3, it[3] as E4) },
        E1::class.java,
        E2::class.java,
        E3::class.java,
        E4::class.java
    )

    protected fun internalSubscribeOnSeriesOfEvents(
        onSeriesOfEvents: (events: List<Event>) -> Unit,
        vararg classes: Class<*>
    ) = javaClass.canonicalName?.let { thisName ->
        EVENT_SUBJECTS[thisName]?.let { eventSubject ->
            DISPOSABLES[thisName]?.addAll(
                eventSubject
                    .filter { classes.contains(it::class.java) }
                    .buffer(classes.size)
                    .filter {
                        it.distinct()
                        it.size == classes.size
                    }
                    .map {
                        ArrayList<Event>().apply {
                            for (i in 0..classes.size) {
                                this[i] = it.find { it::class.java == classes[i] }!!
                            }
                        }
                    }
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(Schedulers.newThread())
                    .subscribe({ onSeriesOfEvents(it) }) { throw  it }

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