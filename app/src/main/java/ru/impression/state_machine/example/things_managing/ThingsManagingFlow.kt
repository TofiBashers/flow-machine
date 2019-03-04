package ru.impression.state_machine.example.things_managing

import ru.impression.state_machine.Flow

class ThingsManagingFlow : Flow<ThingsManagingFlow.Event, ThingsManagingFlow.State>() {

    override fun start() {

        // загружаем любимые вещи
        updateState(State.LOADING_FAVOURITE_THINGS)

        // любимые вещи загружены
        subscribeOnEvent(Event.FAVOURITE_THINGS_LOADED) {

            // устанавливаем в качестве основного состояния отображение только любимых вещей
            updateState(State.SHOWING_FAVOURITE_THINGS, true)

            // пользователь запросил удалить любимую вещь
            subscribeOnEvent(Event.FAVOURITE_THING_UNLIKED) {

                // удаляем любимую вещь
                updateState(State.REMOVING_FAVOURITE_THING)

                // любимая вещь удалена
                subscribeOnEvent(Event.FAVOURITE_THING_REMOVED) {

                    refreshThings()
                }
            }

            // пользователь запросил рекомендуемые вещи
            subscribeOnEvent(Event.RECOMMENDED_THINGS_REQUESTED) {

                // загружаем рекомендуемые вещи
                updateState(State.LOADING_RECOMMENDED_THINGS)

                // рекомендуемые вещи загружены
                subscribeOnEvent(Event.RECOMMENDED_THINGS_LOADED) {

                    // устанавливаем в качестве основного состояния отображение любимых и рекомендуемых вещей
                    updateState(State.SHOWING_ALL_THINGS, true)

                    // пользователю понравилась рекомендуемая вещь
                    subscribeOnEvent(Event.RECOMMENDED_THING_LIKED) {

                        // добавляем понравившуюся вещь в любимые
                        updateState(State.ADDING_FAVOURITE_THING)

                        // понравившаяся вещь добавлена в любимые
                        subscribeOnEvent(Event.FAVOURITE_THING_ADDED) {

                            refreshThings()
                        }
                    }
                }
            }

            // пользователь запросил скрыть рекомендуемые вещи
            subscribeOnEvent(Event.RECOMMENDED_THINGS_HIDE_REQUESTED) {

                // устанавливаем в качестве основного состояния отображение только любимых вещей
                updateState(State.SHOWING_FAVOURITE_THINGS, true)
            }
        }
    }

    private fun refreshThings() =

        if (primaryState == State.SHOWING_FAVOURITE_THINGS) {

            // обновляем любимые вещи
            updateState(State.REFRESHING_FAVOURITE_THINGS)

            // любимые вещи обновлены
            subscribeOnEvent(Event.FAVOURITE_THINGS_REFRESHED) {

                // возвращаемся в основное состояние
                updateState(primaryState!!)
            }

        } else {

            // обновляем все вещи
            updateState(State.REFRESHING_ALL_THINGS)

            // все вещи обновлены
            subscribeOnEvent(Event.ALL_THINGS_REFRESHED) {

                // возвращаемся в основное состояние
                updateState(primaryState!!)
            }
        }

    enum class Event {
        FAVOURITE_THINGS_LOADED,
        FAVOURITE_THING_UNLIKED,
        FAVOURITE_THING_ADDED,
        FAVOURITE_THING_REMOVED,
        FAVOURITE_THINGS_REFRESHED,
        RECOMMENDED_THINGS_REQUESTED,
        RECOMMENDED_THINGS_LOADED,
        RECOMMENDED_THING_LIKED,
        RECOMMENDED_THINGS_HIDE_REQUESTED,
        ALL_THINGS_REFRESHED
    }

    enum class State {
        LOADING_FAVOURITE_THINGS,
        SHOWING_FAVOURITE_THINGS,
        ADDING_FAVOURITE_THING,
        REMOVING_FAVOURITE_THING,
        REFRESHING_FAVOURITE_THINGS,
        LOADING_RECOMMENDED_THINGS,
        SHOWING_ALL_THINGS,
        REFRESHING_ALL_THINGS,
    }
}
