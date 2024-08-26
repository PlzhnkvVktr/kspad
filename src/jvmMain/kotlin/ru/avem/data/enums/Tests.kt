package ru.avem.data.enums

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import ru.avem.modules.tests.*
import ru.avem.ui.screens.tests.*
import ru.avem.ui.viewmodels.TestScreenViewModel


enum class Tests(
    var testName: String,
    var warning: String,
    var check: MutableState<Boolean>,
    var controller: Test,
    var view: @Composable() (() -> Unit)
) {
    MGR(
        testName = "Измерение сопротивления изоляции обмоток относительно корпуса и между фазами обмоток.",
        warning = "   Подключить ТОЛЬКО Высоковольтный провод (ВИУ) к Испытуемой обмотке/выводу ОИ и Провод измерительный (МЕ) к корпусу и/или частям, относительно которых будет проходить проверка.\n" +
                "   Все остальные провода не должны быть подключены к ОИ.",
        check = mutableStateOf(false),
        controller = TestMGR(),
        view = { MGRScreen() }
    ),
    VIU(
        testName = "Испытание изоляции обмоток относительно корпуса и между фазами на электрическую прочность.",
        warning = "   Подключить ТОЛЬКО Высоковольтный провод (ВИУ) к Испытуемой обмотке/выводу ОИ и Провод измерительный (МЕ) к корпусу и/или частям, относительно которых будет проходить проверка.\n" +
                "   Все остальные провода не должны быть подключены к ОИ.",
        check = mutableStateOf(false),
        controller = TestVIU(),
        view = { VIUScreen() }
        ),
    IKAS(
        testName = "Измерение сопротивления обмоток постоянному току в практически холодном состоянии",
        warning = "   Подключить ТОЛЬКО Высоковольтный провод (ВИУ) к Испытуемой обмотке/выводу ОИ и Провод измерительный (МЕ) к корпусу и/или частям, относительно которых будет проходить проверка.\n" +
                "   Все остальные провода не должны быть подключены к ОИ.",
        check = mutableStateOf(false),
        controller = TestIKAS(),
        view = { IKASScreen() }
    ),
    HH(testName = "Контроль равенства токов по фазам.",
        warning = "   Подключить ТОЛЬКО Высоковольтный провод (ВИУ) к Испытуемой обмотке/выводу ОИ и Провод измерительный (МЕ) к корпусу и/или частям, относительно которых будет проходить проверка.\n" +
                "   Все остальные провода не должны быть подключены к ОИ.",
        check = mutableStateOf(false),
        controller = TestHH(),
        view = { HHScreen() }
    ),
    MV(testName = "Оценка состояния сердечников, путём сравнения фактических удельных потерь и напряжённости магнитного поля.",
        warning = "   Подключить ТОЛЬКО Высоковольтный провод (ВИУ) к Испытуемой обмотке/выводу ОИ и Провод измерительный (МЕ) к корпусу и/или частям, относительно которых будет проходить проверка.\n" +
                "   Все остальные провода не должны быть подключены к ОИ.",
        check = mutableStateOf(false),
        controller = TestMV(),
        view = { MVScreen() }
    );
}