package ru.impression.flow_machine.impl

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import io.reactivex.subjects.BehaviorSubject
import ru.impression.flow_machine.Flow
import ru.impression.flow_machine.FlowInitiator
import ru.impression.flow_machine.FlowPerformer

abstract class FlowInitiatingAndroidViewModel<F : Flow<*>>(
    application: Application,
    final override val flowClass: Class<F>
) : AndroidViewModel(application), FlowInitiator<F>, FlowPerformer<F> {

    open val eventEnrichers: List<FlowPerformer<F>> = emptyList()

    final override fun startFlow() = super.startFlow()

    final override fun attachToFlow() = super.attachToFlow()

    init {
        startFlow()
        attachToFlow()
    }

    override fun eventOccurred(event: Flow.Event) {
        eventEnrichers.forEach { it.enrichEvent(event) }
        super.eventOccurred(event)
    }

    override fun onCleared() {
        detachFromFlow()
        finishFlow()
        super.onCleared()
    }
}