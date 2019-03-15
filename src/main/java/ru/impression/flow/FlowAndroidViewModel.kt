package ru.impression.flow

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

abstract class FlowAndroidViewModel<F : Flow<*>>(
    application: Application,
    final override val flow: Class<F>
) : AndroidViewModel(application), FlowPerformer<F> {

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

class FlowAndroidViewModelFactory<F : Flow<*>>(
    private val application: Application,
    private val flow: Class<F>
) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(FlowViewModel::class.java))
            modelClass.getConstructor(application::class.java, flow::class.java).newInstance(application, flow)
        else
            super.create(modelClass)
}