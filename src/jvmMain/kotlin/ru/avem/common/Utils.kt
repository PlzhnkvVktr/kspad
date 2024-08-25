package ru.avem.common

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.nio.file.Path
import java.util.*
import kotlin.io.path.writeText
import kotlin.math.abs

fun String.af(): String = this.toDoubleOrNull()?.af() ?: this
fun Number.af(): String = this.toDouble().af()
fun Double.af(): String = with(abs(this)) {
    when {
        this < 0.001 -> "%.0f"
        this < 0.1 -> "%.3f"
        this < 10 -> "%.2f"
        this < 100 -> "%.1f"
        else -> "%.0f"
    }.format(Locale.ENGLISH, this@af)
}

inline fun <reified T> loadFromJson(path: Path): T {
    val resultFile = path.toFile()
    if (!resultFile.exists()) {
        resultFile.createNewFile()
    }
    return Json.decodeFromString(resultFile.readText())
}

inline fun <reified T> saveToJsonFile(path: Path, model: T) {
    path.writeText(Json.encodeToString(model))
}