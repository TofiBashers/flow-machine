package ru.impression.flow

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class FlowActivity<F : Flow<*>, M : ViewModel>(
    final override val flowClass: Class<F>
) : AppCompatActivity(), FlowPerformer<F> {

    abstract val viewModelClass: Class<M>

    lateinit var viewModel: M

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        attachToFlow()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            if (viewModelClass.isAndroidViewModel())
                FlowAndroidViewModelFactory(application, flowClass)
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

    override fun onDestroy() {
        detachFromFlow()
        super.onDestroy()
    }
}