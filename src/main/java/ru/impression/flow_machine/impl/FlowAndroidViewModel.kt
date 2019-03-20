package ru.impression.flow_machine.impl

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import ru.impression.flow_machine.DISPOSABLES
import ru.impression.flow_machine.Flow
import ru.impression.flow_machine.FlowPerformer

abstract class FlowAndroidViewModel<F : Flow<*>>(
    application: Application,
    final override val flowClass: Class<F>
) : AndroidViewModel(application), FlowPerformer<F> {

    open val eventEnrichers: List<FlowPerformer<F>> = emptyList()

    final override fun attachToFlow() = super.attachToFlow()

    final override fun detachFromFlow() = super.detachFromFlow()

    init {
        attachToFlow()
    }

    final override fun eventOccurred(event: Flow.Event) {
        eventEnrichers.forEach { it.enrichEvent(event) }
        super.eventOccurred(event)
    }

    override fun onCleared() {
        detachFromFlow()
        super.onCleared()
    }
}