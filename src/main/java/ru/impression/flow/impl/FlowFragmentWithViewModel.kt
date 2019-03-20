package ru.impression.flow.impl

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.impression.flow.DISPOSABLES
import ru.impression.flow.Flow
import ru.impression.flow.FlowPerformer

abstract class FlowFragmentWithViewModel<F : Flow<*>, M : ViewModel>(
    override val flowClass: Class<F>,
    val viewModelClass: Class<M>
) : Fragment(), FlowPerformer<F> {

    lateinit var viewModel: M

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        attachToFlow()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            FlowViewModelFactory(activity!!.application, flowClass)
        )[viewModelClass]
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

    override fun onDestroyView() {
        detachFromFlow()
        super.onDestroyView()
    }
}