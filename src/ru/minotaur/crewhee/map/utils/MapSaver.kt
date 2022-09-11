package ru.minotaur.crewhee.map.utils

import ru.minotaur.crewhee.map.GameMap
import java.io.File
import java.nio.file.Path

class MapSaver (mapsTestPath : String? = null) {
    private val mapsPath : Path = Path.of(mapsTestPath ?: "./maps")
    private val folder : File = mapsPath.toFile()

    init {
        if (!folder.isDirectory) {
            folder.mkdirs()
        }
    }

    fun saveMap(map : GameMap) : String {
        val seed = map.getSeed()
        val mapName = "${seed}_${map.width}_${map.height}.txt"
        val mapFile = mapsPath.resolve(mapName).toFile()
        if (mapFile.exists()) {
            mapFile.delete()    // map might be already saved or it might be some blocking file
        }
        mapFile.createNewFile()
        mapFile.bufferedWriter().use {
            it.write("${map.width} ${map.height}\n")
            map.map.forEach { arr ->
                arr.forEach { i ->
                    it.write("$i ")
                }
                it.write("\n")
            }
        }
        return mapName
    }
}