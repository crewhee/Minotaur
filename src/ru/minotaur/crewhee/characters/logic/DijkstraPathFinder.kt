package ru.minotaur.crewhee.characters.logic

import ru.minotaur.crewhee.map.GameMap
import ru.minotaur.crewhee.map.utils.Point
import java.util.*

class DijkstraPathFinder(map: GameMap, start: Point, end: Point) {
    private val endPoint = end
    private val startPoint = start
    val gameMap = map
    private val prev: Array<Array<Point>> = Array(gameMap.width) { Array(gameMap.height) { Point(-1, -1) } }
    private val dist: Array<IntArray> = Array(gameMap.width) { IntArray(gameMap.width) { Int.MAX_VALUE } }


    fun initPathFinder() {

        val next: SortedSet<Pair<Point, Int>> = TreeSet { p1, p2 ->
            if (p1.second.compareTo(p2.second) == 0) {
                if (p1.first.x.compareTo(p2.first.x) == 0) {
                        p1.first.y.compareTo(p2.first.y)
                    }
                    else {
                        p1.first.x.compareTo(p2.first.x)
                }
            } else p1.second.compareTo(p2.second)
        }

        dist[startPoint.x][startPoint.y] = 0
        next.add(Pair(startPoint, 0))
        prev[startPoint.x][startPoint.y] = startPoint

        while (next.isNotEmpty()) {
            val current = next.first()
            val p = current.first
            if (p == endPoint) break
            val d = current.second
            next.remove(current)
            for (i in listOf(
                Point(p.x - 1, p.y), Point(p.x + 1, p.y), Point(p.x, p.y - 1), Point(p.x, p.y + 1)
            )) {
                if (!(i.x in 0 until gameMap.width && i.y in 0 until gameMap.height)) continue
                if (!gameMap.isAccessible(i.x, i.y)) continue
                if (d + 1 < dist[i.x][i.y]) {
                    next.remove(Pair(Point(i.x, i.y), dist[i.x][i.y]))
                    dist[i.x][i.y] = d + 1
                    prev[i.x][i.y] = p
                    val nextSize = next.size
                    next.add(Pair(Point(i.x, i.y), d + 1))
                    if (next.size != nextSize + 1) {
                        print("aaaa")
                    }
                }
            }
        }
    }

    fun getPath() : Array<IntArray> {
        val res = Array(gameMap.width) {IntArray(gameMap.height) {-1} }
        res[endPoint.x][endPoint.y] = dist[endPoint.x][endPoint.y]
        var x = endPoint.x
        var y = endPoint.y
        while (res[startPoint.x][startPoint.y] == -1) {
            val p = prev[x][y]
            x = p.x
            y = p.y
            res[x][y] = dist[x][y]
        }
        return res
    }
}