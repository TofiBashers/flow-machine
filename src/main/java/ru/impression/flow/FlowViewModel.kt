package ru.impression.flow

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

abstract class FlowViewModel<F : Flow<*>>(final override val flow: Class<F>) : ViewModel(), FlowPerformer<F> {

    final override fun attachToFlow() {
        super.attachToFlow()
    }

    init {
        FlowManager.startFlow(flow)
        attachToFlow()
    }

    override fun onCleared() {
        detachFromFlow()
        FlowManager.finishFlow(flow)
        super.onCleared()
    }
}

class FlowViewModelFactory<F : Flow<*>>(private val flow: Class<F>) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(FlowViewModel::class.java))
            modelClass.getConstructor(flow::class.java).newInstance(flow)
        else
            super.create(modelClass)
}