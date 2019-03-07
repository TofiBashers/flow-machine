package ru.impression.flow

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

interface FlowPerformer<F : Flow<*>> {

    val flow: Class<F>

    fun attachToFlow() = flow.canonicalName?.let { flowName ->
        javaClass.canonicalName?.let { thisName ->
            DISPOSABLES[thisName] = CompositeDisposable().apply {
                ACTION_SUBJECTS[flowName]?.let { actionSubject ->
                    addAll(
                        actionSubject
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ performAction(it) }) { throw it }
                    )
                }
            }
        }
    }

    fun performAction(action: Flow.Action)

    fun onEvent(event: Flow.Event) = flow.canonicalName?.let { flowName ->
        EVENT_SUBJECTS[flowName]?.onNext(event)
    }

    fun detachFromFlow() = javaClass.canonicalName?.let { thisName ->
        DISPOSABLES[thisName]?.dispose()
        DISPOSABLES.remove(thisName)
    }
}