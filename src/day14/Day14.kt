package day14

import getOrFetchInputDataAsString
import readInputAsString

fun main() {

    fun part1(input: String, iterations: Int = 10): Long {
        var polymer: Map<Pair<Char, Char>, Long>
        val mapping: Map<Pair<Char, Char>, Char>
        input.split(System.lineSeparator() + System.lineSeparator()).let {
            polymer = it[0].zipWithNext().groupingBy { pair -> pair }.fold(0) { acc, _ -> acc + 1 }
            mapping =
                it[1].split(System.lineSeparator())
                    .associate { line -> line.split(" -> ").let { s -> (s[0][0] to s[0][1]) to s[1][0] } }
        }
        for (i in 1..iterations) {
            val newPolymer = mutableMapOf<Pair<Char, Char>, Long>()
            polymer.forEach { (pair, value) ->
                val middle = mapping[pair]!!
                val pair1 = pair.first to middle
                val pair2 = middle to pair.second
                if (!newPolymer.containsKey(pair1)) {
                    newPolymer[pair1] = 0
                }
                if (!newPolymer.containsKey(pair2)) {
                    newPolymer[pair2] = 0
                }
                newPolymer[pair1] = newPolymer[pair1]!! + value
                newPolymer[pair2] = newPolymer[pair2]!! + value
            }
            polymer = newPolymer
        }

        val firstOccurrences = polymer.map { it.key.first to it.value }.groupBy({ it.first }, { it.second })
            .map { it.key to it.value.sum() }

        val secondOccurrences = polymer.map { it.key.second to it.value }.groupBy({ it.first }, { it.second })
            .map { it.key to it.value.sum() }

        val occurrences = (firstOccurrences + secondOccurrences).groupBy({ it.first }, { it.second })
            .map { it.key to it.value.maxByOrNull { x -> x }!! }

        return occurrences.maxOf { it.second } - occurrences.minOf { it.second }
    }

    fun part2(input: String): Long = part1(input, 40)

    val testInput = readInputAsString("Day14_test", "day14")

    val result1 = part1(testInput)
    val result2 = part2(testInput)
    check(result1 == 1588L) { "Got: $result1" }
    check(result2 == 2188189693529) { "Got: $result2" }

    val input = getOrFetchInputDataAsString(14)
    println(part1(input))
    println(part2(input))
}