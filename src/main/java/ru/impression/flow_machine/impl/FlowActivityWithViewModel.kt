package ru.impression.flow_machine.impl

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.impression.flow_machine.Flow
import ru.impression.flow_machine.FlowPerformer

abstract class FlowActivityWithViewModel<F : Flow<*>, M : ViewModel>(
    final override val flowClass: Class<F>,
    private val viewModelClass: Class<M>
) : AppCompatActivity(), FlowPerformer<F> {

    open val eventEnrichers: List<FlowPerformer<F>> = emptyList()

    lateinit var viewModel: M

    final override fun attachToFlow() = super.attachToFlow()

    final override fun detachFromFlow() = super.detachFromFlow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, FlowViewModelFactory(application, flowClass))[viewModelClass]
        attachToFlow()
    }

    final override fun eventOccurred(event: Flow.Event) {
        eventEnrichers.forEach { it.enrichEvent(event) }
        super.eventOccurred(event)
    }

    override fun onDestroy() {
        detachFromFlow()
        super.onDestroy()
    }
}