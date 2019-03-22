package ru.impression.flow_machine.impl

import android.app.Application
import ru.impression.flow_machine.Flow
import ru.impression.flow_machine.FlowInitiator
import ru.impression.flow_machine.FlowPerformer

abstract class FlowInitiatingApplication<F : Flow<*>>(final override val flowClass: Class<F>) :
    Application(), FlowInitiator<F>, FlowPerformer<F> {

    final override fun startFlow() = super.startFlow()

    final override fun attachToFlow() = super.attachToFlow()

    final override fun eventOccurred(event: Flow.Event) = super.eventOccurred(event)

    final override fun detachFromFlow() = super.detachFromFlow()

    final override fun finishFlow() = super.finishFlow()

    override fun onCreate() {
        super.onCreate()
        startFlow()
        attachToFlow()
    }
}