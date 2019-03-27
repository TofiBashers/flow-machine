package ru.impression.mindflow

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

interface FlowPerformer<F : FlowStep> {

    val flowStepClass: Class<F>

    val eventEnrichers: List<FlowPerformer<F>> get() = emptyList()

    fun attachToFlow() {
        flowStepClass.canonicalName?.let { stepName ->
            javaClass.canonicalName?.let { thisName ->
                DISPOSABLES[thisName]?.dispose()
                DISPOSABLES[thisName] = CompositeDisposable().apply {
                    ACTION_SUBJECTS[stepName]?.let { actionSubject ->
                        add(
                            actionSubject
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({ performAction(it) }) { throw it }
                        )
                    }
                }
            }
        }
    }

    fun eventOccurred(event: FlowEvent) {
        eventEnrichers.forEach { it.enrichEvent(event) }
        flowStepClass.canonicalName?.let { stepName ->
            EVENT_SUBJECTS[stepName]?.onNext(event)
        }
    }

    fun enrichEvent(event: FlowEvent) = Unit

    fun performAction(action: FlowAction) = Unit

    fun detachFromFlow() {
        javaClass.canonicalName?.let { thisName ->
            DISPOSABLES[thisName]?.dispose()
            DISPOSABLES.remove(thisName)
        }
    }
}