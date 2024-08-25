package ru.avem.modules.tests

class TestMV : Test() {
    override suspend fun start() {
        initDevices()
    }
}