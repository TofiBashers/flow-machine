package ru.impression.state_machine.example.things_managing_process

enum class State {
    LOADING_FAVOURITE_THINGS,
    DISPLAY_ONLY_FAVOURITE_THINGS,
    DELETING_FAVOURITE_THING,
    LOADING_RECOMMENDED_THINGS,
    DISPLAY_ALL_THINGS,
    ADDING_THING_TO_FAVOURITES
}