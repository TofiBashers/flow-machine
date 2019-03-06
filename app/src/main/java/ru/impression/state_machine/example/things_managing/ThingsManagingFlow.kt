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
        }

        // пользователь запросил удалить любимую вещь
        subscribeOnEvent<FavouriteThingsUnliked> {

            // удаляем любимую вещь
            performAction(RemoveFavouriteThing(it.thing))
        }

        // пользователь запросил рекомендуемые вещи
        subscribeOnEvent<RecommendedThingsRequested> {

            state.recommendedThingsEnabled = true

            loadRecommendedThings()
        }

        subscribeOnEvent<FavouriteThingsUpdated> {

            if (state.recommendedThingsEnabled) {

                if (state.loadingRecommendedThings) {

                    performAction(CancelLoadingRecommendedThings())
                }

                loadRecommendedThings()
            }
        }

        // рекомендуемые вещи загружены
        subscribeOnEvent<RecommendedThingsLoaded> {

                state.loadingRecommendedThings = false

                // устанавливаем в качестве основного состояния отображение любимых и рекомендуемых вещей
                performAction(ShowRecommendedThings(it.things))

                state.showingRecommendedThings = true
        }

        // пользователю понравилась рекомендуемая вещь
        subscribeOnEvent<RecommendedThingsLiked> {

            // добавляем понравившуюся вещь в любимые
            performAction(AddFavouriteThing(it.thing))
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

            state.recommendedThingsEnabled = false
        }
    }

    private fun loadRecommendedThings() {

        // загружаем рекомендуемые вещи
        performAction(LoadRecommendedThings())

        state.loadingRecommendedThings = true
    }

    class State : Flow.State() {
        var recommendedThingsEnabled: Boolean = false
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

class RecommendedThingsHideRequested : Flow.Event()

class HideRecommendedThings : Flow.Action()