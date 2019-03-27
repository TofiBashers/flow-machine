package ru.impression.mindflow

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.ConcurrentHashMap

@PublishedApi
internal val DISPOSABLES: ConcurrentHashMap<String, CompositeDisposable> = ConcurrentHashMap()

@PublishedApi
internal val EVENT_SUBJECTS: ConcurrentHashMap<String, BehaviorSubject<FlowEvent>> = ConcurrentHashMap()

internal val ACTION_SUBJECTS: ConcurrentHashMap<String, BehaviorSubject<FlowAction>> = ConcurrentHashMap()