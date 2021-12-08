package day08

import getOrFetchInputData
import readInput

class Mapping(val digits: Map<Set<Char>, Int>) {

    companion object {
        fun create(input: List<String>): Mapping {
            val values = input.map { it.length to it.toSet() }.groupBy({ it.first }, { it.second })
            val digits = mutableMapOf<Set<Char>, Int>()
            digits[values[2]!![0]] = 1
            digits[values[4]!![0]] = 4
            digits[values[3]!![0]] = 7
            digits[values[7]!![0]] = 8
            values[5]!!.forEach {
                digits[it] = when {
                    it.containsAll(values[2]!![0]) -> 3
                    it.intersect(values[4]!![0]).size == 3 -> 5
                    else -> 2
                }
            }
            values[6]!!.forEach {
                digits[it] = when {
                    it.containsAll(values[4]!![0]) -> 9
                    it.containsAll(values[2]!![0]) -> 0
                    else -> 6
                }
            }
            return Mapping(digits)
        }
    }
}

fun main() {

    fun part1(input: List<String>) = input.map { it.split(" | ")[1] }
        .map { it.split(' ') }
        .flatten()
        .count { setOf(2, 3, 4, 7).contains(it.length) }

    fun part2(input: List<String>) = input.map { it.split(" | ") }
        .map { Mapping.create(it[0].split(' ')) to it[1].split(' ') }
        .map {
            it.first to it.second.joinToString(separator = "") { x -> it.first.digits[x.toSet()]!!.toString() }
                .toInt()
        }
        .sumOf { (_, v) -> v }

    val testInput = readInput("Day08_test", "day08")

    val result1 = part1(testInput)
    val result2 = part2(testInput)
    check(result1 == 26) { "Got: $result1" }
    check(result2 == 61229) { "Got: $result2" }

    val input = getOrFetchInputData(8)
    println(part1(input))
    println(part2(input))
}