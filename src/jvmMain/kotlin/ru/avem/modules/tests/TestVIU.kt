package ru.avem.modules.tests

class TestVIU : Test() {
    override suspend fun start() {
        initDevices()
    }
}