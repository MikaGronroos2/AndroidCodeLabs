package com.example.bluetoothgraphexercise

import java.util.UUID
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bluetoothgraphexercise.ui.theme.BluetoothGraphExerciseTheme
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.m3.common.rememberM3VicoTheme
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import no.nordicsemi.android.kotlin.ble.client.main.callback.ClientBleGatt
import no.nordicsemi.android.kotlin.ble.core.ServerDevice
import no.nordicsemi.android.kotlin.ble.core.data.BleGattProperty
import no.nordicsemi.android.kotlin.ble.scanner.BleScanner
import no.nordicsemi.android.kotlin.ble.scanner.aggregator.BleScanResultAggregator

class MainActivity : ComponentActivity() {

    private val bleViewModel: BleViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions()
        enableEdgeToEdge()
        setContent {
            BluetoothGraphExerciseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation(modifier = Modifier.padding(innerPadding), bleViewModel)
                }
            }
        }
    }

    private fun requestPermissions() {
        val permissions = mutableListOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissions.add(android.Manifest.permission.BLUETOOTH_SCAN)
            permissions.add(Manifest.permission.BLUETOOTH_CONNECT)
        }

        ActivityCompat.requestPermissions(this, permissions.toTypedArray(), 1)
    }

}



enum class NavigationScreens(val title: String) {
    BTCONNECT("Search"),
    BTGRAPH("Heart Rate Graph")
}

@Composable
fun Navigation(modifier: Modifier, bleViewModel: BleViewModel) {

    val sharedModifier = modifier.fillMaxSize().padding(16.dp)

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationScreens.BTCONNECT.title
    ) {
        composable(NavigationScreens.BTCONNECT.title) { BTConnect(modifier = sharedModifier, bleViewModel, navController) }
        composable(NavigationScreens.BTGRAPH.title) { BTGraph(bleViewModel, modifier = sharedModifier, navController) }
    }

}


@SuppressLint("MissingPermission")
class BleViewModel : ViewModel() {
    companion object {
        val HEART_RATE_SERVICE_UUID = UUID.fromString("0000180d-0000-1000-8000-00805f9b34fb")
        val HEART_RATE_MEASUREMENT_UUID = UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb")
    }

    // Connection
    var savedDevice: ServerDevice? = null
    private var connection: ClientBleGatt? = null
    val scanResults = MutableLiveData<List<ServerDevice>>(emptyList())
    val isScanning = MutableLiveData(false)
    val connectionState = MutableLiveData<String>()
    val _bpmValue = MutableLiveData<Int>()

    fun scanDevices(context: Context) {
        val permission = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            android.Manifest.permission.BLUETOOTH_SCAN
        } else {
            android.Manifest.permission.BLUETOOTH
        }

        if (ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val aggregator = BleScanResultAggregator()
            isScanning.value = true
            BleScanner(context).scan()
                .map { aggregator.aggregateDevices(it) }
                .onEach { devices ->
                    scanResults.value = devices
                    isScanning.value = false
                }
                .launchIn(viewModelScope)
        } else {
            Log.e("BleViewModel", "Bluetooth is not enabled")
        }
    }

    suspend fun connectToDevice(context: Context, device: ServerDevice) {
        try {
            val connection = ClientBleGatt.connect(context, device.address, viewModelScope)
            val services = connection.discoverServices()
            connectionState.postValue("Connected to ${device.name ?: "Unknown"}")
            val heartRateService = services.findService(HEART_RATE_SERVICE_UUID)

            if (heartRateService == null) {
                Log.e("ConnectToDevice", "Heart Rate Service not found")
                return
            }

            val bpmCharacteristic = heartRateService.findCharacteristic(HEART_RATE_MEASUREMENT_UUID)
            if (bpmCharacteristic == null) {
                Log.e("ConnectToDevice", "Heart Rate Measurement Characteristic not found")
                return
            }

            if (bpmCharacteristic.properties.contains(BleGattProperty.PROPERTY_NOTIFY) ||
                bpmCharacteristic.properties.contains(BleGattProperty.PROPERTY_INDICATE)) {
                try {
                    Log.d("ConnectToDevice", "Enabling notifications/indications")
                    bpmCharacteristic.getNotifications()
                        .onEach { data ->
                            val bpmDataValue = data.value
                            if (bpmDataValue != null && bpmDataValue.isNotEmpty()) {
                                val bpmValue = bpmDataValue[1].toInt()
                                _bpmValue.value = bpmValue
                                Log.d("ConnectToDevice", "Parsed BPM Value: $bpmValue")
                            } else {
                                Log.e("ConnectToDevice", "No data received or characteristic value is empty")
                            }
                        }
                        .launchIn(viewModelScope)

                } catch (e: Exception) {
                    Log.e("ConnectToDevice", "Error enabling notifications: ${e.message}")
                }
            } else {
                Log.e("ConnectToDevice", "Characteristic does not support notifications or indications")
            }
        } catch (e: Exception) {
            Log.e("ConnectToDevice", "Error during connection: ${e.message}")
        }
    }

}

