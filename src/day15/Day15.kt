package day15

import getOrFetchInputData
import readInput

typealias Position = Pair<Int, Int>

fun main() {

    val neighbourList = listOf(1 to 0, 0 to 1, 0 to -1, -1 to 0)

    fun part1(input: List<String>): Int {
        val maxX = input[0].length - 1
        val maxY = input.size - 1

        val discovered = mutableMapOf<Position, Int>()
        val reachable = mutableMapOf(0 to 0 to 0) // start is not counted in
        val end = maxX to maxY

        while (!discovered.containsKey(end)) {
            val current = reachable.minByOrNull { it.value }!!
            discovered[current.key] = current.value
            neighbourList.map { it.first + current.key.first to it.second + current.key.second }
                .filter { it.first in 0..maxX && it.second in 0..maxY }
                .filter { it !in discovered }
                .forEach {
                    if (!reachable.containsKey(it)) {
                        reachable[it] = Int.MAX_VALUE
                    }
                    val currentValue = input[it.first][it.second].digitToInt() + current.value
                    if (currentValue < reachable[it]!!) {
                        reachable[it] = currentValue
                    }
                }
            reachable.remove(current.key)
        }
        return discovered[end]!!
    }

    fun part2(input: List<String>): Int {
        val smallMaxX = input[0].length
        val smallMaxY = input.size
        val maxX = input[0].length * 5 - 1
        val maxY = input.size * 5 - 1

        val discovered = mutableMapOf<Position, Int>()
        val reachable = mutableMapOf(0 to 0 to 0) // start is not counted in
        val end = maxX to maxY

        fun getValue(x: Int, y: Int) =
            (input[x.mod(smallMaxX)][y.mod(smallMaxY)].digitToInt() - 1 + x.floorDiv(smallMaxX) + y.floorDiv(smallMaxY)).mod(
                9
            ) + 1

        while (!discovered.containsKey(end)) {
            val current = reachable.minByOrNull { it.value }!!
            discovered[current.key] = current.value
            neighbourList.map { it.first + current.key.first to it.second + current.key.second }
                .filter { it.first in 0..maxX && it.second in 0..maxY }
                .filter { it !in discovered }
                .forEach {
                    if (!reachable.containsKey(it)) {
                        reachable[it] = Int.MAX_VALUE
                    }
                    val currentValue = getValue(it.first, it.second) + current.value
                    if (currentValue < reachable[it]!!) {
                        reachable[it] = currentValue
                    }
                }
            reachable.remove(current.key)
        }
        return discovered[end]!!
    }

    val testInput = readInput("Day15_test", "day15")

    val result1 = part1(testInput)
    val result2 = part2(testInput)
    check(result1 == 40) { "Got: $result1" }
    check(result2 == 315) { "Got: $result2" }

    val input = getOrFetchInputData(15)
    println(part1(input))
    println(part2(input))
}