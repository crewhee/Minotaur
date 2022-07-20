package ru.minotaur.crewhee.utlis.map

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import ru.minotaur.crewhee.map.utils.BFSPathChecker
import ru.minotaur.crewhee.map.utils.MapGenerator

class MapGeneratorTest {
    @Test
    fun generatorTest() {
        repeat (10) {
            val mapgen = MapGenerator()
            val map = mapgen.createMap()
            val bfschecker = BFSPathChecker(map.map)
            bfschecker.initPathChecker(mapgen.getStart())
            assertTrue(bfschecker.checkMap())
        }
    }
}