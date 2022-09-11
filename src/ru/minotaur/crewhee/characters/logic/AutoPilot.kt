package ru.minotaur.crewhee.characters.logic

import ru.minotaur.crewhee.map.GameMap
import ru.minotaur.crewhee.map.utils.Point

class AutoPilot(map: GameMap, point: Point) {
    val gameMap: GameMap = map
    val p = point
    var currentEndPoint: Point = gameMap.startPoint.copy()
    var currentStep: Int = 0
    private var currentPathFinder : DijkstraPathFinder
    private var currentPath: Array<IntArray>

    init {
        currentPathFinder = DijkstraPathFinder(gameMap, p, currentEndPoint)
        currentPathFinder.initPathFinder()
        currentPath = currentPathFinder.getPath()
    }

    fun followPath() {
        val moves = mutableSetOf<Point>()
        if (currentEndPoint == p) {
            reloadPath()
        }

        if (gameMap.isAccessible(p.x-1, p.y) && currentPath[p.x-1][p.y] == currentStep + 1) {
            moves.add(Point(p.x-1, p.y))
        }
        if (gameMap.isAccessible(p.x+1, p.y) && currentPath[p.x+1][p.y] == currentStep + 1) {
            moves.add(Point(p.x+1, p.y))
        }
        if (gameMap.isAccessible(p.x, p.y-1) && currentPath[p.x][p.y-1] == currentStep + 1) {
            moves.add(Point(p.x, p.y-1))
        }
        if (gameMap.isAccessible(p.x, p.y+1) && currentPath[p.x][p.y+1] == currentStep + 1) {
            moves.add(Point(p.x, p.y+1))
        }
        currentStep++
        val res = moves.random()
        p.x = res.x
        p.y = res.y
    }

    private fun reloadPath() {
        while (currentEndPoint == p)
            currentEndPoint = gameMap.getRandomAccessiblePoint()
        currentPathFinder = DijkstraPathFinder(gameMap, p, currentEndPoint)
        currentPathFinder.initPathFinder()
        currentPath = currentPathFinder.getPath()
        currentStep = 0
    }
}
