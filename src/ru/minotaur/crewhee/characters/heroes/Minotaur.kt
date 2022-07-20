package ru.minotaur.crewhee.characters.heroes

import ru.minotaur.crewhee.characters.NPC
import ru.minotaur.crewhee.map.GameMap
import ru.minotaur.crewhee.map.utils.Point

class Minotaur(p : Point, map : GameMap) : NPC(p, map) {
    init {
        updateVision()
    }
}