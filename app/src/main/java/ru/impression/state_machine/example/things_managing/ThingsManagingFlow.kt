package ru.impression.state_machine.example.things_managing

import ru.impression.state_machine.Flow

class ThingsManagingFlow : Flow<ThingsManagingFlow.State>(State(false)) {

    override fun start() {

        // загружаем любимые вещи
        performAction(LoadFavouriteThings())

        // любимые вещи загружены
        subscribeOnEvent<FavouriteThingsLoaded> {

            // устанавливаем в качестве основного состояния отображение только любимых вещей
            performAction(ShowFavouriteThings(it.things))

            // пользователь запросил удалить любимую вещь
            subscribeOnEvent<FavouriteThingsUnliked> {

                // удаляем любимую вещь
                performAction(RemoveFavouriteThing(it.thing))

                refreshRecommendedThings()
            }

            // пользователь запросил рекомендуемые вещи
            subscribeOnEvent<RecommendedThingsRequested> {

                // загружаем рекомендуемые вещи
                performAction(LoadRecommendedThings())

                // рекомендуемые вещи загружены
                subscribeOnEvent<RecommendedThingsLoaded> {

                    state.manageRecommendedThings = true

                    // устанавливаем в качестве основного состояния отображение любимых и рекомендуемых вещей
                    performAction(ShowRecommendedThings(it.things))

                    // пользователю понравилась рекомендуемая вещь
                    subscribeOnEvent<RecommendedThingsLiked> {

                        // добавляем понравившуюся вещь в любимые
                        performAction(AddFavouriteThing(it.thing))

                        refreshRecommendedThings()
                    }
                }
            }

            // пользователь запросил скрыть рекомендуемые вещи
            subscribeOnEvent<RecommendedThingsHideRequested> {

                // устанавливаем в качестве основного состояния отображение только любимых вещей
                performAction(HideRecommendedThings())

                state.manageRecommendedThings = false
            }
        }
    }

    private fun refreshRecommendedThings() {

        if (state.manageRecommendedThings) {

            // обновляем рекомендованные вещи
            performAction(RefreshRecommendedThings())

            // рекомендованные вещи обновлены
            subscribeOnEvent<RecommendedThingsRefreshed> {

                // отображаем рекомендованные вещи
                performAction(ShowRecommendedThings(it.things))
            }
        }
    }

    class State(
        var manageRecommendedThings: Boolean
    ) : Flow.State()
}

class LoadFavouriteThings : Flow.Action()

class FavouriteThingsLoaded(val things: List<String>) : Flow.Event()

class ShowFavouriteThings(val things: List<String>) : Flow.Action()

class FavouriteThingsUnliked(val thing: String) : Flow.Event()

class RemoveFavouriteThing(val thing: String) : Flow.Action()

class RecommendedThingsRequested : Flow.Event()

class LoadRecommendedThings : Flow.Action()

class RecommendedThingsLoaded(val things: List<String>) : Flow.Event()

class ShowRecommendedThings(val things: List<String>) : Flow.Action()

class RecommendedThingsLiked(val thing: String) : Flow.Event()

class AddFavouriteThing(val thing: String) : Flow.Action()

class RefreshRecommendedThings : Flow.Action()

class RecommendedThingsRefreshed(val things: List<String>) : Flow.Event()

class RecommendedThingsHideRequested : Flow.Event()

class HideRecommendedThings : Flow.Action()