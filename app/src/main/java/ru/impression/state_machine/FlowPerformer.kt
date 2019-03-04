package ru.impression.state_machine

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

interface FlowPerformer<F : Flow<E, S>, E : Enum<E>, S : Enum<S>> {

    val flow: Class<F>

    fun attachToFlow() {
        val stateSubject = BehaviorSubject.create<NewStateReceiving>()
        STATE_SUBJECTS[flow.canonicalName!!]!!.add(stateSubject)
        DISPOSABLES[flow.canonicalName!!]!!.addAll(stateSubject
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                onNewStateReceived(it.oldState as S?, it.newState as S)
            }
        )
    }

    fun onNewStateReceived(oldState: S?, newState: S)

    fun makeEvent(event: E) {
        EVENT_SUBJECTS[flow.canonicalName!!]!![event]!!.onNext(Unit)
    }
}