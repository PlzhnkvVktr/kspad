package ru.avem.modules.tests

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

interface ProtectionManager {
    var isStartPressed: Boolean
    var isStopPressed: Boolean
    var doorZone: Boolean
    var doorSCO: Boolean
    var ikzTI: Boolean
    var ikzVIU: Boolean
}