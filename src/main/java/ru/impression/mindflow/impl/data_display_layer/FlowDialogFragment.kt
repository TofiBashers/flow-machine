package ru.impression.mindflow.impl.data_display_layer

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import ru.impression.mindflow.FlowEvent
import ru.impression.mindflow.FlowStep
import ru.impression.mindflow.FlowPerformer

abstract class FlowDialogFragment<F : FlowStep>(final override val flowStepClass: Class<F>) :
    DialogFragment(), FlowPerformer<F> {

    final override fun attachToFlow() = super.attachToFlow()

    final override fun eventOccurred(event: FlowEvent) = super.eventOccurred(event)

    final override fun detachFromFlow() = super.detachFromFlow()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachToFlow()
    }

    override fun onDestroyView() {
        detachFromFlow()
        super.onDestroyView()
    }
}