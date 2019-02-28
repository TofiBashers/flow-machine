package ru.impression.state_machine.example.things_managing_process

import ru.impression.state_machine.BusinessProcess

class ThingsManagingProcess : BusinessProcess<Event, State>() {

    override fun begin() {

        // загружаем любимые вещи
        updateState(State.LOADING_FAVOURITE_THINGS)

        // любимые вещи загружены
        awaitEvent(Event.FAVOURITE_THINGS_LOADED) {

            // устанавливаем в качестве основного состояния отображение только любимых вещей
            updateState(State.DISPLAY_ONLY_FAVOURITE_THINGS, true)

            // пользователь запросил удалить любимую вещь
            awaitEvent(Event.FAVOURITE_THING_UNLIKED) {

                // удаляем любимую вещь
                updateState(State.DELETING_FAVOURITE_THING)

                // любимая вещь удалена
                awaitEvent(Event.FAVOURITE_THING_DELETED) {

                    // возвращаемся в основное состояние
                    updateState(primaryState)
                }
            }

            // пользователь запросил рекомендуемые вещи
            awaitEvent(Event.RECOMMENDED_THINGS_REQUESTED) {

                // загружаем рекомендуемые вещи
                updateState(State.LOADING_RECOMMENDED_THINGS)

                // рекомендуемые вещи загружены
                awaitEvent(Event.RECOMMENDED_THINGS_LOADED) {

                    // устанавливаем в качестве основного состояния отображение любимых и понравившихся вещей
                    updateState(State.DISPLAY_ALL_THINGS, true)

                    // пользователю понравилась рекомендуемая вещь
                    awaitEvent(Event.RECOMMENDED_THING_LIKED) {

                        // добавляем понравившуюся вещь в любимые
                        updateState(State.ADDING_THING_TO_FAVOURITES)

                        // понравившуюся вещь добавлена в любимые
                        awaitEvent(Event.FAVOURITE_THING_ADDED) {

                            // возвращаемся в основное состояние
                            updateState(primaryState)
                        }
                    }
                }
            }

            // пользователь запросил скрыть рекомендуемые вещи
            awaitEvent(Event.RECOMMENDED_THINGS_HIDE_REQUESTED) {

                // устанавливаем в качестве основного состояния отображение только любимых вещей
                updateState(State.DISPLAY_ONLY_FAVOURITE_THINGS, true)
            }
        }
    }

}
