package ru.impression.flow_machine

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

interface FlowPerformer<F : Flow<*>> {

    val flowClass: Class<F>

    fun attachToFlow() {
        flowClass.canonicalName?.let { flowName ->
            javaClass.canonicalName?.let { thisName ->
                DISPOSABLES[thisName]?.dispose()
                DISPOSABLES[thisName] = CompositeDisposable().apply {
                    ACTION_SUBJECTS[flowName]?.let { actionSubject ->
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

    fun eventOccurred(event: Flow.Event) {
        flowClass.canonicalName?.let { flowName ->
            EVENT_SUBJECTS[flowName]?.onNext(event)
        }
    }

    fun enrichEvent(event: Flow.Event) = Unit

    fun performAction(action: Flow.Action) = Unit

    fun detachFromFlow() {
        javaClass.canonicalName?.let { thisName ->
            DISPOSABLES[thisName]?.dispose()
            DISPOSABLES.remove(thisName)
        }
    }
}