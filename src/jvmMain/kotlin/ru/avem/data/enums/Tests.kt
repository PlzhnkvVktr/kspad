package ru.avem.data.enums

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import ru.avem.modules.tests.*
import ru.avem.ui.screens.tests.*
import ru.avem.ui.viewmodels.TestScreenViewModel


enum class Tests(
    var testName: String,
    var check: MutableState<Boolean>,
    var controller: Test,
    var view: @Composable() (() -> Unit)
) {
    MGR(
        testName = "Измерение сопротивления изоляции обмоток относительно корпуса и между фазами обмоток.",
        check = mutableStateOf(false),
        controller = TestMGR(),
        view = { MGRScreen() }
    ),
    VIU(
        testName = "Испытание изоляции обмоток относительно корпуса и между фазами на электрическую прочность.",
        check = mutableStateOf(false),
        controller = TestVIU(),
        view = { VIUScreen() }
        ),
    IKAS(
        testName = "Измерение сопротивления обмоток постоянному току в практически холодном состоянии",
        check = mutableStateOf(false),
        controller = TestIKAS(),
        view = { IKASScreen() }
    ),
    HH(testName = "Контроль равенства токов по фазам.",
        check = mutableStateOf(false),
        controller = TestHH(),
        view = { HHScreen() }
    ),
    MV(testName = "Оценка состояния сердечников, путём сравнения фактических удельных потерь и напряжённости магнитного поля.",
        check = mutableStateOf(false),
        controller = TestMV(),
        view = { MVScreen() }
    );
}