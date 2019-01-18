package lestwald.insectkiller

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class PageFragment : Fragment() {
    lateinit var namesTextView: TextView
    lateinit var scoresTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_page, container, false)
        namesTextView = view.findViewById(R.id.namesTextView)
        scoresTextView = view.findViewById(R.id.scoresTextView)
        return view
    }
}
