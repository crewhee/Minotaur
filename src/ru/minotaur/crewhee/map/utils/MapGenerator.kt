package ru.minotaur.crewhee.map.utils

import ru.minotaur.crewhee.map.GameMap
import java.lang.Integer.min
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.math.max
import kotlin.math.sqrt
import kotlin.random.Random

class MapGenerator {
    private val seed = (LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) % MAX_SEED).toInt()
    private val mapMatrix = Array(MAP_WIDTH) { IntArray(MAP_HEIGHT) { MapStates.WALL.i } }
    private val rooms: MutableList<Room> = mutableListOf()
    private val roomNumber = max(ROOM_NUMBER,
        (sqrt((MAP_WIDTH * MAP_HEIGHT).toDouble()) / 2).toInt() - 1)
    private val gen = Random(seed)
    private lateinit var start : Point
    private lateinit var end : Point


    private fun generateRandomRoom() : Room {
        val x = gen.nextInt(0, MAP_WIDTH - MAX_ROOM_SIZE)
        val y = gen.nextInt(0, MAP_HEIGHT - MAX_ROOM_SIZE)
        val x2 = gen.nextInt(x + MIN_ROOM_SIZE, min(MAP_WIDTH, x + MAX_ROOM_SIZE))
        val y2 = gen.nextInt(y + MIN_ROOM_SIZE, min(MAP_HEIGHT, y + MAX_ROOM_SIZE))
        return Room(Pair(x, y), Pair(x2, y2))
    }

    private fun isPlaceable(room : Room) : Boolean {
        val x = (room.leftUpperCorner.first - 1).coerceAtLeast(0)
        val x2 = min(room.rightLowerCorner.first + 1, MAP_WIDTH - 1)
        val y = (room.leftUpperCorner.second - 1).coerceAtLeast(0)
        val y2 = min(room.rightLowerCorner.second + 1, MAP_HEIGHT - 1)
        for (i in x .. x2) {
            for (j in y .. y2) {
                if (mapMatrix[i][j] == MapStates.FREE.i) return false
            }
        }
        return true
    }

    private fun updateMapWithRoom(room: Room) {
        for (x in room.leftUpperCorner.first..room.rightLowerCorner.first) {
            for (y in room.leftUpperCorner.second..room.rightLowerCorner.second) {
                mapMatrix[x][y] = MapStates.FREE.i
            }
        }
    }

    private fun generateRooms() {
        var counter = 0
        while (rooms.size < roomNumber) {
            counter++
            if (counter > MAP_WIDTH * MAP_HEIGHT * 10) {
                rooms.clear()
                mapMatrix.forEach {
                    it.fill(MapStates.WALL.i)
                }
                counter = 0
            }
            val room = generateRandomRoom()
            if (isPlaceable(room)) {
                rooms.add(room)
                updateMapWithRoom(rooms.last())
            } else continue
        }
    }

    private fun makeHorizontalCorridor(xOld : Int, xNew : Int, y : Int) {
        for (i in min(xOld, xNew)..xOld.coerceAtLeast(xNew)) {
            mapMatrix[i][y] = MapStates.FREE.i
        }
    }

    private fun makeVerticalCorridor(yOld : Int, yNew : Int, x : Int) {
        for (i in min(yOld, yNew)..yOld.coerceAtLeast(yNew)) {
            mapMatrix[x][i] = MapStates.FREE.i
        }
    }

    private fun connectDots(a : Pair<Int, Int>, b : Pair<Int, Int>) {
        when (gen.nextBoolean()) {
            true -> {
                makeHorizontalCorridor(a.first, b.first, a.second)
                makeVerticalCorridor(a.second, b.second, b.first)
            }
            false -> {
                makeVerticalCorridor(a.second, b.second, a.first)
                makeHorizontalCorridor(a.first, b.first, b.second)
            }
        }
    }

    private fun connectRooms() {
        rooms.sortBy { it.leftUpperCorner.first }
        for (i in 1 until rooms.size) {
            connectDots(rooms[i-1].center, rooms[i].center)
        }
        if (CHAOS) {
            val shuffled = rooms.shuffled()
            for (i in 1 until shuffled.size) {
                connectDots(shuffled[i-1].center, shuffled[i].center)
            }
        }
    }

    private fun reset() {
        mapMatrix.forEach {
            it.map {
                MapStates.WALL.i
            }
        }
        rooms.clear()
    }

    private fun getRandomAccesiblePoint() : Point {
        var x = gen.nextInt(0, MAP_WIDTH)
        var y = gen.nextInt(0, MAP_HEIGHT)
        while (mapMatrix[x][y] == MapStates.WALL.i) {
            x = gen.nextInt(0, MAP_WIDTH)
            y = gen.nextInt(0, MAP_HEIGHT)
        }
        return Point(x, y)
    }

    private fun initStartEnd() {
        start = getRandomAccesiblePoint()
        val checker = BFSPathChecker(mapMatrix)
        checker.initPathChecker(start)
        end = checker.getEndPoint()
        mapMatrix[start.x][start.y] = MapStates.ENTRANCE.i
        mapMatrix[end.x][end.y] = MapStates.EXIT.i
    }

    fun getStart() : Point {
        return start
    }

    fun createMap(): GameMap {
        reset()
        generateRooms()
        connectRooms()
        initStartEnd()
        return GameMap(mapMatrix, start, end)
    }
}