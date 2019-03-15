package ru.impression.flow

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

fun <M : ViewModel> Class<M>.isAndroidViewModel() = isAssignableFrom(AndroidViewModel::class.java)

abstract class FlowViewModel<F : Flow<*>>(
    final override val flowClass: Class<F>
) : ViewModel(), FlowManager<F>, FlowPerformer<F> {

    internal lateinit var collectViewEventData: (event: Flow.Event) -> Flow.Event

    final override fun startFlow() = super.startFlow()

    final override fun attachToFlow() = super.attachToFlow()

    init {
        startFlow()
        attachToFlow()
    }

    override fun eventOccurred(event: Flow.Event) = super.eventOccurred(collectViewEventData.invoke(event))

    override fun onCleared() {
        detachFromFlow()
        finishFlow()
        super.onCleared()
    }
}

abstract class FlowAndroidViewModel<F : Flow<*>>(
    application: Application,
    final override val flowClass: Class<F>
) : AndroidViewModel(application), FlowManager<F>, FlowPerformer<F> {

    internal lateinit var collectViewEventData: (event: Flow.Event) -> Flow.Event

    final override fun startFlow() = super.startFlow()

    final override fun attachToFlow() = super.attachToFlow()

    init {
        startFlow()
        attachToFlow()
    }

    override fun eventOccurred(event: Flow.Event) = super.eventOccurred(collectViewEventData.invoke(event))

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

class FlowAndroidViewModelFactory<F : Flow<*>>(
    private val application: Application,
    private val flowClass: Class<F>
) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(FlowAndroidViewModel::class.java))
            modelClass
                .getConstructor(application::class.java, flowClass::class.java)
                .newInstance(application, flowClass)
        else
            super.create(modelClass)
}