package ru.impression.state_machine

import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject


abstract class Flow<E : Enum<E>, S : Enum<S>> {

    protected abstract fun start()

    internal fun startInternal() {
        DISPOSABLES[javaClass.canonicalName!!]!!.addAll(
            FLOW_PERFORMER_ATTACH_SUBJECTS[javaClass.canonicalName!!]!!
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe {
                    makeNewStateReceiving(STATE_SUBJECTS[javaClass.canonicalName!!]!![it])
                }
        )
        start()
    }

    protected var currentState: S? = null

    protected var lastState: S? = null

    protected var primaryState: S? = null

    protected fun subscribeOnEvent(event: E, onEvent: () -> Unit) {
        val eventSubject = BehaviorSubject.create<Unit>()
        EVENT_SUBJECTS[javaClass.canonicalName!!]!![event] = eventSubject
        DISPOSABLES[javaClass.canonicalName!!]!!.addAll(eventSubject
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribe { onEvent.invoke() }
        )
    }

    protected fun updateState(state: S, makePrimary: Boolean = false) {
        lastState = currentState
        currentState = state
        if (makePrimary) {
            primaryState = state
        }
        STATE_SUBJECTS[javaClass.canonicalName!!]!!.forEach { makeNewStateReceiving(it) }
    }

    private fun makeNewStateReceiving(stateSubject: BehaviorSubject<NewStateReceiving>) = stateSubject.onNext(
        NewStateReceiving(lastState, currentState!!)
    )
}