package ru.impression.flow.impl

import android.app.Application
import ru.impression.flow.Flow
import ru.impression.flow.FlowInitiator
import ru.impression.flow.FlowPerformer

abstract class FlowInitiatingApplication<F : Flow<*>>(override val flowClass: Class<F>) :
    Application(), FlowInitiator<F>, FlowPerformer<F> {

    override fun onCreate() {
        super.onCreate()
        startFlow()
        attachToFlow()
    }
}