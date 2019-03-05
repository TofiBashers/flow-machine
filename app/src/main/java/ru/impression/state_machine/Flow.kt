package ru.impression.state_machine

import io.reactivex.schedulers.Schedulers

abstract class Flow<S : Flow.State>(val state: S) {

    private lateinit var lastAction: Action

    protected abstract fun start()

    internal fun startInternal() {
        DISPOSABLES[javaClass.canonicalName!!]!!.addAll(
            FLOW_PERFORMER_ATTACH_SUBJECTS[javaClass.canonicalName!!]!!
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe {
                    STATE_SUBJECTS[javaClass.canonicalName!!]!![it].onNext(lastAction)
                }
        )
        start()
    }

    protected inline fun <reified E : Event> subscribeOnEvent(crossinline onEvent: (E) -> Unit) {
        DISPOSABLES[javaClass.canonicalName!!]!!.addAll(
            EVENT_SUBJECTS[javaClass.canonicalName!!]!!.map { if (it is E) onEvent(it) }
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe()
        )
    }

    protected fun performAction(action: Action) {
        lastAction = action
        STATE_SUBJECTS[javaClass.canonicalName!!]!!.forEach { it.onNext(action) }
    }

    abstract class Event

    abstract class State

    abstract class Action
}