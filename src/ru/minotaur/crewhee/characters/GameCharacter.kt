package ru.minotaur.crewhee.characters

import ru.minotaur.crewhee.map.GameMap
import ru.minotaur.crewhee.map.utils.MapViewStates
import ru.minotaur.crewhee.map.utils.Point

abstract class GameCharacter(position: Point, map: GameMap) {
    private val visionLength: Int = 3
    private val mapVision = List(map.width) { MutableList(map.height) { MapViewStates.UNKNOWN } }
    protected val gameMap: GameMap = map
    var pos: Point = position.copy()


    open fun updateVision() {
        for (x in mapVision.indices) {
            for (y in mapVision[0].indices) {
                mapVision[x][y] = when (mapVision[x][y]) {
                    MapViewStates.UNKNOWN -> MapViewStates.UNKNOWN
                    MapViewStates.IN_SIGHT -> MapViewStates.SEEN
                    MapViewStates.SEEN -> MapViewStates.SEEN
                }
            }
        }
        if (pos.x + 1 < gameMap.width) {
            mapVision[pos.x + 1][pos.y] = MapViewStates.IN_SIGHT
            if (gameMap.isAccessible(pos.x + 1, pos.y) && pos.x + 2 < gameMap.width) {
                mapVision[pos.x + 2][pos.y] = MapViewStates.IN_SIGHT
            }
        }
        if (pos.y + 1 < gameMap.height) {
            mapVision[pos.x][pos.y + 1] = MapViewStates.IN_SIGHT
            if (gameMap.isAccessible(pos.x, pos.y + 1) && pos.y + 2 < gameMap.height) {
                mapVision[pos.x][pos.y + 2] = MapViewStates.IN_SIGHT
            }
        }
        if (pos.x - 1 >= 0) {
            mapVision[pos.x - 1][pos.y] = MapViewStates.IN_SIGHT
            if (gameMap.isAccessible(pos.x - 1, pos.y) && pos.x - 2 >= 0) {
                mapVision[pos.x - 2][pos.y] = MapViewStates.IN_SIGHT
            }
        }
        if (pos.y - 1 >= 0) {
            mapVision[pos.x][pos.y - 1] = MapViewStates.IN_SIGHT
            if (gameMap.isAccessible(pos.x, pos.y - 1) && pos.y - 2 >= 0) {
                mapVision[pos.x][pos.y - 2] = MapViewStates.IN_SIGHT
            }
        }

        for (p in listOf(
            Point(pos.x, pos.y),
            Point(pos.x + 1, pos.y + 1),
            Point(pos.x + 1, pos.y - 1),
            Point(pos.x - 1, pos.y + 1),
            Point(pos.x - 1, pos.y - 1)
        )) {
            if (p.x in 0 until gameMap.width && p.y in 0 until gameMap.height) {
                mapVision[p.x][p.y] = MapViewStates.IN_SIGHT
            }
        }
    }

    fun getMask(): List<List<MapViewStates>> {
        return mapVision
    }
}