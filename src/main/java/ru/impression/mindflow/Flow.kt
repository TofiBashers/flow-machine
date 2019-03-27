package ru.impression.mindflow

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

object Flow {

    fun <F : FlowStep> start(initialStepClass: Class<F>) = moveToStep(null, initialStepClass)

    internal fun <F : FlowStep> moveToStep(previousStepClass: Class<FlowStep>?, nextStepClass: Class<F>) {
        previousStepClass?.canonicalName?.let { previousStepName ->
            DISPOSABLES[previousStepName]?.dispose()
            DISPOSABLES.remove(previousStepName)
            EVENT_SUBJECTS.remove(previousStepName)
            ACTION_SUBJECTS.remove(previousStepName)
        }
        nextStepClass.canonicalName?.let { nextStepName ->
            DISPOSABLES[nextStepName]?.let { return }
            val nextStepInstance = nextStepClass.newInstance()
            DISPOSABLES[nextStepName] = CompositeDisposable()
            EVENT_SUBJECTS[nextStepName] = BehaviorSubject.create()
            ACTION_SUBJECTS[nextStepName] = BehaviorSubject.create()
            nextStepInstance.start()
        }
    }

    fun finish() {
        DISPOSABLES.values.forEach { it.dispose() }
        DISPOSABLES.clear()
        EVENT_SUBJECTS.clear()
        ACTION_SUBJECTS.clear()
    }
}