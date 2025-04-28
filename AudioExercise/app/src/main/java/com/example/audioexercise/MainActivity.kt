package com.example.audioexercise

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioRecord
import android.media.AudioTrack
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import android.content.Context
import android.media.AudioAttributes
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.audioexercise.ui.theme.AudioExerciseTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class AudioRecorderHelper {

    private var audioRecord: AudioRecord? = null
    private var isRecording = false
    private var audioTrack: AudioTrack? = null
    private val bufferSize = AudioRecord.getMinBufferSize(
        SAMPLE_RATE,
        AudioFormat.CHANNEL_IN_MONO,
        AudioFormat.ENCODING_PCM_16BIT
    )

    companion object {
        private const val SAMPLE_RATE = 44100 // 44.1 kHz
    }

    @SuppressLint("MissingPermission")
    fun startRecording(context: Context) {
        if (audioRecord == null) {
            audioRecord = AudioRecord(
                MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize
            )
        }

        audioRecord?.startRecording()
        isRecording = true

        val audioData = ByteArray(bufferSize)
        val outputFile = File(context.getExternalFilesDir(null), "recording.pcm")

        Thread {
            FileOutputStream(outputFile).use { outputStream ->
                while (isRecording) {
                    val read = audioRecord?.read(audioData, 0, audioData.size) ?: 0
                    if (read > 0) {
                        outputStream.write(audioData, 0, read)
                    }
                }
            }
        }.start()
    }

    fun stopRecording() {
        isRecording = false
        audioRecord?.stop()
        audioRecord?.release()
        audioRecord = null
        Log.d("Recording Stopped", "Recording Stopped")
    }

    fun playRecording(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val outputFile = File(context.getExternalFilesDir(null), "recording.pcm")
            if (!outputFile.exists()) {
                Log.e("AudioRecorderHelper", "Recording file not found!")
                return@launch
            }

            val bufferSize = AudioTrack.getMinBufferSize(
                SAMPLE_RATE,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )

            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()

            val audioFormat = AudioFormat.Builder()
                .setSampleRate(SAMPLE_RATE)
                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                .build()

            val audioTrack = AudioTrack(
                audioAttributes,
                audioFormat,
                bufferSize,
                AudioTrack.MODE_STREAM,
                AudioManager.AUDIO_SESSION_ID_GENERATE
            )

            val audioData = ByteArray(bufferSize)
            FileInputStream(outputFile).use { inputStream ->
                audioTrack.play()
                var read: Int
                while (inputStream.read(audioData).also { read = it } > 0) {
                    audioTrack.write(audioData, 0, read)
                }
            }

            audioTrack.stop()
            audioTrack.release()
        }
    }

    fun stopPlayback() {
        audioTrack?.stop()
        audioTrack?.release()
        audioTrack = null
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hasPermissions()
        enableEdgeToEdge()
        setContent {
            AudioExerciseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AudioRecordingExercise(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun hasPermissions(): Boolean {
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            Log.d("DBG", "No audio recorder access")
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 1);
            return true // assuming that the user grants permission
        }
        return true
    }
}

@Composable
fun AudioRecordingExercise(modifier: Modifier = Modifier) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val audioRecorderHelper = AudioRecorderHelper()
    val isRecording = remember { mutableStateOf(false) }
    val isListening = remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text("Hello, This is Audio")

        if (isRecording.value) {
            Button(onClick = {
                audioRecorderHelper.stopRecording()
                isRecording.value = false
            }) {
                Text("Stop Recording")
            }
        } else {
            Button(onClick = {
                audioRecorderHelper.startRecording(context)
                isRecording.value = true
            }) {
                Text("Start Recording")
            }
        }

        Spacer(modifier = Modifier.padding(8.dp))

        if (!isListening.value) {
            Button(onClick = {
                isListening.value = true
                audioRecorderHelper.playRecording(context)
            }) {
                Text("Listen to the Recording")
            }
        } else {
            Button(onClick = {
                isListening.value = false
                audioRecorderHelper.stopPlayback()
            }) {
                Text("Stop listening to the Recording")
            }
        }
    }
}
