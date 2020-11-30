package id.ac.ui.cs.mobileprogramming.yafonia.helloworld

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*

open class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_scan_wifi.setOnClickListener{
            startActivity(Intent(this, WifiActivity::class.java))
            finish()
        }
    }

}
