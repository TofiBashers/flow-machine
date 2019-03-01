package ru.impression.state_machine

object FlowManager {

    fun <F : Flow<*, *>> startFlow(flow: Class<F>) {
        val flowInstance = flow.newInstance()
        REGISTERED_FLOWS[flow.canonicalName!!] = flowInstance
        flowInstance.startInternal()
    }

    fun <F : Flow<*, *>> finishFlow(flow: Class<F>) {
        REGISTERED_FLOWS.remove(flow.canonicalName!!)
    }
}