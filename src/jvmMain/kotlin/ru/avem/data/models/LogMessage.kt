package ru.avem.data.models

import ru.avem.data.enums.LogType

data class LogMessage (
    val text: String,
    val type: LogType
)
