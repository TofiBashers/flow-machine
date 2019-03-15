package ru.impression.flow

import android.arch.lifecycle.AndroidViewModel
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
            if (viewModelClass.isAssignableFrom(AndroidViewModel::class.java))
                FlowAndroidViewModelFactory(activity!!.application, flowClass)
            else
                FlowViewModelFactory(flowClass)
        )[viewModelClass]
    }

    override fun onDestroyView() {
        detachFromFlow()
        super.onDestroyView()
    }
}