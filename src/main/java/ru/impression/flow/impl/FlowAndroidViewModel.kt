package ru.impression.flow.impl

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import io.reactivex.subjects.BehaviorSubject
import ru.impression.flow.Flow
import ru.impression.flow.FlowPerformer

abstract class FlowAndroidViewModel<F : Flow<*>>(
    application: Application,
    final override val flowClass: Class<F>
) : AndroidViewModel(application), FlowPerformer<F> {

    internal val viewEnrichEventSubject = BehaviorSubject.create<Flow.Event>()

    final override fun attachToFlow() = super.attachToFlow()

    init {
        attachToFlow()
    }

    override fun eventOccurred(event: Flow.Event) {
        viewEnrichEventSubject.onNext(event)
        super.eventOccurred(event)
    }

    override fun onCleared() {
        detachFromFlow()
        super.onCleared()
    }
}