package ru.impression.flow

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

interface FlowManager<F : Flow<*>> {

    val flowClass: Class<F>

    fun startFlow() {
        flowClass.canonicalName?.let { flowName ->
            val flowInstance = flowClass.newInstance()
            DISPOSABLES[flowName] = CompositeDisposable()
            EVENT_SUBJECTS[flowName] = BehaviorSubject.create()
            ACTION_SUBJECTS[flowName] = BehaviorSubject.create()
            flowInstance.start()
        }
    }

    fun stopFlow() {
        flowClass.canonicalName?.let { flowName ->
            DISPOSABLES[flowName]?.dispose()
            DISPOSABLES.remove(flowName)
            EVENT_SUBJECTS.remove(flowName)
            ACTION_SUBJECTS.remove(flowName)
        }
    }
}