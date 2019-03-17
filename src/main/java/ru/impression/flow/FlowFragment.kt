package ru.impression.flow

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class FlowFragment<F : Flow<*>, M : ViewModel>(
    final override val flowClass: Class<F>,
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
            if (viewModelClass.isAndroidViewModel())
                FlowAndroidViewModelFactory(activity!!.application, flowClass)
            else
                FlowViewModelFactory(flowClass)
        )[viewModelClass]

        (if (viewModelClass.isAndroidViewModel())
            (viewModel as FlowAndroidViewModel<*>).viewEnrichEventSubject
        else
            (viewModel as FlowViewModel<*>).viewEnrichEventSubject)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ enrichEvent(it) }) { throw it }
            .let { disposable ->
                javaClass.canonicalName?.let { thisName ->
                    DISPOSABLES[thisName]?.addAll(disposable)
                }
            }
    }

    override fun eventOccurred(event: Flow.Event) {
        (viewModel as FlowPerformer<*>).enrichEvent(event)
        super.eventOccurred(event)
    }

    override fun onDestroyView() {
        detachFromFlow()
        super.onDestroyView()
    }
}