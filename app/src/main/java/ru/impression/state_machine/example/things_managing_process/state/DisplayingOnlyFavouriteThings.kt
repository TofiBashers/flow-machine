package ru.impression.state_machine.example.things_managing_process.state

import ru.impression.state_machine.BusinessProcess

class DisplayingOnlyFavouriteThings(
    val things: List<String>
) : BusinessProcess.State()