package com.ecamarero.spacex.network.client

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.internal.bind.util.ISO8601Utils
import org.junit.Assert.*
import org.junit.Test
import java.sql.Timestamp
import java.text.DateFormat
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

class HttpClientModuleTest {
    @Test
    fun timestamp() {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create()
        val fromJson = gson.fromJson<DateWrapper>(JSON, DateWrapper::class.java)
        assert(fromJson.date == ISO8601Utils.parse("2021-01-01T00:00:00.000Z", ParsePosition(0)))
    }

    val JSON = """{"date":"2021-01-01T00:00:00.000Z"}"""
}

data class DateWrapper (val date: Date)