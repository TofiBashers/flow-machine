package ru.impression.flow_machine.impl

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import ru.impression.flow_machine.Flow

class FlowViewModelFactory<F : Flow<*>>(private val application: Application, private val flowClass: Class<F>) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when {
        FlowViewModel::class.java.isAssignableFrom(modelClass) ||
                FlowInitiatingViewModel::class.java.isAssignableFrom(modelClass) ->
            modelClass.getConstructor(flowClass::class.java).newInstance(flowClass)
        FlowAndroidViewModel::class.java.isAssignableFrom(modelClass) ||
                FlowInitiatingAndroidViewModel::class.java.isAssignableFrom(modelClass) ->
            modelClass
                .getConstructor(application::class.java, flowClass::class.java)
                .newInstance(application, flowClass)
        else -> super.create(modelClass)
    }
}