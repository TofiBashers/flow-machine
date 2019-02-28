package ru.impression.state_machine.example.things_managing_process.state

import ru.impression.state_machine.BusinessProcess

class DeletingFavouriteThing(val thing: String) : BusinessProcess.State()