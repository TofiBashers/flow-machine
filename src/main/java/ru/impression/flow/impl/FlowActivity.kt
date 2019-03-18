package ru.impression.flow.impl

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.impression.flow.Flow
import ru.impression.flow.FlowPerformer

abstract class FlowActivity<F : Flow<*>>(override val flowClass: Class<F>) : AppCompatActivity(), FlowPerformer<F> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        attachToFlow()
    }

    override fun onDestroy() {
        detachFromFlow()
        super.onDestroy()
    }
}