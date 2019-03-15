package ru.impression.flow

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

abstract class FlowFragment<M : ViewModel, F : Flow<*>>(
    final override val flow: Class<F>
) : Fragment(), FlowPerformer<F> {

    abstract val viewModel: Class<M>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        attachToFlow()
    }

    private fun initViewModel() = ViewModelProviders.of(
        this,
        if (viewModel.isAssignableFrom(AndroidViewModel::class.java))
            FlowAndroidViewModelFactory(activity!!.application, flow)
        else
            FlowViewModelFactory(flow)
    )[viewModel]

    override fun onDestroyView() {
        detachFromFlow()
        super.onDestroyView()
    }
}