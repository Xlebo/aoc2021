package day01

import fetchInputDataAsNumbers
import readInputNumbers

fun main() {
    fun part1(input: List<Int>): Int {
        if (input.isEmpty())
            return 0
        var greaterThanPrevious = 0
        var previous = input.first()
        for (depth in input) {
            if (depth > previous) {
                greaterThanPrevious++
            }
            previous = depth
        }
        return greaterThanPrevious
    }

    fun part2(input: List<Int>): Int {
        if (input.size < 4)
            return 0

        var first = input[0]
        var second = input[1]
        var third = input[2]
        var currentSum: Int

        var previousSum = first + second + third
        var greaterThanPrevious = 0

        for (i in 3 until input.size) {
            first = second
            second = third
            third = input[i]

            currentSum = first + second + third

            if (currentSum > previousSum)
                greaterThanPrevious++
            previousSum = currentSum
        }
        return greaterThanPrevious
    }

    val testInput = readInputNumbers("Day01_test", "day01")
    val result1 = part1(testInput)
    val result2 = part2(testInput)
    check(result1 == 7) { "Got: $result1" }
    check(result2 == 4) { "Got: $result2" }

    val input = fetchInputDataAsNumbers(1)
    println(part1(input))
    println(part2(input))
}