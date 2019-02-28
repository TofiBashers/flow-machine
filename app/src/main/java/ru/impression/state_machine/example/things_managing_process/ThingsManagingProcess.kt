package ru.impression.state_machine.example.things_managing_process

import ru.impression.state_machine.BusinessProcess
import ru.impression.state_machine.example.things_managing_process.event.*
import ru.impression.state_machine.example.things_managing_process.state.*

class ThingsManagingProcess : BusinessProcess() {

    override fun begin() {

        updateState(LoadingFavouriteThings())

        awaitEvent<FavouriteThingsLoaded> {

            updateState(DisplayingOnlyFavouriteThings(it.things), true)

            manageFavouriteThings()

            manageRecommendedThings()
        }
    }

    private fun manageFavouriteThings() =

        awaitEvent<FavouriteThingUnliked> {

            updateState(DeletingFavouriteThing(it.thing))

            awaitEvent<FavouriteThingDeleted> {

                updateState(primaryState)
            }
        }

    private fun manageRecommendedThings() {

        awaitEvent<RecommendedThingsLoadRequested> {

            updateState(LoadingRecommendedThings())

            awaitEvent<RecommendedThingsLoaded> {

                updateState(
                    DisplayingAllThings(
                        (primaryState as DisplayingOnlyFavouriteThings).things
                    ),
                    true
                )

                awaitEvent<RecommendedThingLiked> {

                    updateState(AddingFavouriteThing())

                    awaitEvent<FavouriteThingAdded> {

                        updateState(primaryState)
                    }
                }
            }
        }

        awaitEvent<RecommendedThingsHideRequested> {

            updateState(
                DisplayingOnlyFavouriteThings(
                    (primaryState as DisplayingAllThings).things
                ),
                true
            )
        }

    }
}
