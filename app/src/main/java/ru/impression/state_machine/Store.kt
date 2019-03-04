package ru.impression.state_machine

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import java.util.HashMap
import java.util.concurrent.ConcurrentHashMap

internal val DISPOSABLES: ConcurrentHashMap<String, CompositeDisposable> = ConcurrentHashMap()

internal val FLOW_PERFORMER_ATTACH_SUBJECTS: ConcurrentHashMap<String, BehaviorSubject<Int>> = ConcurrentHashMap()

internal val EVENT_SUBJECTS: ConcurrentHashMap<String, HashMap<Enum<*>, BehaviorSubject<Unit>>> = ConcurrentHashMap()

internal val STATE_SUBJECTS: ConcurrentHashMap<String, ArrayList<BehaviorSubject<NewStateReceiving>>> = ConcurrentHashMap()