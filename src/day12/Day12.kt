package day12

import getOrFetchInputData
import readInput

fun main() {

    class Node(val name: String, val paths: MutableList<Node>, var visitable: Boolean = true) {
        val big = name.all { it.isUpperCase() }

        fun findPathToEnd(): Int {
            if (name == "end") return 1
            visitable = big
            return paths.filter { it.visitable }
                .sumOf { it.findPathToEnd() }
                .also { visitable = true }
        }

        fun findPathToEnd2(path: String, token: Boolean): Int {
            if (name == "end") {
                println("${path}end")
                return 1
            }
            val currentState = visitable
            var tokenResult = 0
            if (token) {
                visitable = big
                tokenResult += paths.filter { !it.visitable && it.name != "start" } // use token to visit unvisitable
                    .sumOf { it.findPathToEnd2("$path$name-", false) }
                    .also { visitable = currentState }
            }

            visitable = big
            return tokenResult + paths.filter { it.visitable } // no token
                .sumOf { it.findPathToEnd2("$path$name-", token) }
                .also { visitable = currentState }
        }
    }

    class PathMap(val start: Node)

    fun parseMap(input: List<String>): PathMap {
        val nodes = mutableMapOf<String, Node>()
        input.map { it.split('-') }.forEach { node ->
            if (!nodes.containsKey(node[0])) {
                nodes[node[0]] = Node(node[0], mutableListOf())
            }
            if (!nodes.containsKey(node[1])) {
                nodes[node[1]] = Node(node[1], mutableListOf())
            }
            nodes[node[0]]!!.paths.add(nodes[node[1]]!!)
            nodes[node[1]]!!.paths.add(nodes[node[0]]!!)
        }
        check(nodes.containsKey("end"))
        return PathMap(nodes["start"]!!)
    }

    fun part1(input: List<String>): Int {
        val map = parseMap(input)
        return map.start.findPathToEnd()
    }

    fun part2(input: List<String>): Int {
        val map = parseMap(input)
        return map.start.findPathToEnd2("", true)
    }

    val testInput1 = readInput("Day12_test1", "day12")
    val testInput2 = readInput("Day12_test2", "day12")
    val testInput3 = readInput("Day12_test3", "day12")

    val result1 = part1(testInput1)
    val result2 = part1(testInput2)
    val result3 = part1(testInput3)
    val result4 = part2(testInput1)
    val result5 = part2(testInput2)
    val result6 = part2(testInput3)

    check(result1 == 10) { "Got: $result1" }
    check(result2 == 19) { "Got: $result2" }
    check(result3 == 226) { "Got: $result3" }

    check(result4 == 36) { "Got: $result4" }
    check(result5 == 103) { "Got: $result5" }
    check(result6 == 3509) { "Got: $result6" }

    val input = getOrFetchInputData(12)
    println(part1(input))
    println(part2(input))
}