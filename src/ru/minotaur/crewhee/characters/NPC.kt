package ru.minotaur.crewhee.characters

import ru.minotaur.crewhee.characters.logic.AutoPilot
import ru.minotaur.crewhee.map.GameMap
import ru.minotaur.crewhee.map.utils.Point

abstract class NPC(p : Point, map : GameMap) : GameCharacter(p, map) {
    private val ap = AutoPilot(map, this.pos)
    fun makeMove() {
        ap.followPath()
    }
}