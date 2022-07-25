package ru.minotaur.crewhee.characters

import ru.minotaur.crewhee.characters.heroes.Minotaur
import ru.minotaur.crewhee.characters.heroes.Theseus
import ru.minotaur.crewhee.map.GameMap

class CharacterFactory(map : GameMap) {
    val gameMap = map

    fun createHero() : Theseus {
        return Theseus(gameMap.startPoint, gameMap)
    }

    fun createChars() : List<NPC> {
        return listOf(
            Minotaur(gameMap.endPoint, gameMap)
        )
    }
}