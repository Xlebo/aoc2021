package day13

import getOrFetchInputDataAsString
import readInputAsString
import kotlin.math.abs

typealias Position = Pair<Int, Int>

fun Position.invertVertically(y: Int): Position = this.first to y - abs(this.second - y)

fun Position.invertHorizontally(x: Int): Position = x - abs(this.first - x) to this.second

typealias Manual = Set<Position>

fun Manual.foldInstruction(s: String): Manual =
    s.split('=').let { if (it[0] == "x") splitHorizontally(it[1].toInt()) else splitVertically(it[1].toInt()) }

fun Manual.splitVertically(y: Int): Manual =
    this.filter { it.second > y }.map { it.invertVertically(y) }.plus(this.filter { it.second < y }).toSet()

fun Manual.splitHorizontally(x: Int): Manual =
    this.filter { it.first > x }.map { it.invertHorizontally(x) }.plus(this.filter { it.first < x }).toSet()

fun Manual.print() = (0..this.maxByOrNull { it.second }!!.second).forEach { y ->
    (0..this.maxByOrNull { it.first }!!.first).forEach { x ->
        print(if (this.contains(x to y)) '#' else '.')
    }
    print('\n')
}


fun main() {

    fun part1(input: String): Int =
        input.split(System.lineSeparator() + System.lineSeparator()).let { instructions ->
            instructions[0].split(System.lineSeparator())
                .fold(setOf<Position>()) { set, item ->
                    set.plus(
                        item.split(',').let { it[0].toInt() to it[1].toInt() })
                }.foldInstruction("(.=\\d*)".toRegex().find(instructions[1])!!.groups[1]!!.value)
        }.size

    fun part2(input: String) =
        input.split(System.lineSeparator() + System.lineSeparator()).let { instructions ->
            instructions[0].split(System.lineSeparator())
                .fold(setOf<Position>()) { set, item ->
                    set.plus(
                        item.split(',').let { it[0].toInt() to it[1].toInt() })
                }.let {
                    instructions[1].split(System.lineSeparator()).fold(it) { instructions, instruction ->
                        instructions.foldInstruction("(.=\\d*)".toRegex().find(instruction)!!.groups[1]!!.value)
                    }
                }.print()
        }

    val testInput = readInputAsString("Day13_test", "day13")

    val result1 = part1(testInput)
//    val result2 = part2(testInput)
    check(result1 == 17) { "Got: $result1" }
//    check(result2 == 195) { "Got: $result2" }

    val input = getOrFetchInputDataAsString(13)
    println(part1(input))
    part2(input)
}