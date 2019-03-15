package ru.impression.flow

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class FlowActivity<M : ViewModel, F : Flow<*>>(
    final override val flow: Class<F>
) : AppCompatActivity(), FlowPerformer<F> {

    abstract val viewModel: Class<M>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        attachToFlow()
    }

    private fun initViewModel() = ViewModelProviders.of(
        this,
        if (viewModel.isAssignableFrom(AndroidViewModel::class.java))
            FlowAndroidViewModelFactory(application, flow)
        else
            FlowViewModelFactory(flow)
    )[viewModel]

    override fun onDestroy() {
        detachFromFlow()
        super.onDestroy()
    }
}