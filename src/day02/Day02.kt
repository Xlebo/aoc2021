package day02

import getOrFetchInputData
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val data = input.map { x -> x.split(' ') }
            .map { x -> x[0] to x[1].toInt() }
            .groupBy({ it.first }, { it.second })
            .map { (k, v) -> k to v.sum() }.toMap()

        val down = data["down"] ?: 0
        val up = data["up"] ?: 0
        val forward = data["forward"] ?: 0
        return (down - up) * forward
    }

    fun part2(input: List<String>): Int {
        var aim = 0
        var vertical = 0
        var horizontal = 0

        input.map { x -> x.split(' ') }
            .map { x -> x[0] to x[1].toInt() }
            .forEach { x ->
                when (x.first) {
                    "up" -> aim -= x.second
                    "down" -> aim += x.second
                    "forward" -> {
                        vertical += x.second
                        horizontal += x.second * aim
                    }
                }
            }
        return horizontal * vertical
    }

    val testInput = readInput("Day02_test", "day02")
    val result1 = part1(testInput)
    val result2 = part2(testInput)
    check(result1 == 150) { "Got: $result1" }
    check(result2 == 900) { "Got: $result2" }

    val input = getOrFetchInputData(2)
    println(part1(input))
    println(part2(input))
}