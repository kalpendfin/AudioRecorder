package com.pals.audioplayer.interfaces

import java.io.File

interface AudioPlayer {
    fun playFile(file: File)
    fun stop()
}