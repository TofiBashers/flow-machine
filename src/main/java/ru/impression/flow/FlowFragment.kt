package ru.impression.flow

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

abstract class FlowFragment<F : Flow<*>, M : ViewModel>(
    final override val flowClass: Class<F>
) : Fragment(), FlowPerformer<F> {

    abstract val viewModelClass: Class<M>

    lateinit var viewModel: M

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        attachToFlow()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            if (viewModelClass.isAndroidViewModel())
                FlowAndroidViewModelFactory(activity!!.application, flowClass)
            else
                FlowViewModelFactory(flowClass)
        )[viewModelClass].apply {
            if (viewModelClass.isAndroidViewModel())
                (this as FlowAndroidViewModel<*>).collectViewEventData = { collectEventData(it) }
            else
                (this as FlowViewModel<*>).collectViewEventData = { collectEventData(it) }
        }
    }

    override fun eventOccurred(event: Flow.Event) {
        (viewModel as FlowPerformer<*>).collectEventData(event)
        super.eventOccurred(event)
    }

    override fun onDestroyView() {
        detachFromFlow()
        super.onDestroyView()
    }
}