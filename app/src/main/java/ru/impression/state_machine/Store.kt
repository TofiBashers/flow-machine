package ru.impression.state_machine

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import java.util.HashMap

internal val DISPOSABLES: HashMap<String, CompositeDisposable> = HashMap()

internal val EVENT_SUBJECTS: HashMap<String, HashMap<Enum<*>, BehaviorSubject<Unit>>> = HashMap()

internal val STATE_SUBJECTS: HashMap<String, ArrayList<BehaviorSubject<NewStateReceiving>>> = HashMap()
