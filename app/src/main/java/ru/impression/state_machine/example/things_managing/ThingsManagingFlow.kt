package ru.impression.state_machine.example.things_managing

import ru.impression.state_machine.Flow

class ThingsManagingFlow : Flow<ThingsManagingFlow.Event, ThingsManagingFlow.State>() {

    override fun start() {

        // загружаем любимые вещи
        updateState(State.LOADING_FAVOURITE_THINGS)

        // любимые вещи загружены
        subscribeOnEvent(Event.FAVOURITE_THINGS_LOADED) {

            // устанавливаем в качестве основного состояния отображение только любимых вещей
            updateState(State.SHOWING_ONLY_FAVOURITE_THINGS, true)

            // пользователь запросил удалить любимую вещь
            subscribeOnEvent(Event.FAVOURITE_THING_UNLIKED) {

                // удаляем любимую вещь
                updateState(State.DELETING_FAVOURITE_THING)

                // любимая вещь удалена
                subscribeOnEvent(Event.FAVOURITE_THING_DELETED) {

                    // возвращаемся в основное состояние
                    updateState(primaryState!!)
                }
            }

            // пользователь запросил рекомендуемые вещи
            subscribeOnEvent(Event.RECOMMENDED_THINGS_REQUESTED) {

                // загружаем рекомендуемые вещи
                updateState(State.LOADING_RECOMMENDED_THINGS)

                // рекомендуемые вещи загружены
                subscribeOnEvent(Event.RECOMMENDED_THINGS_LOADED) {

                    // устанавливаем в качестве основного состояния отображение любимых и рекомендуемых вещей
                    updateState(State.SHOWING_FAVOURITE_AND_RECOMMENDED_THINGS, true)

                    // пользователю понравилась рекомендуемая вещь
                    subscribeOnEvent(Event.RECOMMENDED_THING_LIKED) {

                        // добавляем понравившуюся вещь в любимые
                        updateState(State.MAKING_THING_FAVOURITE)

                        // понравившуюся вещь добавлена в любимые
                        subscribeOnEvent(Event.THING_BECAME_FAVOURITE) {

                            // возвращаемся в основное состояние
                            updateState(primaryState!!)
                        }
                    }
                }
            }

            // пользователь запросил скрыть рекомендуемые вещи
            subscribeOnEvent(Event.RECOMMENDED_THINGS_HIDE_REQUESTED) {

                // устанавливаем в качестве основного состояния отображение только любимых вещей
                updateState(State.SHOWING_ONLY_FAVOURITE_THINGS, true)
            }
        }
    }

    enum class Event {
        FAVOURITE_THINGS_LOADED,
        FAVOURITE_THING_UNLIKED,
        FAVOURITE_THING_DELETED,
        RECOMMENDED_THINGS_REQUESTED,
        RECOMMENDED_THINGS_LOADED,
        RECOMMENDED_THING_LIKED,
        THING_BECAME_FAVOURITE,
        RECOMMENDED_THINGS_HIDE_REQUESTED
    }

    enum class State {
        LOADING_FAVOURITE_THINGS,
        SHOWING_ONLY_FAVOURITE_THINGS,
        DELETING_FAVOURITE_THING,
        LOADING_RECOMMENDED_THINGS,
        SHOWING_FAVOURITE_AND_RECOMMENDED_THINGS,
        MAKING_THING_FAVOURITE
    }
}
