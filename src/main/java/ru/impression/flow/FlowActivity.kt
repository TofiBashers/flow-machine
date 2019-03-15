package ru.impression.flow

import android.arch.lifecycle.AndroidViewModel
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
            if (viewModelClass.isAssignableFrom(AndroidViewModel::class.java))
                FlowAndroidViewModelFactory(application, flowClass)
            else
                FlowViewModelFactory(flowClass)
        )[viewModelClass]
    }

    override fun onDestroy() {
        detachFromFlow()
        super.onDestroy()
    }
}