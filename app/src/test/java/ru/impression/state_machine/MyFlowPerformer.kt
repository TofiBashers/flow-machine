package ru.impression.state_machine

class MyFlowPerformer : FlowPerformer<MyFlow.Event, MyFlow.State> {

    init {
        attachToFlow(MyFlow::class.java)
        FlowManager.startFlow(MyFlow::class.java)
    }

    override fun onNewState(oldState: MyFlow.State?, newState: MyFlow.State) {
        when (newState) {
            MyFlow.State.MY_FIRST_STATE -> {
                print("FIRST_STATE")
                makeEvent(MyFlow.Event.MY_FIRST_EVENT)
            }
            MyFlow.State.MY_SECOND_STATE -> {
                print("SECOND_STATE")
                makeEvent(MyFlow.Event.MY_SECOND_EVENT)
            }
        }
    }

}