@Composable
fun BTConnect(modifier: Modifier, bleViewModel: BleViewModel, navController: NavController) {
    val context = LocalContext.current
    val devices by bleViewModel.scanResults.observeAsState(emptyList())
    val isScanning by bleViewModel.isScanning.observeAsState(false)
    val connectionState by bleViewModel.connectionState.observeAsState("")
    val bpmValue by bleViewModel._bpmValue.observeAsState(0)

    Column(modifier = modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = connectionState)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "BPM: $bpmValue")


        Button(
            onClick = { bleViewModel.scanDevices(context) },
            enabled = !isScanning,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isScanning) "Scanning..." else "Start Scanning")
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(devices) { result ->
                val name = result.name ?: "Unknown"
                val address = result.address

                Button(
                    onClick = {

                        bleViewModel.savedDevice = result
                        navController.navigate(NavigationScreens.BTGRAPH.title)

                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "$name\n$address")
                }
            }
        }
    }
}

@Composable
fun BTGraph(bleViewModel: BleViewModel, modifier: Modifier, navController: NavController) {

    val bpmValue by bleViewModel._bpmValue.observeAsState()
    val context: Context = LocalContext.current

    // List for Vico
    val bpmValues = remember { mutableStateListOf<Float>() }
    val modelProducer = remember { CartesianChartModelProducer() }

    LaunchedEffect(Unit) {
        if (bleViewModel.savedDevice != null) {
            bleViewModel.connectToDevice(context, bleViewModel.savedDevice!!)
        }
    }

    LaunchedEffect(Unit) {
        modelProducer.runTransaction {
            lineSeries { series(bpmValues.map { it }) }
        }
    }

    // Whenever bpmValue changes, add it to the list for vico
    LaunchedEffect(bpmValue) {

        bpmValue?.let {
            bpmValues.add(it.toFloat())
            // Limit the list to the last 50 values
            if (bpmValues.size > 50) {
                bpmValues.removeAt(0)
            }

            // Update the chart model only if bpmValues is not empty
            if (bpmValues.isNotEmpty()) {
                modelProducer.runTransaction {
                    lineSeries { series(bpmValues.map { it }) }
                }
            }
        }
    }

    Column(modifier = modifier) {
        Text("BTGraph")
        Text("BPM Value: $bpmValue")

        if (bpmValues.isNotEmpty()) {
            JetpackComposeBasicLineChart(
                modelProducer = modelProducer,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        } else {
            Text("No data available to display the chart.")
        }
    }
}

@Composable
fun JetpackComposeBasicLineChart(
    modelProducer: CartesianChartModelProducer,
    modifier: Modifier = Modifier,
) {
    CartesianChartHost(
        chart =
            rememberCartesianChart(
                rememberLineCartesianLayer(),
                startAxis = VerticalAxis.rememberStart(),
                bottomAxis = HorizontalAxis.rememberBottom(),
            ),
        modelProducer = modelProducer,
        modifier = modifier,
    )
}