package ru.avem.data.repos

import kotlinx.serialization.Serializable

@Serializable
data class Settings(
    var language: String = "",
    var login: String = "",
    var font: String = "",
    var isAdmin: Boolean = true
)