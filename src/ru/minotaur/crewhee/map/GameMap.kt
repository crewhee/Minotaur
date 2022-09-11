package ru.minotaur.crewhee.map

import ru.minotaur.crewhee.map.utils.*
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.random.Random

class GameMap(mapMatrix: Array<IntArray>, start: Point, end: Point) {
    val map = mapMatrix
    val startPoint = start
    val endPoint = end
    private val seed = (LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) % MAX_SEED).toInt()
    private val gen = Random(seed)

    val width: Int = mapMatrix.size
    val height: Int = if (width > 0) mapMatrix[0].size else throw RuntimeException("Broken map")

    fun getRandomAccessiblePoint() : Point {
        var x = gen.nextInt(0, width)
        var y = gen.nextInt(0, height)
        while (map[x][y] == MapStates.WALL.i) {
            x = gen.nextInt(0, width)
            y = gen.nextInt(0, height)
        }
        return Point(x, y)
    }

    fun isAccessible(x: Int, y: Int) : Boolean {
        return if (x < 0 || x >= width || y < 0 || y >= height) {
            false
        } else {
            map[x][y] != MapStates.WALL.i
        }
    }

    fun getSeed() : Int {
        return seed
    }
}