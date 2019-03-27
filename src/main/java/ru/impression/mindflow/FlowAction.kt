package ru.impression.mindflow

abstract class FlowAction

open class MoveToNextFlowStep(val nextFlowStepClass: Class<FlowStep>) : FlowAction()