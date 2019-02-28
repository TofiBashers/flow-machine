package ru.impression.state_machine.example.things_managing_process.event

import ru.impression.state_machine.BusinessProcess

class FavouriteThingUnliked(val thing: String) : BusinessProcess.Event()