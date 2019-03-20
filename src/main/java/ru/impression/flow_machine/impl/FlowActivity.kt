package ru.impression.flow_machine.impl

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.impression.flow_machine.Flow
import ru.impression.flow_machine.FlowPerformer

abstract class FlowActivity<F : Flow<*>>(override val flowClass: Class<F>) : AppCompatActivity(), FlowPerformer<F> {

    open val eventEnrichers: List<FlowPerformer<F>> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        attachToFlow()
    }

    override fun eventOccurred(event: Flow.Event) {
        eventEnrichers.forEach { it.enrichEvent(event) }
        super.eventOccurred(event)
    }

    override fun onDestroy() {
        detachFromFlow()
        super.onDestroy()
    }
}