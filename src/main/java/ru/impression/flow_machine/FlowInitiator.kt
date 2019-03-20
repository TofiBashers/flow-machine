package ru.impression.flow_machine

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

interface FlowInitiator<F : Flow<*>> {

    val flowClass: Class<F>

    fun startFlow() {
        flowClass.canonicalName?.let { flowName ->
            DISPOSABLES[flowName]?.let { return }
            val flowInstance = flowClass.newInstance()
            DISPOSABLES[flowName] = CompositeDisposable()
            EVENT_SUBJECTS[flowName] = BehaviorSubject.create()
            ACTION_SUBJECTS[flowName] = BehaviorSubject.create()
            flowInstance.start()
        }
    }

    fun finishFlow() {
        flowClass.canonicalName?.let { flowName ->
            DISPOSABLES[flowName]?.dispose()
            DISPOSABLES.remove(flowName)
            EVENT_SUBJECTS.remove(flowName)
            ACTION_SUBJECTS.remove(flowName)
        }
    }
}