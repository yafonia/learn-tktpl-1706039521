package id.ac.ui.cs.mobileprogramming.yafonia.helloworld

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var startTimeMillis: Long = 600000
    private lateinit var countdownTimer: CountDownTimer
    private var timerIsRunning: Boolean = false
    private var timeLeftMillis: Long = startTimeMillis
    private var endTimeMillis: Long = 0

    private lateinit var countdownText: TextView
    private lateinit var startPauseButton: Button
    private lateinit var resetButton: Button
    private lateinit var exitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        countdownText = findViewById(R.id.countdownText) as TextView
        countdownText.setText("00.00")
        startPauseButton = findViewById(R.id.startButton) as Button
        resetButton = findViewById(R.id.resetButton) as Button
        exitButton = findViewById(R.id.exitButton) as Button

        startPauseButton.setOnClickListener {
            if (timerIsRunning) {
                pauseTimer()
            }
            else {
                startTimer()
            }
        }
        resetButton.setOnClickListener {
            resetTimer()
        }
        exitButton.setOnClickListener{
            finish()
        }
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onStop() {
        val prefs: SharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putLong("timeLeftMillis", timeLeftMillis)
        editor.putBoolean("timerIsRunning", timerIsRunning)
        editor.putLong("endTimeMillis", endTimeMillis)
        editor.apply()

    }

    override fun onStart() {
        super.onStart()
        val prefs: SharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        timeLeftMillis = prefs.getLong("timeLeftMillis", startTimeMillis)
        timerIsRunning = prefs.getBoolean("timerIsRunning", false)
        updateCountdownText()

        if (timerIsRunning) {
            endTimeMillis = prefs.getLong("endTimeMillis",0)
            timeLeftMillis = endTimeMillis - System.currentTimeMillis()

            if (timeLeftMillis < 0) {
                timeLeftMillis = 0
                timerIsRunning = false
                updateCountdownText()
            }

            else {
                startTimer()
            }
        }

    }

    override fun onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back press disabled!", Toast.LENGTH_SHORT).show();
    }

    private fun startTimer() {
        endTimeMillis = System.currentTimeMillis() + timeLeftMillis
        countdownTimer = object: CountDownTimer(timeLeftMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftMillis = millisUntilFinished
                updateCountdownText()
            }

            override fun onFinish() {
                timerIsRunning = false
                startPauseButton.setText("Start")
            }
        }.start()
        timerIsRunning = true
        startPauseButton.setText("Pause")

    }

    private fun pauseTimer() {
        countdownTimer.cancel()
        timerIsRunning = false
        startPauseButton.setText("Start")

    }

    private fun resetTimer() {
        countdownTimer.cancel()
        timerIsRunning = false
        timeLeftMillis = startTimeMillis
        updateCountdownText()
    }

    private fun updateCountdownText() {
        val minute = (timeLeftMillis/1000) / 60
        val second = (timeLeftMillis/1000) % 60
        val countDownTime: String = minute.toString() + "." + second.toString()
        countdownText.setText(countDownTime)
    }

}
