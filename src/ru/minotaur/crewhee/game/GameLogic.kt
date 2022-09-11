package ru.minotaur.crewhee.game

import ru.minotaur.crewhee.characters.CharacterFactory
import ru.minotaur.crewhee.characters.NPC
import ru.minotaur.crewhee.characters.heroes.Theseus
import ru.minotaur.crewhee.game.utils.GameState
import ru.minotaur.crewhee.map.GameMap
import ru.minotaur.crewhee.map.MapView

class GameLogic(map: GameMap) {
    private val gameMap: GameMap = map
    private val characterFactory: CharacterFactory = CharacterFactory(map)
    private val hero: Theseus = characterFactory.createHero()
    private val chars: List<NPC> = characterFactory.createChars()
    private val view: MapView = MapView(map, listOf(hero, chars[0]))

    fun getMapView(): String {
        return view.applyMask(hero.getMask())
    }

    fun makeMove(k: Int): GameState {
        if (hero.makeMove(k)) {
            if (chars.any { it.pos == hero.pos }) return GameState.LOST
            chars.forEach { it.makeMove() }
            hero.updateVision()
            chars.forEach { it.updateVision() }
        }
        return if (chars.any { it.pos == hero.pos }) GameState.LOST
        else if (gameMap.endPoint == hero.pos) GameState.WON
        else GameState.PLAYING
    }

    fun getCurrentMap(): GameMap {
        return gameMap
    }
}