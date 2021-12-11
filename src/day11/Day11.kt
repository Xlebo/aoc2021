package day11

import getOrFetchInputData
import readInput

fun main() {

    val neighbourList = listOf(1 to 1, 1 to 0, 1 to -1, 0 to 1, 0 to -1, -1 to 1, -1 to 0, -1 to -1)

    class Octopus(
        x: Int,
        y: Int,
        var value: Int,
        val map: Array<Array<Octopus>>,
        maxX: Int,
        maxY: Int,
        var flashed: Boolean = false
    ) {
        val neighbours: List<Pair<Int, Int>>

        init {
            neighbours = neighbourList.map { it.first + x to it.second + y }
                .filter { it.first in 0..maxX && it.second in 0..maxY }
        }

        fun flash(): Int {
            if (flashed) return 0
            value++
            if (value > 9) {
                flashed = true
                value = 0
                return 1 + neighbours.sumOf { map[it.first][it.second].flash() }
            }
            return 0
        }

    }


    fun initMap(input: List<String>): Array<Array<Octopus>> {
        val dummyOctopus = Octopus(0, 0, 0, arrayOf(), 0, 0)
        val maxX = input.size - 1
        val maxY = input[0].length - 1
        // initialize array with dummyOctopus values, so the reference to map in each octopus won't change due to reallocation
        val m: Array<Array<Octopus>> = Array(maxX + 1) { Array(maxY + 1) { dummyOctopus } }
        (0..maxX).forEach() { row ->
            (0..maxY).forEach { column ->
                m[row][column] = Octopus(
                    row,
                    column,
                    input[row][column].digitToInt(),
                    m,
                    maxX,
                    maxY
                )
            }
        }
        return m
    }

    fun part1(input: List<String>): Int {
        val m = initMap(input)
        var flashes = 0
        for (i in 1..100) {
            m.forEach { it.forEach { octopus -> octopus.flashed = false } }
            flashes += m.sumOf { it.sumOf { x -> x.flash() } }
        }
        return flashes
    }

    fun part2(input: List<String>): Int {
        val m = initMap(input)
        var step = 1
        while (true) {
            m.forEach { it.forEach { octopus -> octopus.flashed = false } }
            m.forEach { it.forEach { x -> x.flash() } }
            if (m.all { it.all { octopus -> octopus.flashed } })
                return step
            step++
        }
    }

    val testInput = readInput("Day11_test", "day11")

    val result1 = part1(testInput)
    val result2 = part2(testInput)
    check(result1 == 1656) { "Got: $result1" }
    check(result2 == 195) { "Got: $result2" }

    val input = getOrFetchInputData(11)
    println(part1(input))
    println(part2(input))
}