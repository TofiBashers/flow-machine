package ru.impression.mindflow.impl.display_layer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.impression.mindflow.FlowEvent
import ru.impression.mindflow.FlowStep
import ru.impression.mindflow.FlowPerformer

abstract class FlowActivity<F : FlowStep>(final override val flowStepClass: Class<F>) :
    AppCompatActivity(), FlowPerformer<F> {

    final override fun attachToFlow() = super.attachToFlow()

    final override fun eventOccurred(event: FlowEvent) = super.eventOccurred(event)

    final override fun detachFromFlow() = super.detachFromFlow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        attachToFlow()
    }

    override fun onDestroy() {
        detachFromFlow()
        super.onDestroy()
    }
}