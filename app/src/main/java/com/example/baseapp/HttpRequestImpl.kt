package com.example.baseapp

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import org.xml.sax.Parser
import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import kotlin.reflect.KClass

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

// TODO: probar que funcione en múltiples querys
// TODO: probar que funcione cuando devuelve sólo una lista
// TODO: armar método para devolver un link para ver en explorador
fun <T>query(baseUrl: String, queryName: String, queryBody:String ,variables: GraphQlInput): T? {
    val response: T?
    val query = "query $queryName" +
            "(\$input: ${variables.getClass().simpleName}!){$queryName(input:\$input) $queryBody} "

    val httpURLConnection: HttpURLConnection = URL(baseUrl).openConnection() as HttpURLConnection

    httpURLConnection.requestMethod = "POST"
    httpURLConnection.setRequestProperty("content-type", "application/json")
    httpURLConnection.doOutput = true
    httpURLConnection.doInput = true

    val outputStreamWriter =
        OutputStreamWriter(BufferedOutputStream(httpURLConnection.outputStream))
    outputStreamWriter.write(
        "{\"query\":\"$query\",\"variables\":{\"input\":${Gson().toJson(variables)}}}"
    )
    outputStreamWriter.flush()
    outputStreamWriter.close()

    httpURLConnection.connect()
    val inputStream: InputStream = httpURLConnection.inputStream
    val outPutStream = ByteArrayOutputStream()
    var temp = ByteArray(inputStream.available())
    while (inputStream.read(temp, 0, temp.size) != -1) {
        outPutStream.write(temp)
        temp = ByteArray(httpURLConnection.inputStream.available())
    }
    response = GsonBuilder().create().fromJson(JSONObject(outPutStream.toString()).toString(),
            object : TypeToken<T>() {}.type)
    inputStream.close()
    return response
}


interface GraphQlInput

data class CoordinatesInput(
    val lat: Double,
    val lng: Double,
) : GraphQlInput

data class MealResponse(
    @SerializedName("idCategory") val idCategory: String,
    @SerializedName("strCategory") val strCategory: String,
    @SerializedName("srtCategoryDescription") val srtCategoryDescription: String,
    @SerializedName("strCategoryThumb") val strCategoryThumb: String,
)

data class PreviewHomeSupplierRequest(val previewHomeSuppliers: PreviewHomeSupplierResponse)
data class PreviewHomeSupplierResponse(val suppliers: List<PreviewSupplier>)
data class PreviewSupplier(val id: Int, val name: String, val legalName: String, val avatar: String)
data class MealRequest(
    val categories: List<MealResponse>,
)

fun<T: Any> T.getClass(): KClass<out T> = this::class