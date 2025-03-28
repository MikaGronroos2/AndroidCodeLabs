package com.example.bluetoothexercise

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.MutableLiveData
import com.example.bluetoothexercise.ui.theme.BluetoothExerciseTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private var mBluetoothAdapter: BluetoothAdapter? = null
    private val SCAN_PERIOD: Long = 10000 // Define your scan period
    private val fScanning = MutableLiveData<Boolean>()
    private val scanResults = MutableLiveData<List<ScanResult>>()
    private val mResults = java.util.HashMap<String, ScanResult>()
    private val leScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            mResults[result.device.address] = result
        }

        override fun onBatchScanResults(results: List<ScanResult>) {
            for (result in results) {
                mResults[result.device.address] = result
            }
        }

        override fun onScanFailed(errorCode: Int) {
            Log.e("DBG", "Scan failed with error: $errorCode")
        }
    }

    private fun hasPermissions(): Boolean {
        if (mBluetoothAdapter == null || !mBluetoothAdapter!!.isEnabled) {
            Log.d("DBG", "No Bluetooth LE capability")
            return false
        } else if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED ||
            checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            Log.d("DBG", "Missing permissions")
            requestPermissions(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT
            ), 1)
            return false
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        mBluetoothAdapter = bluetoothManager.adapter

        if (hasPermissions()) {
            val bluetoothLeScanner = mBluetoothAdapter?.bluetoothLeScanner
            if (bluetoothLeScanner != null) {
                scanDevices(bluetoothLeScanner)
            }
        }

        enableEdgeToEdge()
        setContent {
            BluetoothExerciseTheme {
            }
        }
    }

    private fun scanDevices(scanner: BluetoothLeScanner) {
        CoroutineScope(Dispatchers.IO).launch {
            if (checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED) {
                fScanning.postValue(true)
                val settings = ScanSettings.Builder()
                    .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                    .setReportDelay(0)
                    .build()
                scanner.startScan(null, settings, leScanCallback)
                delay(SCAN_PERIOD)
                scanner.stopScan(leScanCallback)
                scanResults.postValue(mResults.values.toList())
                fScanning.postValue(false)
            } else {
                Log.d("DBG", "Missing BLUETOOTH_SCAN permission")
            }
        }
    }
}