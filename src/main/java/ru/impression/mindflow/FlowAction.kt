package ru.impression.mindflow

abstract class FlowAction

abstract class MoveToNextFlowStep<F : FlowStep>(val nextFlowStepClass: Class<F>) : FlowAction()