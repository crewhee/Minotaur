package ru.minotaur.crewhee.map.utils

import ru.minotaur.crewhee.map.GameMap
import java.nio.file.Path

class MapLoader(path: Path) {
    val p = path
    val dir = p.toFile()

    init {
        if (!dir.isDirectory) {
            dir.mkdirs()
        }
    }

    fun loadMapList(): List<String> {
        val res = mutableListOf<String>()
        val files = dir.listFiles { _, name -> Regex("[1-9]\\d*_[1-9]\\d*_[1-9]\\d*\\.txt").matches(name) }
        files ?: return res
        for (file in files) {
            res.add(file.name)
        }
        return res
    }

    fun loadMap(mapFileName: String): GameMap? {
        val mapFile = dir.resolve(mapFileName)
        val reader = mapFile.bufferedReader()
        val dims =
            reader.readLine().split(' ')
                .filter { it.isNotBlank() }
                .onEach { if (!it.all { it.isDigit() }) return null }
                .map { it.toInt() }
        if (dims.size != 2) return null
        val res = Array(dims[0]) { IntArray(dims[1]) { -1 } }
        for (i in 0 until dims[0]) {
            val temp = reader.readLine().split(' ')
                .filter { it.isNotBlank() }
                .onEach { if (!it.all { it.isDigit() }) return null }
                .map { it.toInt() }
                .onEach { if (it !in 0..MapStates.EXIT.i) return null }
                .toIntArray()
            if (temp.size != dims[1]) return null
            res[i] = temp
        }
        val badPoint = Point(-1, -1)
        var start = badPoint
        var end = badPoint
        for (x in 0 until dims[0]) {
            for (y in 0 until dims[1]) {
                if (res[x][y] == 2) {
                    if (start != badPoint) return null
                    else start = Point(x, y)
                }
                if (res[x][y] == 3) {
                    if (end != badPoint) return null
                    else end = Point(x, y)
                }
            }
        }
        if (start == badPoint) return null
        if (end == badPoint) return null
        return GameMap(res, start, end)
    }
}