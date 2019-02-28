package ru.impression.state_machine.example.things_managing_process.state

import ru.impression.state_machine.BusinessProcess

class DisplayingAllThings(
    val things: List<String>
) : BusinessProcess.State()