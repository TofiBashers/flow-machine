package ru.impression.flow.impl

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.impression.flow.Flow
import ru.impression.flow.FlowManager
import ru.impression.flow.FlowPerformer

abstract class FlowManageActivity<F : Flow<*>> : AppCompatActivity(), FlowPerformer<F>, FlowManager<F> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startFlow()
        attachToFlow()
    }

    override fun onDestroy() {
        detachFromFlow()
        stopFlow()
        super.onDestroy()
    }
}