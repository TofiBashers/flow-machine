package ru.impression.state_machine

import java.util.HashMap

internal val REGISTERED_FLOWS: HashMap<String, Flow<*, *>> = HashMap()

internal val REGISTERED_FLOW_PERFORMERS: HashMap<FlowPerformer<*, *>, Class<Flow<*, *>>> = HashMap()
