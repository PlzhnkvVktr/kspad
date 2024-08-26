package ru.avem.data.db

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import java.sql.Connection


object DBManager: KoinComponent {
    init {
        Database.connect("jdbc:sqlite:data.db", "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

        validateData()
    }

    private fun validateData() {
        transaction {
            SchemaUtils.create(TestItems, TestProtocols, Users)
        }
        if (getAllUsers().isEmpty()) {
            addUser("admin", "888", true)
        }
        if (getAllTI().isEmpty()) {
            addTI(
                name = "Test",
                scheme = true,
                power = "20",
                u_linear = "380",
                i = "20",
                i_viu = "0.2",
                i_mz = "60",
                u_viu = "1760",
                u_mgr = "1000",
                t_viu = "60",
                t_hh = "60",
                t_mv = "10",
                r_max = "200000",
                r_min = "500",
                r20_max = "0",
                r20_min = "0",
                t = "0.00425"
            )
            addTI(
                name = "Htcbnbbb",
                scheme = true,
                power = "21",
                u_linear = "400",
                i = "20",
                i_viu = "0.2",
                i_mz = "60",
                u_viu = "1760",
                u_mgr = "1000",
                t_viu = "60",
                t_hh = "60",
                t_mv = "10",
                r_max = "200000",
                r_min = "500",
                r20_max = "0",
                r20_min = "0",
                t = "0.00425"
            )
        }
    }

//    fun addTI(
//        testObject: TestObject
//    ) {
//        TestItem.new {
//            this.name = testObject.name
//            this.scheme = testObject.scheme
//            this.power = testObject.power
//            this.u_linear = testObject.u_linear
//            this.i = testObject.i
//            this.i_viu = testObject.i_viu
//            this.i_mz = testObject.i_mz
//            this.u_viu = testObject.u_viu
//            this.u_mgr = testObject.u_mgr
//            this.t_viu = testObject.t_viu
//            this.t_hh = testObject.t_hh
//            this.t_mv = testObject.t_mv
//            this.r_max = testObject.r_max
//            this.r_min = testObject.r_min
//            this.r20_max = testObject.r20_max
//            this.r20_min = testObject.r20_min
//            this.t = testObject.t
//        }
//    }



    fun getAllUsers(): List<User> {
        return transaction {
            User.all().toList()
        }
    }
    fun addUser(
        login: String,
        password: String,
        isAdmin: Boolean
    ) {

        transaction {
            User.new {
                this.login = login
                this.password = password
                this.isAdmin = isAdmin
            }
        }
    }

    fun getAllTI(): List<String> {
        return transaction {
            TestItem.all().map { it.name }
        }
    }
    fun getAllTI1(): List<TestItem> {
        return transaction {
            TestItem.all().toList()
        }
    }


    fun getAllProtocols(): List<TestProtocol> {
        return transaction {
            TestProtocol.all().reversed().toList()
        }
    }

    fun getTI(value: String) = transaction { TestItem.find { TestItems.name eq value }.first() }
    fun getProtocolById(protocol: TestProtocol) = transaction { TestProtocol.find { TestProtocols.id eq protocol.id }.first() }

    fun addTI(
        name: String,
        scheme: Boolean,
        power: String,
        u_linear: String,
        i: String,
        i_viu: String,
        i_mz: String,
        u_viu: String,
        u_mgr: String,
        t_viu: String,
        t_hh: String,
        t_mv: String,
        r_max: String,
        r_min: String,
        r20_max: String,
        r20_min: String,
        t: String
    ) {
        transaction {
            TestItem.new {
                this.name = name
                this.scheme = scheme
                this.power = power
                this.u_linear = u_linear
                this.i = i
                this.i_viu = i_viu
                this.i_mz = i_mz
                this.u_viu = u_viu
                this.u_mgr = u_mgr
                this.t_viu = t_viu
                this.t_hh = t_hh
                this.t_mv = t_mv
                this.r_max = r_max
                this.r_min = r_min
                this.r20_max = r20_max
                this.r20_min = r20_min
                this.t = t
            }
        }
    }

    fun deleteTestItemById(name: String) {
        transaction {
            TestItems.deleteWhere {
                TestItems.name eq name
            }
        }
    }

    fun deleteProtocolItemByName1(item: TestProtocol) {
        transaction {
            TestProtocols.deleteWhere {
                TestProtocols.id eq item.id
            }
        }
    }
    fun deleteUser(user: User) {
        transaction {
            Users.deleteWhere {
                Users.id eq user.id
            }
        }
    }

    fun updateUser(user: User, newLogin: String, newPassword: String, isAdminStatus: Boolean) {
        transaction {
            Users.update ({ Users.id eq user.id }) {
                it[login] = newLogin
                it[password] = newPassword
                it[isAdmin] = isAdminStatus
            }
        }
    }


    fun findUser(login: String): List<User> {
        return getAllUsers().filter { it.login.contains(login) }
    }
    fun findUserLogin(login: String): Boolean {
        return getAllUsers().find { it.login == login} != null
    }
    fun findProtocol(itemName: String): List<TestProtocol> {
        return getAllProtocols().filter { it.name.contains(itemName) }
    }

    fun findCurrentProtocol(itemName: String): TestProtocol? {
        return getAllProtocols().find { it.name == itemName }
    }

    fun addNewProtocol(type: String, testItem: TestItem, name: String) {



    }
}

