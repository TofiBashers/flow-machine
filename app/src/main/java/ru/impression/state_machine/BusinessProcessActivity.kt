package ru.impression.state_machine

import android.support.v7.app.AppCompatActivity

abstract class BusinessProcessActivity<E : Enum<E>, S : Enum<S>> : AppCompatActivity() {

    protected lateinit var primaryState: S

    protected lateinit var currentState: S

    abstract fun onStateUpdated()

    fun makeEvent(event: E) {

    }

}