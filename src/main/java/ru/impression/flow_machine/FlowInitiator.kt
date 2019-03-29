package ru.impression.flow_machine

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject

interface FlowInitiator<F : Flow<*>> {

    val flowClass: Class<F>

    fun startFlow(holdLastEventsCount: Int = 0, holdLastActionsCount: Int = 0) {
        flowClass.canonicalName?.let { flowName ->
            DISPOSABLES[flowName]?.let { return }
            val flowInstance = flowClass.newInstance()
            DISPOSABLES[flowName] = CompositeDisposable()
            EVENT_SUBJECTS[flowName] = if (holdLastEventsCount == 0)
                PublishSubject.create()
            else
                ReplaySubject.createWithSize(holdLastEventsCount)
            ACTION_SUBJECTS[flowName] = if (holdLastEventsCount == 0)
                PublishSubject.create()
            else
                ReplaySubject.createWithSize(holdLastActionsCount)
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