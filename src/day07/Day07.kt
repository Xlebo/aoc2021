package day07

import getOrFetchInputDataIntArray
import readInput
import kotlin.math.absoluteValue

fun main() {

    fun part1(input: List<Int>, mapToFunction: (Int) -> (Int) = { it }): Int = input.map { x ->
        (0 until input.maxOrNull()!!).map { it to mapToFunction((it - x).absoluteValue) }
    }
        .flatMap { it.asSequence() }
        .groupBy({ it.first }, { it.second })
        .map { (_, v) -> v.sum() }
        .minOrNull()!!

    fun part2(input: List<Int>) = part1(input) { x -> (1..x).sum()}

    val testInput = readInput("Day07_test", "day07")[0].split(',').map { it.toInt() }

    val result1 = part1(testInput)
    val result2 = part2(testInput)
    check(result1 == 37) { "Got: $result1" }
    check(result2 == 168) { "Got: $result2" }

    val input = getOrFetchInputDataIntArray(7)
    println(part1(input))
    println(part2(input))
}