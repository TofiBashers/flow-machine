package ru.impression.state_machine

import java.util.*

object FlowManager {

    fun startFlow(flow: Flow<*, *>) {
        REGISTERED_FLOWS[flow.javaClass.canonicalName!!] = flow
        flow.startInternal()
    }

    fun finishFlow(flow: Flow<*, *>) {
        REGISTERED_FLOWS.remove(flow.javaClass.canonicalName!!)
    }
}