package day03

import getOrFetchInputData
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val gamma = input
            .map { x -> x.toCharArray() }
            .map { x -> (x.indices).map { it to x[it].digitToInt() } }
            .flatMap { it.asSequence() }
            .groupBy( {it.first } , {it.second} )
            .map { (_, v) -> if (v.sum() > input.size / 2) '1' else '0' }
            .joinToString("")
            .toInt(2)

        val epsilon = (1..input.first().length)
            .map { _ -> '1' }
            .joinToString("")
            .toInt(2)
            .xor(gamma)

        return gamma * epsilon
    }

    fun bitCriteria(input: List<String>, operation: (Int, Int) -> (Boolean), index: Int = 0): Int {
        if (input.size == 1) return input.first().toInt(2)
        val sum = input.sumOf { x -> x[index].digitToInt() }
        val rating = if (operation(input.size - sum, sum)) '0' else '1'
        return bitCriteria(input.filter { x -> x[index] == rating }, operation, index.inc())
    }


    fun part2(input: List<String>): Int {
        val generator = bitCriteria(input, { a, b -> a > b })
        val scrubber = bitCriteria(input, { a, b -> a <= b})
        return generator * scrubber
    }

    val testInput = readInput("Day03_test", "day03")
    val result1 = part1(testInput)
    val result2 = part2(testInput)
    check(result1 == 198) { "Got: $result1" }
    check(result2 == 230) { "Got: $result2" }

    val input = getOrFetchInputData(3)
    println(part1(input))
    println(part2(input))
}