package ru.minotaur.crewhee.map

import ru.minotaur.crewhee.characters.GameCharacter
import ru.minotaur.crewhee.characters.heroes.Minotaur
import ru.minotaur.crewhee.characters.heroes.Theseus
import ru.minotaur.crewhee.map.utils.MapStates
import ru.minotaur.crewhee.map.utils.MapSymbols
import ru.minotaur.crewhee.map.utils.MapViewStates

class MapView(map: GameMap, chars : List<GameCharacter>) {
    private val mapMatrix = map.map
    private val characters = chars
    private val mapView: List<List<MapSymbols>> = mapMatrix.map { l ->
        l.map {
            when (it) {
                MapStates.FREE.i -> MapSymbols.FREE
                MapStates.WALL.i -> MapSymbols.WALL
                MapStates.ENTRANCE.i -> MapSymbols.ENTRANCE
                MapStates.EXIT.i -> MapSymbols.EXIT
                else -> MapSymbols.UNKNOWN
            }
        }
    }

    fun applyMask(mapVision: List<List<MapViewStates>>): String {
        val res = MutableList(mapVision.size) { MutableList(mapVision[0].size) { MapSymbols.WALL } }
        for (x in mapVision.indices) {
            for (y in mapVision[0].indices) {
                res[x][y] = when (mapView[x][y]) {
                    MapSymbols.WALL -> when (mapVision[x][y]) {
                        MapViewStates.UNKNOWN -> MapSymbols.UNKNOWN
                        else -> MapSymbols.WALL
                    }
                    MapSymbols.EXIT -> when (mapVision[x][y]) {
                        MapViewStates.UNKNOWN -> MapSymbols.UNKNOWN
                        else -> MapSymbols.EXIT
                    }
                    MapSymbols.ENTRANCE -> when (mapVision[x][y]) {
                        MapViewStates.UNKNOWN -> MapSymbols.UNKNOWN
                        else -> MapSymbols.ENTRANCE
                    }
                    MapSymbols.FREE -> when (mapVision[x][y]) {
                        MapViewStates.UNKNOWN -> MapSymbols.UNKNOWN
                        MapViewStates.IN_SIGHT -> MapSymbols.FREE
                        else -> MapSymbols.FOG
                    }
                    else -> MapSymbols.UNKNOWN
                }
            }
        }
        characters.forEach {
            if (mapVision[it.pos.x][it.pos.y] == MapViewStates.IN_SIGHT)
                when (it) {
                    is Minotaur -> res[it.pos.x][it.pos.y] = MapSymbols.ENEMY
                    is Theseus -> res[it.pos.x][it.pos.y] = MapSymbols.HERO
                    else -> {}
                }
        }
        val sb = StringBuilder()
        for (y in res[0].indices) {
            for (x in res.indices) {
                sb.append(res[x][y].c)
            }
            sb.append('\n')
        }
        return sb.toString()
    }


}