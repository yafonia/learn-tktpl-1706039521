package id.ac.ui.cs.mobileprogramming.yafonia.helloworld

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.ac.ui.cs.mobileprogramming.yafonia.helloworld.dummy.DummyContent

class ContactViewModel : ViewModel() {
    val contacts = MutableLiveData<DummyContent.DummyItem>()

    fun setContacts(contacts: DummyContent.DummyItem){
        this.contacts.value = contacts
    }
}
