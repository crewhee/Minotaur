package ru.minotaur.crewhee.characters.logic

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.minotaur.crewhee.map.utils.MapGenerator

class PathFinderChecker {

    @Test
    fun testPath() {
        val mapgen = MapGenerator()
        val map = mapgen.createMap()
        val pathFinder = DijkstraPathFinder(map, map.startPoint, map.endPoint)
        pathFinder.initPathFinder()
        val res = pathFinder.getPath()
        for (y in 0 until res[0].size) {
            for (x in 0 until res.size) {
                print(res[x][y].toString() + ' ')
            }
            print('\n')
        }
        val max = res.maxOf { it.max() }
        assertEquals(max, res[map.endPoint.x][map.endPoint.y])
    }
}