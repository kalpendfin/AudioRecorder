package com.pals.audioplayer.interfaces

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): String
}