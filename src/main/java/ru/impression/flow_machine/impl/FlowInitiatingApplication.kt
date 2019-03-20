package ru.impression.flow_machine.impl

import android.app.Application
import ru.impression.flow_machine.Flow
import ru.impression.flow_machine.FlowInitiator
import ru.impression.flow_machine.FlowPerformer

abstract class FlowInitiatingApplication<F : Flow<*>>(override val flowClass: Class<F>) :
    Application(), FlowInitiator<F>, FlowPerformer<F> {

    override fun onCreate() {
        super.onCreate()
        startFlow()
        attachToFlow()
    }
}