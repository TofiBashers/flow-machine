package ru.impression.flow.impl

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import ru.impression.flow.Flow
import ru.impression.flow.FlowManager
import ru.impression.flow.FlowPerformer

abstract class FlowManageFragment<F : Flow<*>>(
    final override val flowClass: Class<F>
) : Fragment(), FlowPerformer<F>, FlowManager<F> {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startFlow()
        attachToFlow()
    }

    override fun onDestroyView() {
        detachFromFlow()
        stopFlow()
        super.onDestroyView()
    }
}