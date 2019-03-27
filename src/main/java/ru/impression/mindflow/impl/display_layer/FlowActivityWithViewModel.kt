package ru.impression.mindflow.impl.display_layer

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.impression.mindflow.FlowEvent
import ru.impression.mindflow.FlowStep
import ru.impression.mindflow.FlowPerformer
import ru.impression.mindflow.impl.processing_layer.FlowViewModelFactory

abstract class FlowActivityWithViewModel<F : FlowStep, M : ViewModel>(
    final override val flowStepClass: Class<F>,
    private val viewModelClass: Class<M>
) : AppCompatActivity(), FlowPerformer<F> {

    lateinit var viewModel: M

    final override fun attachToFlow() = super.attachToFlow()

    final override fun eventOccurred(event: FlowEvent) = super.eventOccurred(event)

    final override fun detachFromFlow() = super.detachFromFlow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this,
            FlowViewModelFactory(application, flowStepClass)
        )[viewModelClass]
        attachToFlow()
    }

    override fun onDestroy() {
        detachFromFlow()
        super.onDestroy()
    }
}