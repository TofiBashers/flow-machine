package ru.impression.flow_machine.impl

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.impression.flow_machine.DISPOSABLES
import ru.impression.flow_machine.Flow
import ru.impression.flow_machine.FlowPerformer

abstract class FlowFragmentWithViewModel<F : Flow<*>, M : ViewModel>(
    final override val flowClass: Class<F>,
    private val viewModelClass: Class<M>
) : Fragment(), FlowPerformer<F> {

    open val eventEnrichers: List<FlowPerformer<F>> = emptyList()

    lateinit var viewModel: M

    final override fun attachToFlow() = super.attachToFlow()

    final override fun detachFromFlow() = super.detachFromFlow()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(
            this,
            FlowViewModelFactory(activity!!.application, flowClass)
        )[viewModelClass]
        attachToFlow()
    }

    final override fun eventOccurred(event: Flow.Event) {
        eventEnrichers.forEach { it.enrichEvent(event) }
        super.eventOccurred(event)
    }

    override fun onDestroyView() {
        detachFromFlow()
        super.onDestroyView()
    }
}