package ru.impression.flow

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class FlowActivity<F : Flow<*>, M : ViewModel>(
    final override val flowClass: Class<F>
) : AppCompatActivity(), FlowPerformer<F> {

    abstract val viewModelClass: Class<M>

    lateinit var viewModel: M

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        attachToFlow()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            if (viewModelClass.isAndroidViewModel())
                FlowAndroidViewModelFactory(application, flowClass)
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

    override fun onDestroy() {
        detachFromFlow()
        super.onDestroy()
    }
}