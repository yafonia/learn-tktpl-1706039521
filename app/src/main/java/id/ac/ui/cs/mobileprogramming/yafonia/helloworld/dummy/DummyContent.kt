package id.ac.ui.cs.mobileprogramming.yafonia.helloworld.dummy

import java.util.ArrayList
import java.util.HashMap

object DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<DummyItem> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, DummyItem> = HashMap()
    private val ITEM_DUMMY: MutableMap<String, String> = HashMap()

    init {
        listContact()
        // Add some sample items.
        var counter = 1
        for ((title, detail) in ITEM_DUMMY) {
            addItem(createDummyItem(counter, title, detail))
            counter++
        }
    }

    private fun listContact(){
        ITEM_DUMMY["Mila"] = "0877777777"
        ITEM_DUMMY["Caca"] = "0866666666"
        ITEM_DUMMY["Rindu"] = "0855555555"

    }

    private fun addItem(item: DummyItem) {
        ITEMS.add(item)
        ITEM_MAP[item.id] = item
    }

    private fun createDummyItem(position: Int, name: String, number: String): DummyItem {
        return DummyItem(position.toString(), name, number)
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0..position - 1) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class DummyItem(val id: String, val name: String, val number: String) {
        override fun toString(): String = name
    }
}
