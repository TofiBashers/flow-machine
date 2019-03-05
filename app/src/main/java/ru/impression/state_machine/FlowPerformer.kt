package ru.impression.state_machine

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

interface FlowPerformer<F : Flow<*>> {

    val flow: Class<F>

    fun attachToFlow() {
        val actionSubject = BehaviorSubject.create<Flow.Action>()
        STATE_SUBJECTS[flow.canonicalName!!]!!.add(actionSubject)
        DISPOSABLES[flow.canonicalName!!]!!.addAll(actionSubject
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ performAction(it) }) { throw it }
        )
        FLOW_PERFORMER_ATTACH_SUBJECTS[flow.canonicalName!!]!!.onNext(
            STATE_SUBJECTS[flow.canonicalName!!]!!.indexOf(actionSubject)
        )
    }

    fun performAction(action: Flow.Action)

    fun performEvent(event: Flow.Event) {
        EVENT_SUBJECTS[flow.canonicalName!!]!!.onNext(event)
    }
}