package ru.impression.flow

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.ReplaySubject

object FlowManager {

    fun <F : Flow<*>> startFlow(flow: Class<F>) {
        flow.canonicalName?.let { flowName ->
            val flowInstance = flow.newInstance()
            DISPOSABLES[flowName] = CompositeDisposable()
            EVENT_SUBJECTS[flowName] = BehaviorSubject.create()
            ACTION_SUBJECTS[flowName] = BehaviorSubject.create()
            flowInstance.start()
        }
    }

    fun <F : Flow<*>> finishFlow(flow: Class<F>) {
        flow.canonicalName?.let { flowName ->
            DISPOSABLES[flowName]?.dispose()
            DISPOSABLES.remove(flowName)
            EVENT_SUBJECTS.remove(flowName)
            ACTION_SUBJECTS.remove(flowName)
        }
    }
}