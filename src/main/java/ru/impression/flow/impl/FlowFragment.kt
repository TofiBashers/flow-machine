package ru.impression.flow.impl

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import ru.impression.flow.Flow
import ru.impression.flow.FlowPerformer

abstract class FlowFragment<F : Flow<*>>(override val flowClass: Class<F>) : Fragment(), FlowPerformer<F> {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachToFlow()
    }

    override fun onDestroyView() {
        detachFromFlow()
        super.onDestroyView()
    }
}