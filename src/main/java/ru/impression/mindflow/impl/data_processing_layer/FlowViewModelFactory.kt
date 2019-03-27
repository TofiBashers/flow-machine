package ru.impression.mindflow.impl.data_processing_layer

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import ru.impression.mindflow.FlowStep

class FlowViewModelFactory<F : FlowStep>(private val application: Application, private val flowClass: Class<F>) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when {
        FlowViewModel::class.java.isAssignableFrom(modelClass) ->
            modelClass.getConstructor(flowClass::class.java).newInstance(flowClass)
        FlowAndroidViewModel::class.java.isAssignableFrom(modelClass) ->
            modelClass
                .getConstructor(application::class.java, flowClass::class.java)
                .newInstance(application, flowClass)
        else -> super.create(modelClass)
    }
}