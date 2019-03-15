package ru.impression.flow

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

abstract class FlowViewModel<F : Flow<*>>(
    final override val flowClass: Class<F>
) : ViewModel(), FlowManager<F>, FlowPerformer<F> {

    final override fun startFlow() = super.startFlow()

    final override fun attachToFlow() = super.attachToFlow()

    init {
        startFlow()
        attachToFlow()
    }

    override fun onCleared() {
        detachFromFlow()
        finishFlow()
        super.onCleared()
    }
}

class FlowViewModelFactory<F : Flow<*>>(private val flowClass: Class<F>) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(FlowViewModel::class.java))
            modelClass.getConstructor(flowClass::class.java).newInstance(flowClass)
        else
            super.create(modelClass)
}