package ru.impression.state_machine

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

object FlowManager {

    fun <F : Flow<*, *>> startFlow(flow: Class<F>) {
        val flowInstance = flow.newInstance()
        DISPOSABLES[flow.canonicalName!!] = CompositeDisposable()
        FLOW_PERFORMER_ATTACH_SUBJECTS[flow.canonicalName!!] = BehaviorSubject.create()
        EVENT_SUBJECTS[flow.canonicalName!!] = HashMap()
        STATE_SUBJECTS[flow.canonicalName!!] = ArrayList()
        flowInstance.startInternal()
    }

    fun <F : Flow<*, *>> finishFlow(flow: Class<F>) {
        DISPOSABLES[flow.canonicalName!!]!!.dispose()
        DISPOSABLES.remove(flow.canonicalName!!)
        EVENT_SUBJECTS.remove(flow.canonicalName!!)
        STATE_SUBJECTS.remove(flow.canonicalName!!)
    }
}