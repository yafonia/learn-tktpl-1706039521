package id.ac.ui.cs.mobileprogramming.yafonia.helloworld

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.app.Fragment
import android.util.Log
import kotlinx.android.synthetic.main.activity_wifi.*
import com.github.kittinunf.fuel.gson.jsonBody
import com.github.kittinunf.fuel.httpPost
import org.json.JSONObject

open class WifiActivity : AppCompatActivity() {

    companion object {
        private const val PERMISSION_REQUEST_CODE_ACCESS_COARSE_LOCATION = 120
    }

    private val wifiManager: WifiManager
        get() = this.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    private var listFragment: WifiListFragment? = null
    private var detailFragment: WifiDetailFragment? = null

    private var listFragmentVisible: Boolean = false
    private var wifiReceiverRegistered: Boolean = false

    private val wifiReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val results = wifiManager.scanResults
            if (listFragmentVisible && results != null) {
                listFragment?.updateItems(results)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi)
        setTitle(R.string.title_wifi)
        transitionToList()

        //Wifiを許可
        if (!wifiManager.isWifiEnabled) {
            Toast.makeText(this, R.string.prompt_enabling_wifi, Toast.LENGTH_SHORT).show()
            wifiManager.isWifiEnabled = true
        }

        btn_refresh.setOnClickListener{
            refreshList()
        }
        btn_send_wifi.setOnClickListener{
            sendToPipedream()
        }
    }

    override fun onStart() {
        super.onStart()
        startScanning()
    }

    fun onResumeFragment(fragment: Fragment) {
        listFragmentVisible = false

        if (fragment == listFragment) {
            listFragmentVisible = true

            refreshList()
        }
    }

    override fun onStop() {
        stopScanning()
        super.onStop()
    }

    private fun startScanning() {
        if (checkPermissions()) {
            registerReceiver(wifiReceiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
            wifiReceiverRegistered = true
        }
    }

    private fun stopScanning() {
        if (wifiReceiverRegistered) {
            unregisterReceiver(wifiReceiver)
            wifiReceiverRegistered = false
        }
    }

    private fun transition(fragment: Fragment, add: Boolean = false) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.layout_frame, fragment)
        if (add) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    fun transitionToList() {
        listFragment = WifiListFragment.newInstance()
        transition(requireNotNull(listFragment))
    }

    fun transitionToDetail(item: WifiStation) {
        detailFragment = WifiDetailFragment.newInstance(item)
        transition(requireNotNull(detailFragment), true)
    }

    private fun refreshList() {
        listFragment?.clearItems()
        wifiManager.startScan()
    }

    private fun checkPermissions(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
            && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                PERMISSION_REQUEST_CODE_ACCESS_COARSE_LOCATION
            )
            return false
        } else {
            return true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE_ACCESS_COARSE_LOCATION -> {
                startScanning()
            }
        }
    }

    private fun sendToPipedream(){
        val listOfWifiNames = wifiManager.scanResults.map {
            it.SSID
        }
        val bodyJson = JSONObject("""{"List of Wifis":${listOfWifiNames.toString()}}""")
        val url = "https://0b253054dbc873e487ffefee70e80f4f.m.pipedream.net/"
            .httpPost().jsonBody(bodyJson).response {
                    request, response, result ->
                when(result.component2() ) {
                    null -> {
                        val ex = result.get()
                    }
                    else -> {
                        Log.e("fail", "${response}")
                    }
                }
            }
        url.join()
        Toast.makeText(applicationContext,R.string.send_to_pipedream,Toast.LENGTH_SHORT).show()
    }
}