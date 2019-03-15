package ru.impression.flow

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

abstract class FlowAndroidViewModel<F : Flow<*>>(
    application: Application,
    final override val flowClass: Class<F>
) : AndroidViewModel(application), FlowManager<F>, FlowPerformer<F> {

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

class FlowAndroidViewModelFactory<F : Flow<*>>(
    private val application: Application,
    private val flowClass: Class<F>
) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(FlowViewModel::class.java))
            modelClass.getConstructor(application::class.java, flowClass::class.java).newInstance(application, flowClass)
        else
            super.create(modelClass)
}