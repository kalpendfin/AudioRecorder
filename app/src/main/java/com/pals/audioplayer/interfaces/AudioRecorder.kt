package com.pals.audioplayer.interfaces

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun stop()
}