package ru.impression.mindflow.impl.data_processing_layer

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import ru.impression.mindflow.FlowEvent
import ru.impression.mindflow.FlowStep
import ru.impression.mindflow.FlowPerformer

abstract class FlowAndroidViewModel<F : FlowStep>(
    application: Application,
    final override val flowStepClass: Class<F>
) : AndroidViewModel(application), FlowPerformer<F> {

    final override fun attachToFlow() = super.attachToFlow()

    final override fun eventOccurred(event: FlowEvent) = super.eventOccurred(event)

    final override fun detachFromFlow() = super.detachFromFlow()

    init {
        attachToFlow()
    }

    override fun onCleared() {
        detachFromFlow()
        super.onCleared()
    }
}