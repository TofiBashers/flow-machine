package ru.impression.flow

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.ReplaySubject
import java.util.concurrent.ConcurrentHashMap

//internal
val DISPOSABLES: ConcurrentHashMap<String, CompositeDisposable> = ConcurrentHashMap()

//internal
val EVENT_SUBJECTS: ConcurrentHashMap<String, BehaviorSubject<Flow.Event>> = ConcurrentHashMap()

internal val ACTION_SUBJECTS: ConcurrentHashMap<String, ReplaySubject<Flow.Action>> = ConcurrentHashMap()