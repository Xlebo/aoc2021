package day06

import getOrFetchInputData
import readInput

fun main() {

    fun part1(input: List<String>, range: IntRange = 1..80): Long {
        val fishes = ((0..8).associateWith { 0L } + input[0].split(',')
            .map { it.toInt() }
            .map { it to 1 }
            .groupBy({ it.first }, { it.second })
            .map { (k, v) -> k to v.sum().toLong() }).toMutableMap()
        for (i in range) {
            val temp = fishes[6]!!
            fishes[6] = fishes[0]!!.plus(fishes[7]!!)
            fishes[7] = fishes[8]!!
            fishes[8] = fishes[0]!!
            fishes[0] = fishes[1]!!
            fishes[1] = fishes[2]!!
            fishes[2] = fishes[3]!!
            fishes[3] = fishes[4]!!
            fishes[4] = fishes[5]!!
            fishes[5] = temp
        }
        return fishes.values.sum()
    }

    fun part2(input: List<String>): Long = part1(input, 1..256)

    val testInput = readInput("Day06_test", "day06")

    val result1 = part1(testInput)
    val result2 = part2(testInput)
    check(result1 == 5934L) { "Got: $result1" }
    check(result2 == 26984457539) { "Got: $result2" }

    val input = getOrFetchInputData(6)
    println(part1(input))
    println(part2(input))
}