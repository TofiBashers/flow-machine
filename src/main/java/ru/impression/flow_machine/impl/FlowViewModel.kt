package ru.impression.flow_machine.impl

import android.arch.lifecycle.ViewModel
import io.reactivex.subjects.BehaviorSubject
import ru.impression.flow_machine.Flow
import ru.impression.flow_machine.FlowPerformer

abstract class FlowViewModel<F : Flow<*>>(
    final override val flowClass: Class<F>
) : ViewModel(), FlowPerformer<F> {

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