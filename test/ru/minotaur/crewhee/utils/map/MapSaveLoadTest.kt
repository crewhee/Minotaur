package ru.minotaur.crewhee.utils.map

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.minotaur.crewhee.map.utils.BFSPathChecker
import ru.minotaur.crewhee.map.utils.MapGenerator
import ru.minotaur.crewhee.map.utils.MapLoader
import ru.minotaur.crewhee.map.utils.MapSaver
import java.nio.file.Path

class MapSaveLoadTest {

    @Test
    fun testSaveLoadMap() {
        val mapgen = MapGenerator()
        val mapToSave = mapgen.createMap()
        val saver = MapSaver("./test/resources/maps/")
        val mapName = saver.saveMap(mapToSave)
        val loader = MapLoader(Path.of("./test/resources/maps/"))
        val mapToLoad = loader.loadMap(mapName)
        assertNotNull(mapToLoad)
        mapToLoad ?: return   // asserted earlier
        assertArrayEquals(mapToSave.map, mapToLoad.map)
        val checker = BFSPathChecker(mapToLoad.map)
        checker.initPathChecker(mapToLoad.startPoint)
        assertTrue(checker.checkMap())
    }

    @Test
    fun loadMapList() {
        val loader = MapLoader(Path.of("./test/resources/maps/"))
        val mapList = loader.loadMapList()
        if(mapList.isNotEmpty())
            assertTrue(mapList.all {
                it.matches(Regex("[1-9]\\d*_[1-9]\\d*_[1-9]\\d*\\.txt"))
            })
    }
}