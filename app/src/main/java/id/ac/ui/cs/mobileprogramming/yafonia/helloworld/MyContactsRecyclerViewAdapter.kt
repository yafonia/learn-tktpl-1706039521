package id.ac.ui.cs.mobileprogramming.yafonia.helloworld

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity


import id.ac.ui.cs.mobileprogramming.yafonia.helloworld.ContactsFragment.OnListFragmentInteractionListener
import id.ac.ui.cs.mobileprogramming.yafonia.helloworld.dummy.DummyContent.DummyItem

import kotlinx.android.synthetic.main.fragment_contacts.view.*

class MyContactsRecyclerViewAdapter(
    private val mValues: List<DummyItem>,
    private val viewModel: ContactViewModel
) : RecyclerView.Adapter<MyContactsRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_contacts, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mContentView.text = item.name

        with(holder.mView) {
            tag = item
            setOnClickListener{
                viewModel?.setContacts(item)
                (holder.itemView.context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.list_contacts_fragment, ContactFragment()).addToBackStack(null).commit()
            }
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mContentView: TextView = mView.content

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
