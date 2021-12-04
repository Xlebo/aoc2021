package day04

import getOrFetchInputData
import readInput

class Board(
    val rows: List<MutableList<Int>>,
) {
    private val columns: List<MutableList<Int>> = (rows.indices)
        .map { it to rows.map { x -> x[it] } }
        .map { (_, v) -> v.toMutableList() }

    fun hasBingo(): Boolean {
        return (rows + columns).any { x -> x.isEmpty() }
    }

    fun remove(number: Int) {
        rows.find { x -> x.contains(number) }?.remove(number)
        columns.find { x -> x.contains(number) }?.remove(number)
    }
}

fun main() {
    fun parseNumbers(input: List<String>): List<Int> = input[0].split(',').map { it.toInt() }

    fun parseBoards(input: List<String>): List<Board> = input.subList(1, input.size)
        .windowed(6, 6)
        .map {
            it.subList(1, it.size)
                .map { row ->
                    "(\\d+)".toRegex()
                        .findAll(row)
                        .map { number -> number.groupValues[1].toInt() }
                        .toMutableList()
                }
        }
        .map { x -> Board(x) }

    fun part1(input: List<String>): Int {
        val boards = parseBoards(input)
        var winner: Board? = null
        var multiplier = 0
        for (number in parseNumbers(input)) {
            for (board in boards) {
                board.remove(number)
                if (board.hasBingo()) {
                    winner = board
                    multiplier = number
                    break
                }
            }
            if (winner != null) break
        }

        return winner!!.rows.sumOf { it.sum() } * multiplier
    }

    fun part2(input: List<String>): Int {
        val numbers = parseNumbers(input)
        val boards = parseBoards(input).toMutableSet()
        var lastWinner: Board? = null
        var multiplier = 0
        for (number in numbers) {
            for (board in boards) {
                board.remove(number)
                if (board.hasBingo()) {
                    if (boards.size == 1) {
                        lastWinner = board
                        multiplier = number
                        break
                    }
                }
            }
            if (lastWinner != null) break
            if (boards.all { it.hasBingo() }) {
                lastWinner = boards.last()
                multiplier = number
                break
            }
            boards.removeAll { it.hasBingo() }
        }

        return lastWinner!!.rows.sumOf { it.sum() } * multiplier
    }

    val testInput = readInput("Day04_test", "day04")
    val result1 = part1(testInput)
    val result2 = part2(testInput)
    check(result1 == 4512) { "Got: $result1" }
    check(result2 == 1924) { "Got: $result2" }

    val input = getOrFetchInputData(4)
    println(part1(input))
    println(part2(input))
}