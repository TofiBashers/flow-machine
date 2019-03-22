package ru.impression.flow_machine.impl

import android.arch.lifecycle.ViewModel
import io.reactivex.subjects.BehaviorSubject
import ru.impression.flow_machine.Flow
import ru.impression.flow_machine.FlowPerformer

abstract class FlowViewModel<F : Flow<*>>(final override val flowClass: Class<F>) : ViewModel(), FlowPerformer<F> {

    final override fun attachToFlow() = super.attachToFlow()

    final override fun eventOccurred(event: Flow.Event) = super.eventOccurred(event)

    final override fun detachFromFlow() = super.detachFromFlow()

    init {
        attachToFlow()
    }

    override fun onCleared() {
        detachFromFlow()
        super.onCleared()
    }
}