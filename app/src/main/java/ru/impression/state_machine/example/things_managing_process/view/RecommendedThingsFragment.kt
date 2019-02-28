package ru.impression.state_machine.example.things_managing_process.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import ru.impression.state_machine.R

private const val KEY_ITEMS = "ITEMS"

class RecommendedThingsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        View.inflate(context, R.layout.fragment_recommended_things, null).also {
            it.findViewById<ListView>(R.id.recommended_things_list_view).adapter = ThingsAdapter(context!!).apply {
                addAll(arguments!!.getStringArrayList(KEY_ITEMS)!!)

            }
        }

    companion object {
        fun newInstance() = RecommendedThingsFragment()
    }

}