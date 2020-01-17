package com.txt.worldyachts_mobile.api

import com.google.gson.Gson // Докачать для парса gson, можно просто скачать либу и закинуть в папку с проетом.
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonWriter
import java.net.URL
import kotlin.math.log

//В бд много синтаксических косяков, править их сейчас болезненно, поэтому не обращаем внимания.

enum class Tables(val tableName : String) {
    accessoryId("AccessoryId"), // Аксесуары по id.
    auth("Auth"), // Логин, пароль, тип юзера, его id как кастомера.
    boat("Boat"), // Вся инфа по лодке.
    boatType("BoatType"), // Тип лодки в виде строки.
    colours("Colours"), // Цвета в виде строки.
    contract("Contract"), // Инфа о контракте (Для магаза по идее не нужна).
    customers("Customers"), // Юзреры + инфа о них.
    details("Details"), // Детали контракта (аксесуар - ордер).
    documentName("DocumentName"), // Документ в виде строки.
    fit("Fit"), // Связь какой аксесуар к какой лодке подходит.
    partners("Partners"), // Партнер который предоставляет аксесуар.
    productionProcess("ProductionProcess"), // Стадия процесса производства в виде строки.
    salesPerson("SalesPerson"), // Тот кто продал лодку.
    vat("Vat"), // НДС.
    wood("Wood") // Дерево в виде строки.
}

class Sender() {
    companion object {
        fun getTable(tables: Tables, id : Int? = null) : String {
            return try {
                val url = if (id == null) {
                    "https://yaht.azurewebsites.net/Home/Get?name=${tables.tableName}&id=all"
                } else {
                    "https://yaht.azurewebsites.net/Home/Get?name=${tables.tableName}&id=${id}"
                }
                URL(url).openStream().bufferedReader().use{ it.readText() }
            } catch (e : Exception) {
                "[]"
            }
        }

        fun getOrders(id : Int) : ArrayList<Orders> {
            val url = "https://yaht.azurewebsites.net/Orders/Get?customerId=${id}"
            val json = URL(url).openStream().bufferedReader().use{ it.readText() }
            return Gson().fromJson(json, object : TypeToken<List<Orders>>() {}.type)
        }

        fun checkAuth(login : String, password : String) : Int { // !!! Если возвращён -1 - авторизация не прошла или произошла ошибка.
            val auth = Auth()
            auth.username = login
            auth.password = password

            val url = "https://yaht.azurewebsites.net/Account/AppLogin?json=${Gson().toJson(auth)}"
            val json = URL(url).openStream().bufferedReader().use{ it.readText() }

            return  Gson().fromJson<Int>(json, Int::class.java)
        }

        fun registration(auth: Auth, customer: Customers) : ArrayList<String> {
            val map : MutableMap<String, Any> = mutableMapOf()
            map["auth"] = auth
            map["customer"] = customer

            val url = "https://yaht.azurewebsites.net/Account/AppRegistration?json=${Gson().toJson(map)}"
            val json = URL(url).openStream().bufferedReader().use{ it.readText() }

            return Gson().fromJson(json, object : TypeToken<List<String>>() {}.type)
        }
    }
}

class AccessoryId
{
    var accessoryId1 = 0
    var accName = ""
    var descriptionOfAccessory = ""
    var price = 0
    var vat = 0
    var inventory = 0
    var partnerId = 0
}

class Auth
{
    var username = ""
    var password = ""
    var userType = 0
    var customerId = 0
}

class Boat
{
    var boatId = 0
    var model = ""
    var boatType = 0
    var numberOfRowers = 0
    var colour = 0
    var wood = 0
    var basePrice = 0
    var vat = 0
}

class BoatType
{
    var boatTypeId = 0
    var boatType1 = ""
}

class Colours
{
    var colourId = 0
    var colour = ""
}

class Contract
{
    var contractId = 0
    var date = ""
    var dateDepositPayed = 0
    var orderId = 0
    var contractTotalPrice = 0
    var contractTotalPriceIncVat = 0
    var productionProcess = 0
}

class Customers
{
    var customerId = 0
    var firstName = ""
    var secondName = ""
    var dateOfBirth = ""
    var address = ""
    var city = ""
    var email = ""
    var phoneNumber = ""
    var idNumber = ""
    var idDocumentName = 0
}

class Details
{
    var detailId = 0
    var accessoryId = 0
    var orderId = 0
}

class DocumentName
{
    var documentNameId = 0
    var documentName1 = ""
}

class Fit
{
    var fitId = 0
    var accessoryId = 0
    var boatId = 0
}

class Orders
{
    var orderId = 0
    var date = ""
    var selesPersonId = 0
    var customerId = 0
    var boatId = 0
    var deliveryAddress = ""
    var city = ""
}

class Partners
{
    var partnerId = 0
    var name = ""
    var address = ""
    var city = ""
}

class ProductionProcess
{
    var productionProcessId = 0
    var productionProcess1 = ""
}

class SalesPerson
{
    var salesPersonId = 0
    var firstName = ""
    var secondName = ""
}

class Vat
{
    var vatId = 0
    var vat1 = 0.0
}

class Wood
{
    var woodId = 0
    var wood1 = ""
}

open class Main{
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            var json = Sender.getTable(Tables.accessoryId, 1) // Взятие по id.
            val accessoryId = Gson().fromJson<AccessoryId>(json, AccessoryId::class.java) // Конверт в объект.

            json = Sender.getTable(Tables.boat) // Взятие всего из таблицы.
            val List : ArrayList<Boat> = Gson().fromJson<ArrayList<Boat>>(json, object : TypeToken<List<Boat>>() {}.type) // Конверт в лист.



            //println(accessoryId)
            //println(accessoryIdList)

            //println(Sender.getOrders(Sender.checkAuth("admin", "admin")))

            val auth = Auth()
            auth.username = "admin"
            auth.password = "admin"
            val customer = Customers()
            println(Sender.registration(auth, customer)[0])
        }
    }
}