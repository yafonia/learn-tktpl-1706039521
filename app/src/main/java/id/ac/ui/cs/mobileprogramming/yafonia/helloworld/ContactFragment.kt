package id.ac.ui.cs.mobileprogramming.yafonia.helloworld

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import id.ac.ui.cs.mobileprogramming.yafonia.helloworld.dummy.DummyContent


class ContactFragment : Fragment() {

    companion object {
        fun newInstance() = ContactFragment()
    }

    private lateinit var viewModel: ContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.contact_fragment, container, false)
        viewModel = ViewModelProviders.of(requireActivity()).get(ContactViewModel::class.java)
        val number = view.findViewById<View>(R.id.number) as TextView
        viewModel.contacts.observe(this,  Observer<DummyContent.DummyItem> { contacts ->
            number.text = contacts.number
        })

        return view
    }

}
