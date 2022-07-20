package ru.minotaur.crewhee.map.utils

data class Room(
    val leftUpperCorner: Pair<Int, Int>, val rightLowerCorner: Pair<Int, Int>
) {
    val center : Pair<Int, Int> = Pair((rightLowerCorner.first + leftUpperCorner.first) / 2,
        (rightLowerCorner.second + leftUpperCorner.second) / 2
    )
}