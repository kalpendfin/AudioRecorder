package com.pals.audioplayer.ui.screen

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pals.audioplayer.R
import com.pals.audioplayer.dialog.PermissionDialog
import com.pals.audioplayer.player.AndroidAudioPlayer
import com.pals.audioplayer.recorder.AndroidAudioRecorder
import com.pals.audioplayer.ui.model.AudioRecording
import com.pals.audioplayer.ui.theme.AudioPlayerTheme
import com.pals.audioplayer.ui.viewmodel.MainViewModel
import com.pals.audioplayer.util.RecordAudioPermissionTextProvider
import java.io.File
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {

    // Initialize the recorder
    private val recorder by lazy {
        AndroidAudioRecorder(_mContext = applicationContext)
    }

    // Initialize the player
    private val player by lazy {
        AndroidAudioPlayer(_mContext = applicationContext)
    }

    // Store the audio file
//    private var audioFile: File? = null
    private lateinit var audioFilePath: String


    // List of saved recordings
    private val recordings = mutableStateListOf<AudioRecording>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            loadRecordings()
            AudioPlayerTheme {

                val viewModel = viewModel<MainViewModel>()
                val dialogQueue = viewModel.visiblePermissionDialogQueue

                val cameraPermissionResultLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        viewModel.onPermissionResult(
                            permission = Manifest.permission.RECORD_AUDIO, isGranted = isGranted
                        )
                    })

                var isRecording by remember { mutableStateOf(false) }
                var buttonText by remember { mutableStateOf("Start Recording") }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(onClick = {
                        if (isRecording) {
                            isRecording = false
                            buttonText = "Start Recording"
                            recorder.stop()
                            loadRecordings()

                        } else {
                            if (applicationContext.checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                                cameraPermissionResultLauncher.launch(
                                    Manifest.permission.RECORD_AUDIO
                                )
                            } else {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                    isRecording = true
                                    buttonText = "Stop Recording"
                                    audioFilePath = getNewFilePath()

                                    File(cacheDir, "audio.mp3").also {
                                        recorder.start(File(audioFilePath))
                                    }
                                }
                            }
                        }
                    }) {
                        Text(text = buttonText)
                    }

                    RecordListScreen(recordings, player)
                }

                dialogQueue.reversed().forEach { permission ->
                        PermissionDialog(
                            permissionTextProvider = when (permission) {
                            Manifest.permission.RECORD_AUDIO -> {
                                RecordAudioPermissionTextProvider()
                            }

                            else -> return@forEach
                        }, isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                            permission
                        ), onDismiss = viewModel::dismissDialog, onOkClick = {
                            viewModel.dismissDialog()
                        }, onGoToAppSettingsClick = {
                            openAppSettings()
                            viewModel.dismissDialog()
                        })
                    }
            }
        }
    }

    // Loads all previously recorded .3gp files from app storage
    private fun loadRecordings() {
        recordings.clear()
        val dir = getExternalFilesDir(null)
        dir?.listFiles()?.filter { it.name.endsWith(".3gp") }?.sortedBy { it.name }?.forEach {
            recordings.add(AudioRecording(it.absolutePath, it.name))
        }
    }

    // Generate a unique file path for each new recording
    private fun getNewFilePath(): String {
        val dir = getExternalFilesDir(null)
        val formatter = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault())
        val timeStamp = formatter.format(Date())
        return File(dir, "File_$timeStamp.3gp").absolutePath
    }


}

// Let open App from setting and allow user to grant permission
fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}

@Composable
fun RecordingItem(
    fileName: String, filePath: String, player: AndroidAudioPlayer
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                player.playFile(File(filePath) ?: return@Button)
            }) {
                Image(
                    painter = painterResource(R.drawable.play),
                    contentDescription = "play",
                    modifier = Modifier.size(40.dp)
                )
            }

//            Spacer(modifier = Modifier.size(8.dp))
            Text(fileName)
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}

@Composable
fun RecordListScreen(
    recordings: List<AudioRecording>, player: AndroidAudioPlayer
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn {
            items(recordings.size) { index ->
                RecordingItem(
                    recordings[index].fileName, recordings[index].filePath, player
                )
            }
        }
    }
}