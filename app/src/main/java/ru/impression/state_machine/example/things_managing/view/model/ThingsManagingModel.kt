package ru.impression.state_machine.example.things_managing.view.model

import android.arch.lifecycle.ViewModel
import com.maximeroussy.invitrode.WordGenerator
import ru.impression.state_machine.Flow
import ru.impression.state_machine.FlowPerformer
import ru.impression.state_machine.example.things_managing.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

class ThingsManagingModel : ViewModel(), FlowPerformer<ThingsManagingFlow> {

    override val flow = ThingsManagingFlow::class.java

    private val favouriteThings = arrayListOf("vodka", "spice", "bitches", "money", "black-jack", "cocaine")

    private val recommendedThings
        get () = ArrayList<String>().apply {
            for (i in 0..6) {
                add(WordGenerator().newWord(Random().nextInt(10) + 3))
            }
        }

    private lateinit var loadingRecommendedThingsThread: Thread

    init {
        attachToFlow()
    }

    override fun onCleared() {
        detachFromFlow()
        super.onCleared()
    }

    override fun performAction(action: Flow.Action) {
        when (action) {
            is LoadFavouriteThings -> thread {
                Thread.sleep(1000)
                onEvent(FavouriteThingsLoaded(favouriteThings))
            }
            is LoadRecommendedThings -> {
                loadingRecommendedThingsThread = Thread(Runnable {
                    try {
                        Thread.sleep(1000)
                        onEvent(RecommendedThingsLoaded(recommendedThings))
                    } catch (e: InterruptedException) {
                    }
                })
                loadingRecommendedThingsThread.start()
            }
            is CancelLoadingRecommendedThings -> loadingRecommendedThingsThread.interrupt()
        }
    }

}