package ru.impression.flow_machine

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.ConcurrentHashMap

//internal
val DISPOSABLES: ConcurrentHashMap<String, CompositeDisposable> = ConcurrentHashMap()

//internal
val EVENT_SUBJECTS: ConcurrentHashMap<String, BehaviorSubject<Flow.Event>> = ConcurrentHashMap()

internal val ACTION_SUBJECTS: ConcurrentHashMap<String, BehaviorSubject<Flow.Action>> = ConcurrentHashMap()