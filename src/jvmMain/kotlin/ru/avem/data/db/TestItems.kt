package ru.avem.data.db

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object TestItems : IntIdTable() {
    var name = varchar("name", 50)
    val scheme = bool("scheme")
    val power = varchar("power", 50)
    val u_linear = varchar("u_linear", 50)
    val i = varchar("i", 50)
    val i_viu = varchar("i_viu", 50)
    val i_mz = varchar("i_mz", 50)
    val u_viu = varchar("u_viu", 50)
    val u_mgr = varchar("u_mgr", 50)
    val t_viu = varchar("t_viu", 50)
    val t_hh = varchar("t_hh", 50)
    val t_mv = varchar("t_mv", 50)
    val r_max = varchar("r_max", 50)
    val r_min = varchar("r_min", 50)
    val r20_max = varchar("r20_max", 50)
    val r20_min = varchar("r20_min", 50)
    val t = varchar("t", 50)
}

class TestItem(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TestItem>(TestItems)

    var name by TestItems.name
    var scheme by TestItems.scheme
    var power by TestItems.power
    var u_linear by TestItems.u_linear
    var i by TestItems.i
    var i_viu by TestItems.i_viu
    var i_mz by TestItems.i_mz
    var u_viu by TestItems.u_viu
    var u_mgr by TestItems.u_mgr
    var t_viu by TestItems.t_viu
    var t_hh by TestItems.t_hh
    var t_mv by TestItems.t_mv
    var r_max by TestItems.r_max
    var r_min by TestItems.r_min
    var r20_max by TestItems.r20_max
    var r20_min by TestItems.r20_min
    var t by TestItems.t

    override fun toString() = name

}