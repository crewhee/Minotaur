package ru.minotaur.crewhee.utlis.map

import org.junit.jupiter.api.Test
import ru.minotaur.crewhee.map.GameMap
import ru.minotaur.crewhee.map.MapView
import ru.minotaur.crewhee.map.utils.MAP_HEIGHT
import ru.minotaur.crewhee.map.utils.MAP_WIDTH
import ru.minotaur.crewhee.map.utils.MapGenerator
import ru.minotaur.crewhee.map.utils.MapViewStates
import kotlin.random.Random

class MapViewTest {


    private fun makeRandomMask(m : Array<IntArray>) : List<List<MapViewStates>> {
        val mask = MutableList(MAP_WIDTH) {MutableList(MAP_HEIGHT) {MapViewStates.UNKNOWN} }
        val x = Random.nextInt(0, MAP_WIDTH-5)
        val y = Random.nextInt(0, MAP_HEIGHT-5)
        for (i in x until x+3) {
            for (j in y until y+3) {
                mask[i][j] = MapViewStates.IN_SIGHT
            }
        }
        return mask
    }

    @Test
    fun testMapView() {
//        val mapgen = MapGenerator()
//        val map = GameMap(mapgen.createMap(), start, end)
//        val mask = makeRandomMask(map.map)
//        val view = MapView(map)
//        val res = view.applyMask(mask)
//        print(res)
    }
}