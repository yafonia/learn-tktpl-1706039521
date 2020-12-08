package id.ac.ui.cs.mobileprogramming.yafonia.helloworld

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private external fun randomizeNumber(): Int

    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        val button = findViewById(R.id.submit) as Button

        button.setOnClickListener {
            printResponse()
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun printHello(name: String): String {
        return "Hello " + name + "! You've done your best today! There are " + randomizeNumber().toString() + " people out there rooting for you<3"
    }

    fun printResponse() {
        var name = findViewById(R.id.name) as EditText
        var textResponse = findViewById(R.id.response) as TextView
        textResponse.text = printHello(name.text.toString())
    }

}
