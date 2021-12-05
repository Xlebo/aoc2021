package day05

import getOrFetchInputData
import readInput

data class Vector(val start: Point, val end: Point) {

    companion object {
        fun create(p1: Point, p2: Point): Vector = when {
            p2.x > p1.x -> Vector(p1, p2)
            p2.x < p1.x -> Vector(p2, p1)
            else -> when {
                p2.y > p1.y -> Vector(p1, p2)
                else -> Vector(p2, p1)
            }
        }
    }
}

data class Point(val x: Int, val y: Int)

fun main() {

    fun part1(
        input: List<String>,
        filter: (Vector) -> Boolean = { v -> v.start.x == v.end.x || v.start.y == v.end.y }
    ): Int = input
        .map {
            "(\\d+,\\d+) -> (\\d+,\\d+)".toRegex().find(it)!!.destructured.toList()
                .map { point -> point.split(',').map { coordinate -> coordinate.toInt() } }
                .map { point -> Point(point[0], point[1]) }
        }
        .map { Vector.create(it[0], it[1]) }
        .filter { filter(it) }
        .map { vector ->
            if (vector.start.x == vector.end.x) { // then guaranteed start.y < end.y
                (vector.start.y..vector.end.y).map { y -> Point(vector.start.x, y) }
            } else {
                (vector.start.x..vector.end.x).map { x ->
                    when {
                        vector.start.y < vector.end.y -> Point(x, vector.start.y - vector.start.x + x)
                        vector.start.y > vector.end.y -> Point(x, vector.start.y - x + vector.start.x)
                        else -> Point(x, vector.start.y)
                    }
                }
            }
        }
        .flatMap { it.asSequence() }
        .groupingBy { it }
        .eachCount()
        .filter { it.value > 1 }
        .size

    fun part2(input: List<String>): Int = part1(input) { true }

    val testInput = readInput("Day05_test", "day05")

    val result1 = part1(testInput)
    val result2 = part2(testInput)
    check(result1 == 5) { "Got: $result1" }
    check(result2 == 12) { "Got: $result2" }

    val input = getOrFetchInputData(5)
    println(part1(input))
    println(part2(input))
}