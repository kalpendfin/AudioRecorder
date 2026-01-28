package com.pals.audioplayer.player

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import com.pals.audioplayer.interfaces.AudioPlayer
import java.io.File

class AndroidAudioPlayer(
    private val _mContext: Context
) : AudioPlayer {

    private var player: MediaPlayer? = null

    override fun playFile(file: File) {
        MediaPlayer.create(_mContext, file.toUri()).apply {
            player = this
            start()
        }
    }

    override fun stop() {
        player?.stop()
        player?.release()
        player = null
    }
}