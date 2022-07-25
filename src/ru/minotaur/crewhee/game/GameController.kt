package ru.minotaur.crewhee.game

import ru.minotaur.crewhee.game.utils.GameState
import ru.minotaur.crewhee.gui.MainWindow
import ru.minotaur.crewhee.map.GameMap
import ru.minotaur.crewhee.map.utils.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.nio.file.Path

class GameController : KeyAdapter(), ActionListener {
    private val window = MainWindow(MAP_WIDTH * 16 + 50, MAP_HEIGHT * 24 + 150)
    private val mapLoader = MapLoader(Path.of("./maps/"))
    private val mapSaver = MapSaver()
    private lateinit var gameLogic: GameLogic
    var state: GameState = GameState.MAIN_MENU

    init {
        window.setActionListener(this)
        window.setKeyListener(this)
        val mapList = mapLoader.loadMapList()
        window.setMapList(mapList)
    }

    private fun startGame(map: GameMap?) {
        val gameMap: GameMap
        window.setRestartKey("Restart")
        window.setWinLabel(" ")
        if (map == null)
            gameMap = MapGenerator().createMap()
        else
            gameMap = map
        gameLogic = GameLogic(gameMap)
        state = GameState.PLAYING
        window.refreshField(gameLogic.getMapView())
        window.showCard("game")
    }

    override fun keyPressed(e: KeyEvent?) {
        when (state) {
            GameState.MAIN_MENU -> {}
            GameState.PLAYING -> if (e != null) {
                when (gameLogic.makeMove(e.keyCode)) {
                    GameState.PLAYING -> {}
                    GameState.LOST -> {
                        state = GameState.LOST
                        window.setWinLabel("You lost!")
                    }
                    GameState.WON -> {
                        state = GameState.WON
                        window.setRestartKey("New map")
                        window.setWinLabel("You won!")
                    }
                    else -> {}
                }
                window.refreshField(gameLogic.getMapView())
            }
            else -> {}
        }
    }

    override fun actionPerformed(e: ActionEvent?) {
        when (e?.actionCommand) {
            "restart" -> {
                when (state) {
                    GameState.LOST, GameState.PLAYING -> startGame(gameLogic.getCurrentMap())
                    GameState.WON -> startGame(null)
                    else -> {}
                }
            }
            "new" -> startGame(null)
            "save" -> mapSaver.saveMap(gameLogic.getCurrentMap())
            "load" -> {
                val mapName = window.getCurrentMapChoice() ?: return
                val map = mapLoader.loadMap(mapName)
                if (map != null)
                    startGame(map)
                else
                    window.informBadMap()
            }
            "main_menu" -> {
                window.setMapList(mapLoader.loadMapList())
                window.showCard("menu")
                state = GameState.MAIN_MENU
            }
            else -> {}
        }
    }

    fun run() = window.setVisible()
}