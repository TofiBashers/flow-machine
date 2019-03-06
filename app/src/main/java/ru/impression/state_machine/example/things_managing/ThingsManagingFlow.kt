package ru.impression.state_machine.example.things_managing

import ru.impression.state_machine.Flow

class ThingsManagingFlow : Flow<ThingsManagingFlow.State>(State()) {

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
            }

            // пользователь запросил рекомендуемые вещи
            subscribeOnEvent<RecommendedThingsRequested> {

                // загружаем рекомендуемые вещи
                performAction(LoadRecommendedThings())

                state.loadingRecommendedThings = true

                // рекомендуемые вещи загружены
                subscribeOnEvent<RecommendedThingsLoaded> {

                    if (state.loadingRecommendedThings) {

                        // устанавливаем в качестве основного состояния отображение любимых и рекомендуемых вещей
                        performAction(ShowRecommendedThings(it.things))

                        state.showingRecommendedThings = true

                        // пользователю понравилась рекомендуемая вещь
                        subscribeOnEvent<RecommendedThingsLiked> {

                            // добавляем понравившуюся вещь в любимые
                            performAction(AddFavouriteThing(it.thing))
                        }
                    }
                }
            }

            subscribeOnEvent<FavouriteThingsUpdated> {

                if (state.showingRecommendedThings) {

                    // обновляем рекомендованные вещи
                    performAction(LoadRecommendedThings())

                    state.loadingRecommendedThings = true

                    // рекомендованные вещи обновлены
                    subscribeOnEvent<RecommendedThingsRefreshed> {

                        if (state.loadingRecommendedThings) {

                            // отображаем рекомендованные вещи
                            performAction(ShowRecommendedThings(it.things))
                        }
                    }
                }
            }

            // пользователь запросил скрыть рекомендуемые вещи
            subscribeOnEvent<RecommendedThingsHideRequested> {

                if (state.loadingRecommendedThings) {

                    // устанавливаем в качестве основного состояния отображение только любимых вещей
                    performAction(CancelLoadingRecommendedThings())

                    state.loadingRecommendedThings = false

                } else if (state.showingRecommendedThings) {

                    // устанавливаем в качестве основного состояния отображение только любимых вещей
                    performAction(HideRecommendedThings())

                    state.showingRecommendedThings = false
                }
            }
        }
    }

    class State : Flow.State() {
        var loadingRecommendedThings: Boolean = false
        var showingRecommendedThings: Boolean = false
    }
}

class LoadFavouriteThings : Flow.Action()

class FavouriteThingsLoaded(val things: List<String>) : Flow.Event()

class ShowFavouriteThings(val things: List<String>) : Flow.Action()

class FavouriteThingsUnliked(val thing: String) : Flow.Event()

class RemoveFavouriteThing(val thing: String) : Flow.Action()

class RecommendedThingsRequested : Flow.Event()

class LoadRecommendedThings : Flow.Action()

class RecommendedThingsLoaded(val things: List<String>) : Flow.Event()

class CancelLoadingRecommendedThings : Flow.Action()

class ShowRecommendedThings(val things: List<String>) : Flow.Action()

class RecommendedThingsLiked(val thing: String) : Flow.Event()

class AddFavouriteThing(val thing: String) : Flow.Action()

class FavouriteThingsUpdated : Flow.Event()

class RecommendedThingsRefreshed(val things: List<String>) : Flow.Event()

class RecommendedThingsHideRequested : Flow.Event()

class HideRecommendedThings : Flow.Action()