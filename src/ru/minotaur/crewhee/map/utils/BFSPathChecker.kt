package ru.minotaur.crewhee.map.utils

class BFSPathChecker(val mapMatrix: Array<IntArray>) {
    private val pathLength = Array(mapMatrix.size) { IntArray(mapMatrix[0].size) { -1 } }
    private var lastVisited : Point = Point(-1, -1)

    fun initPathChecker(start : Point) {
        val visited = ArrayDeque<Point>()
        visited.addLast(start)
        pathLength[start.x][start.y] = 0
        while (visited.isNotEmpty()) {
            lastVisited = visited.removeFirst()
            val x = lastVisited.x; val y = lastVisited.y
            if (x + 1 < mapMatrix.size) {
                if (mapMatrix[x+1][y] == MapStates.FREE.i &&
                    pathLength[x+1][y] == -1) {
                    pathLength[x+1][y] = pathLength[x][y] + 1
                    visited.addLast(Point(x+1, y))
                }
            }
            if (x - 1 >= 0) {
                if (mapMatrix[x-1][y] == MapStates.FREE.i &&
                    pathLength[x-1][y] == -1) {
                    pathLength[x-1][y] = pathLength[x][y] + 1
                    visited.addLast(Point(x-1, y))
                }
            }
            if (y + 1 < mapMatrix[0].size) {
                if (mapMatrix[x][y+1] == MapStates.FREE.i &&
                    pathLength[x][y+1] == -1) {
                    pathLength[x][y+1] = pathLength[x][y] + 1
                    visited.addLast(Point(x, y+1))
                }
            }
            if (y - 1 >= 0) {
                if (mapMatrix[x][y-1] == MapStates.FREE.i &&
                    pathLength[x][y-1] == -1) {
                    pathLength[x][y-1] = pathLength[x][y] + 1
                    visited.addLast(Point(x, y-1))
                }
            }
        }
    }

    fun checkMap() : Boolean {
        var res = true
        outer@ for (x in 0 until mapMatrix.size) {
            for (y in 0 until mapMatrix[0].size) {
                if (mapMatrix[x][y] == 0 && pathLength[x][y] == -1) {
                    res = false
                    break@outer
                }
            }
        }
        return res
    }

    fun getEndPoint() : Point {
        return lastVisited.copy()
    }
}