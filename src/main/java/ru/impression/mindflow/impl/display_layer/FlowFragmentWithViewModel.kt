package ru.impression.mindflow.impl.display_layer

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import ru.impression.mindflow.FlowEvent
import ru.impression.mindflow.FlowStep
import ru.impression.mindflow.FlowPerformer
import ru.impression.mindflow.impl.processing_layer.FlowViewModelFactory

abstract class FlowFragmentWithViewModel<F : FlowStep, M : ViewModel>(
    final override val flowStepClass: Class<F>,
    private val viewModelClass: Class<M>
) : Fragment(), FlowPerformer<F> {

    lateinit var viewModel: M

    final override fun attachToFlow() = super.attachToFlow()

    final override fun eventOccurred(event: FlowEvent) = super.eventOccurred(event)

    final override fun detachFromFlow() = super.detachFromFlow()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(
            this,
            FlowViewModelFactory(activity!!.application, flowStepClass)
        )[viewModelClass]
        attachToFlow()
    }

    override fun onDestroyView() {
        detachFromFlow()
        super.onDestroyView()
    }
}