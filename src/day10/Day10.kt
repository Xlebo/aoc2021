package day10

import getOrFetchInputData
import readInput
import java.util.*

fun main() {

    val opening = setOf('[', '<', '(', '{')
    val bracketMapping = mapOf('[' to ']', '<' to '>', '(' to ')', '{' to '}')
    val scoreMapping1 = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
    val scoreMapping2 = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)


    fun part1(input: List<String>): Int {
        var score = 0
        for (line in input) {
            val stack = Stack<Char>()
            for (letter in line) {
                when (letter) {
                    in opening -> stack.push(bracketMapping[letter])
                    stack.peek() -> stack.pop()
                    else -> {
                        score += scoreMapping1[letter]!!
                        break
                    }
                }
            }
        }
        return score
    }

    fun part2(input: List<String>): Long {
        val scores = mutableListOf<Long>()
        for (line in input) {
            val stack = Stack<Char>()
            for (letter in line) {
                when (letter) {
                    in opening -> stack.push(bracketMapping[letter])
                    stack.peek() -> stack.pop()
                    else -> {
                        stack.clear()
                        break
                    }
                }
            }
            if (stack.empty()) continue
            var score = 0L
            while (stack.isNotEmpty()) {
                val letter = stack.pop()
                score *= 5
                score += scoreMapping2[letter]!!
            }
            scores.add(score)
        }
        return scores.sorted()[(scores.size - 1) / 2]
    }

    val testInput = readInput("Day10_test", "day10")

    val result1 = part1(testInput)
    val result2 = part2(testInput)
    check(result1 == 26397) { "Got: $result1" }
    check(result2 == 288957L) { "Got: $result2" }

    val input = getOrFetchInputData(10)
    println(part1(input))
    println(part2(input))
}