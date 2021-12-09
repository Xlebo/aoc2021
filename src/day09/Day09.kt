package day09

import getOrFetchInputData
import readInput

typealias Point = Pair<Int, Int>

class HeightMap(input: List<String>) {
    private val rows: Int
    private val columns: Int
    val heightMap: Map<Point, Int>
    val lowPoints: List<Point>

    init {
        rows = input.size
        columns = input[0].length
        heightMap =
            input.indices.map { row ->
                input[0].indices.map { column ->
                    Point(
                        row,
                        column
                    ) to input[row][column].digitToInt()
                }
            }
                .flatten().toMap()
        lowPoints = heightMap.keys.filter { isLowPoint(it) }
    }

    fun getFlowToPoint(point: Point): Set<Point> {
        if (heightMap[point] == 9) return setOf()
        val neighbours = setOf(
            point.first - 1 to point.second,
            point.first + 1 to point.second,
            point.first to point.second - 1,
            point.first to point.second + 1
        )
            .filter { isValid(it) }
            .filter { heightMap[it] != 9 }
            .filter { heightMap[it]!! > heightMap[point]!! }

        return setOf(point).plus(neighbours.map { getFlowToPoint(it) }.flatten())
    }

    private fun isValid(point: Point): Boolean = point.first in 0 until rows && point.second in 0 until columns

    private fun isLowPoint(point: Point): Boolean {
        val value = heightMap[point]!!
        return when {
            point.first + 1 < rows && heightMap[point.first + 1 to point.second]!! < value -> false
            point.first - 1 >= 0 && heightMap[point.first - 1 to point.second]!! < value -> false
            point.second + 1 < columns && heightMap[point.first to point.second + 1]!! < value -> false
            point.second - 1 >= 0 && heightMap[point.first to point.second - 1]!! < value -> false
            else -> true
        }
    }
}

fun main() {

    fun part1(input: List<String>): Int {
        val heightMap = HeightMap(input)
        return heightMap.lowPoints.size + heightMap.lowPoints.sumOf { heightMap.heightMap[it]!! }
    }

    fun part2(input: List<String>): Int {
        val heightMap = HeightMap(input)
        return heightMap.lowPoints.map { heightMap.getFlowToPoint(it).size }.sortedDescending().also { println(it) }
            .subList(0, 3).reduce { acc, i -> acc * i }
    }

    val testInput = readInput("Day09_test", "day09")

    val result1 = part1(testInput)
    val result2 = part2(testInput)
    check(result1 == 15) { "Got: $result1" }
    check(result2 == 1134) { "Got: $result2" }

    val input = getOrFetchInputData(9)
    println(part1(input))
    println(part2(input))
}