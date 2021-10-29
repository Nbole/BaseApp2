package com.example.baseapp

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

// TODO: permitir que el método acepte múltiples atributos
// TODO: Crear una data class para indicar el estado de la request, similar a REQUEST de retrofit
fun <T> get(baseUrl: String, params: String?, key: String?): T? {
    val response: T?
    // TODO: resolver esto en un método
    val url = URL("$baseUrl?$params=$key")
    val httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
    httpURLConnection.requestMethod = "GET"
    val inputStream: InputStream = httpURLConnection.inputStream
    val outPutStream = ByteArrayOutputStream()
    // TODO: resolver esto en un método aparte
    var temp = ByteArray(inputStream.available())
    while (inputStream.read(temp, 0, temp.size) != -1) {
        outPutStream.write(temp)
        temp = ByteArray(httpURLConnection.inputStream.available())
    }
    // TODO: Probar en una respuesta que sea una lista
    response =
        GsonBuilder().create().fromJson(JSONObject(outPutStream.toString()).toString(),
            object : TypeToken<T>() {}.type)
    inputStream.close()
    return response
}

data class MealResponse(
    @SerializedName("idCategory") val idCategory: String,
    @SerializedName("strCategory") val strCategory: String,
    @SerializedName("srtCategoryDescription") val srtCategoryDescription: String,
    @SerializedName("strCategoryThumb") val strCategoryThumb: String,
)

data class MealRequest(
    val categories: List<MealResponse>,
)