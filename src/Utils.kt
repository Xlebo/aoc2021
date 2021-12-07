import java.io.File
import java.math.BigInteger
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String, parent: String = name) = File("src\\$parent", "$name.txt").readLines()

fun readInputNumbers(name: String, parent: String = name): List<Int> = readInput(name, parent).map { x -> x.toInt() }

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun fetchInputData(day: Int = 1): String {
    val cookie = System.getProperty("cookie")
    val client = HttpClient.newBuilder().build()
    val request = HttpRequest.newBuilder()
        .setHeader(
            "cookie",
            "session=$cookie"
        )
        .uri(URI.create("https://adventofcode.com/2021/day/$day/input"))
        .build()
    val response = client.send(request, HttpResponse.BodyHandlers.ofString())
    return response.body()
}

fun getOrFetchInputData(day: Int = 1): List<String> {
    val parent = "day${day.toString().padStart(2, '0')}"
    val f = File("src\\$parent", "$parent.txt")

    if (!f.exists()) {
        f.createNewFile()
        f.writeBytes(fetchInputData(day).toByteArray())
    }

    return f.readLines()
}

fun getOrFetchInputDataAsNumbers(day: Int = 1): List<Int> = getOrFetchInputData(day)
    .filter { x -> x.isNotEmpty() }
    .map { x -> x.toInt() }

fun getOrFetchInputDataIntArray(day: Int = 1): List<Int> = getOrFetchInputData(day)[0].split(',').map { it.toInt() }