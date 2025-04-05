package com.example.blescannerapp

import android.Manifest
import android.bluetooth.BluetoothManager
import android.bluetooth.le.*
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.*
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions()

        setContent {
            val viewModel = BleViewModel()
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                BleScannerScreen(
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }

    private fun requestPermissions() {
        val permissions = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissions.add(Manifest.permission.BLUETOOTH_SCAN)
            permissions.add(Manifest.permission.BLUETOOTH_CONNECT)
        }

        ActivityCompat.requestPermissions(this, permissions.toTypedArray(), 1)
    }
}

class BleViewModel : ViewModel() {
    val scanResults = MutableLiveData<List<ScanResult>>(emptyList())
    val isScanning = MutableLiveData(false)

    private val results = HashMap<String, ScanResult>()

    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            results[result.device.address] = result
        }
    }

    fun scanDevices(scanner: BluetoothLeScanner?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (scanner == null) {
                isScanning.postValue(false)
                return@launch
            }

            try {
                isScanning.postValue(true)
                results.clear()

                val settings = ScanSettings.Builder()
                    .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                    .build()

                scanner.startScan(null, settings, scanCallback)
                delay(3000)
                scanner.stopScan(scanCallback)

                scanResults.postValue(results.values.toList())
                isScanning.postValue(false)

            } catch (e: SecurityException) {
                isScanning.postValue(false)
                scanResults.postValue(emptyList())
            }
        }
    }
}

@Composable
fun BleScannerScreen(
    viewModel: BleViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

    val scanner: BluetoothLeScanner? = if (
        ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN)
        == PackageManager.PERMISSION_GRANTED
    ) {
        bluetoothManager.adapter.bluetoothLeScanner
    } else null

    val devices by viewModel.scanResults.observeAsState(emptyList())
    val isScanning by viewModel.isScanning.observeAsState(false)

    Column(modifier = modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Button(
            onClick = { viewModel.scanDevices(scanner) },
            enabled = !isScanning,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isScanning) "Skannataan..." else "Aloita skannaus")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(devices) { result ->
                val name = result.device.name ?: "Tuntematon"
                val address = result.device.address
                val rssi = result.rssi
                val color = if (result.isConnectable) Color.Black else Color.Gray

                Text(
                    text = "$name\n$address\nRSSI: $rssi dBm",
                    color = color,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }
    }
}
