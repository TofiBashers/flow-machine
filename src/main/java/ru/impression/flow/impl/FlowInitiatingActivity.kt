package ru.impression.flow.impl

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.impression.flow.Flow
import ru.impression.flow.FlowInitiator
import ru.impression.flow.FlowPerformer

abstract class FlowInitiatingActivity<F : Flow<*>>(override val flowClass: Class<F>) :
    AppCompatActivity(), FlowInitiator<F>, FlowPerformer<F> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startFlow()
        attachToFlow()
    }

    override fun onDestroy() {
        detachFromFlow()
        finishFlow()
        super.onDestroy()
    }
}