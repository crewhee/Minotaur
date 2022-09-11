package ru.minotaur.crewhee.characters.heroes

import ru.minotaur.crewhee.characters.GameCharacter
import ru.minotaur.crewhee.map.GameMap
import ru.minotaur.crewhee.map.utils.Point

class Theseus(p: Point, map : GameMap) : GameCharacter(p, map) {
    init {
        updateVision()
    }

    fun makeMove(key: Int): Boolean {
        when (key) {
            38 -> if (gameMap.isAccessible(pos.x, pos.y - 1)) {
                pos.y -= 1
                return true
            }
            40 -> if (gameMap.isAccessible(pos.x, pos.y + 1)) {
                pos.y += 1
                return true
            }
            37 -> if (gameMap.isAccessible(pos.x - 1, pos.y)) {
                pos.x -= 1
                return true
            }
            39 -> if (gameMap.isAccessible(pos.x + 1, pos.y)) {
                pos.x += 1
                return true
            }
            else -> return false
        }
        return false
    }
}