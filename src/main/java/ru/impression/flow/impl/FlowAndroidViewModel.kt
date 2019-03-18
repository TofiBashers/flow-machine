package ru.impression.flow.impl

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import io.reactivex.subjects.BehaviorSubject
import ru.impression.flow.Flow
import ru.impression.flow.FlowManager
import ru.impression.flow.FlowPerformer

abstract class FlowAndroidViewModel<F : Flow<*>>(
    application: Application,
    final override val flowClass: Class<F>
) : AndroidViewModel(application), FlowManager<F>, FlowPerformer<F> {

    internal val viewEnrichEventSubject = BehaviorSubject.create<Flow.Event>()

    final override fun startFlow() = super.startFlow()

    final override fun attachToFlow() = super.attachToFlow()

    init {
        startFlow()
        attachToFlow()
    }

    override fun eventOccurred(event: Flow.Event) {
        viewEnrichEventSubject.onNext(event)
        super.eventOccurred(event)
    }

    override fun onCleared() {
        detachFromFlow()
        stopFlow()
        super.onCleared()
    }
}