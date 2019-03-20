package ru.impression.flow_machine.impl

import android.app.Application
import ru.impression.flow_machine.Flow
import ru.impression.flow_machine.FlowInitiator
import ru.impression.flow_machine.FlowPerformer

abstract class FlowInitiatingApplication<F : Flow<*>>(override val flowClass: Class<F>) :
    Application(), FlowInitiator<F>, FlowPerformer<F> {

    open val eventEnrichers: List<FlowPerformer<F>> = emptyList()

    override fun onCreate() {
        super.onCreate()
        startFlow()
        attachToFlow()
    }

    override fun eventOccurred(event: Flow.Event) {
        eventEnrichers.forEach { it.enrichEvent(event) }
        super.eventOccurred(event)
    }
}