package ru.avem.data.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class ParamOI(
    val title: String,
    val param: MutableState<String>,
    val min: Number,
    val max: Number,
    val isError: MutableState<Boolean> = mutableStateOf(false),
    val isString: Boolean = false,
)
