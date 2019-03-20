package ru.impression.flow_machine.impl

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.impression.flow_machine.DISPOSABLES
import ru.impression.flow_machine.Flow
import ru.impression.flow_machine.FlowPerformer

abstract class FlowActivityWithViewModel<F : Flow<*>, M : ViewModel>(
    override val flowClass: Class<F>,
    val viewModelClass: Class<M>
) : AppCompatActivity(), FlowPerformer<F> {

    lateinit var viewModel: M

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        attachToFlow()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, FlowViewModelFactory(application, flowClass))[viewModelClass]
        when {
            FlowViewModel::class.java.isAssignableFrom(viewModelClass) ->
                (viewModel as FlowViewModel<*>).viewEnrichEventSubject
            FlowAndroidViewModel::class.java.isAssignableFrom(viewModelClass) ->
                (viewModel as FlowAndroidViewModel<*>).viewEnrichEventSubject
            FlowInitiatingViewModel::class.java.isAssignableFrom(viewModelClass) ->
                (viewModel as FlowInitiatingViewModel<*>).viewEnrichEventSubject
            FlowInitiatingAndroidViewModel::class.java.isAssignableFrom(viewModelClass) ->
                (viewModel as FlowInitiatingAndroidViewModel<*>).viewEnrichEventSubject
            else -> null
        }
            ?.subscribeOn(Schedulers.newThread())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ enrichEvent(it) }) { throw it }
            ?.let { disposable ->
                javaClass.canonicalName?.let { thisName ->
                    DISPOSABLES[thisName]?.add(disposable)
                }
            }
    }

    override fun eventOccurred(event: Flow.Event) {
        if (viewModel is FlowPerformer<*>) (viewModel as FlowPerformer<*>).enrichEvent(event)
        super.eventOccurred(event)
    }

    override fun onDestroy() {
        detachFromFlow()
        super.onDestroy()
    }
}