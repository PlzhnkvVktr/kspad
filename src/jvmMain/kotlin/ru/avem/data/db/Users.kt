package ru.avem.data.db

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Users : IntIdTable() {
    var login = varchar("login", 50)
    val password = varchar("password", 25)
    val isAdmin = bool("isAdmin")
}

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(Users)

    var login by Users.login

    var password by Users.password
    var isAdmin by Users.isAdmin



    override fun toString() = login

}