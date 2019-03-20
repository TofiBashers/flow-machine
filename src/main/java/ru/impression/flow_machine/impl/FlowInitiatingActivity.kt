package ru.impression.flow_machine.impl

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.impression.flow_machine.Flow
import ru.impression.flow_machine.FlowInitiator
import ru.impression.flow_machine.FlowPerformer

abstract class FlowInitiatingActivity<F : Flow<*>>(final override val flowClass: Class<F>) :
    AppCompatActivity(), FlowInitiator<F>, FlowPerformer<F> {

    open val eventEnrichers: List<FlowPerformer<F>> = emptyList()

    final override fun attachToFlow() = super.attachToFlow()

    final override fun detachFromFlow() = super.detachFromFlow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startFlow()
        attachToFlow()
    }

    final override fun eventOccurred(event: Flow.Event) {
        eventEnrichers.forEach { it.enrichEvent(event) }
        super.eventOccurred(event)
    }

    override fun onDestroy() {
        detachFromFlow()
        finishFlow()
        super.onDestroy()
    }
}