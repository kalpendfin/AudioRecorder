package com.pals.audioplayer.recorder

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import com.pals.audioplayer.interfaces.AudioRecorder
import java.io.File
import java.io.FileOutputStream

class AndroidAudioRecorder(
    private val _mContext: Context
) : AudioRecorder {

    private var _mRecorder: MediaRecorder? = null

    private fun createRecorder(): MediaRecorder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(_mContext)
        } else MediaRecorder()
    }

    override fun start(outputFile: File) {
        createRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(FileOutputStream(outputFile).fd)

            prepare()
            start()

            _mRecorder = this
        }

    }

    override fun stop() {
        _mRecorder?.apply {
            stop()
            release()
        }
        _mRecorder = null
    }
}