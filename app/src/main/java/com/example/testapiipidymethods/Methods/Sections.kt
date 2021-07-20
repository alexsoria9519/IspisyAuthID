package com.example.testapiipidymethods.Methods

import android.content.Context
import com.google.gson.Gson
import java.io.IOException
import java.util.*

class Sections {
    var sections = ArrayList<Section>()


    fun  loadData(data: String): ArrayList<Section>{
        val gson = Gson()
        val methodsData: Sections = gson.fromJson(data, Sections::class.java)
        return methodsData.sections;
    }

    fun readJson(context: Context, fileName: String): String?{
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }




}