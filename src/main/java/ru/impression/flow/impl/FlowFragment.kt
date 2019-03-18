package ru.impression.flow.impl

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import ru.impression.flow.Flow
import ru.impression.flow.FlowPerformer

abstract class FlowFragment<F : Flow<*>>(final override val flowClass: Class<F>) : Fragment(), FlowPerformer<F> {

    private var attachedToFlow = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!attachedToFlow) {
            attachToFlow()
            attachedToFlow = true
        }
    }

    override fun onDestroy() {
        detachFromFlow()
        attachedToFlow = false
        super.onDestroy()
    }
}