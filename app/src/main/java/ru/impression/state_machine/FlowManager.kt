package ru.impression.state_machine

object FlowManager {

    fun <F : Flow<*, *>> startFlow(flow: Class<F>) {
        val flowInstance = flow.newInstance()
        EVENT_SUBJECTS[flow.canonicalName!!] = HashMap()
        flowInstance.startInternal()
    }

    fun <F : Flow<*, *>> finishFlow(flow: Class<F>) {
        DISPOSABLES[flow.canonicalName!!]!!.dispose()
        DISPOSABLES.remove(flow.canonicalName!!)
        EVENT_SUBJECTS.remove(flow.canonicalName!!)
        STATE_SUBJECTS.remove(flow.canonicalName!!)
    }
}