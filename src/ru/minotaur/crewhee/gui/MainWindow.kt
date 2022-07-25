package ru.minotaur.crewhee.gui

import ru.minotaur.crewhee.GAME_NAME
import ru.minotaur.crewhee.map.utils.MAP_HEIGHT
import ru.minotaur.crewhee.map.utils.MAP_WIDTH
import java.awt.*
import java.awt.event.ActionListener
import java.awt.event.KeyListener
import javax.swing.*


class MainWindow (width : Int, height : Int) {
    private val frame = JFrame(GAME_NAME)
    private val cardLayout : JPanel = JPanel(CardLayout())
    private val textArea : JTextArea
//    private val textAreaPanel : JPanel

    private val restartButton = JButton("Restart")
    private val newGameButton = JButton("New game")
    private val saveMapButton = JButton("Save map")
    private val loadMapButton = JButton("Load map")
    private val mainMenuButton = JButton("Main menu")

    private val gameNameLabel = JLabel(GAME_NAME)
    private val mapListLabel = JLabel("Map list")
    private val winLabel = JLabel(" ")

    private val mapListModel = DefaultListModel<String>()
    private val mapList = JList(mapListModel)

    private val gamePanelBox = JPanel()
    private val menuPanelBox = JPanel()


    init {
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        mapList.visibleRowCount = 5

        gameNameLabel.alignmentX = Component.CENTER_ALIGNMENT
        mapListLabel.alignmentX = Component.CENTER_ALIGNMENT
        winLabel.alignmentX = Component.CENTER_ALIGNMENT

        restartButton.alignmentX = Component.CENTER_ALIGNMENT
        newGameButton.alignmentX = Component.CENTER_ALIGNMENT
        saveMapButton.alignmentX = Component.CENTER_ALIGNMENT
        loadMapButton.alignmentX = Component.CENTER_ALIGNMENT
        mainMenuButton.alignmentX = Component.CENTER_ALIGNMENT

        restartButton.actionCommand = "restart"
        newGameButton.actionCommand = "new"
        saveMapButton.actionCommand = "save"
        loadMapButton.actionCommand = "load"
        mainMenuButton.actionCommand = "main_menu"

        textArea = JTextArea(MAP_HEIGHT, MAP_WIDTH / 2)
        gamePanelBox.layout = BoxLayout(gamePanelBox, BoxLayout.Y_AXIS)

        textArea.isEditable = false
        textArea.font = Font(Font.MONOSPACED, Font.PLAIN, 22)
        textArea.alignmentX = Component.CENTER_ALIGNMENT
        textArea.preferredSize = Dimension(MAP_WIDTH * 16, MAP_HEIGHT * 24)
        textArea.minimumSize = Dimension(MAP_WIDTH * 16, MAP_HEIGHT * 24 + 15)
        textArea.maximumSize = Dimension(MAP_WIDTH * 16, MAP_HEIGHT * 24 + 15)

        gamePanelBox.add(textArea, BorderLayout.CENTER)
        gamePanelBox.add(winLabel, BorderLayout.CENTER)
        gamePanelBox.add(restartButton, BorderLayout.CENTER)
        gamePanelBox.add(saveMapButton, BorderLayout.CENTER)
        gamePanelBox.add(mainMenuButton, BorderLayout.CENTER)
        gamePanelBox.alignmentX = Component.CENTER_ALIGNMENT

        menuPanelBox.layout = BoxLayout(menuPanelBox, BoxLayout.Y_AXIS)
        menuPanelBox.alignmentX = Component.CENTER_ALIGNMENT
        menuPanelBox.alignmentY = Component.CENTER_ALIGNMENT
        menuPanelBox.add(Box.createRigidArea(Dimension(0, 25)))
        menuPanelBox.add(gameNameLabel)
        menuPanelBox.add(Box.createRigidArea(Dimension(0, 25)))
        menuPanelBox.add(newGameButton)
        menuPanelBox.add(Box.createRigidArea(Dimension(0, 25)))
        menuPanelBox.add(mapListLabel)
        menuPanelBox.add(mapList)
        menuPanelBox.add(loadMapButton)

        cardLayout.add("menu", menuPanelBox)
        cardLayout.add("game", gamePanelBox)

        frame.add(cardLayout, BorderLayout.CENTER)
        frame.preferredSize = Dimension(width, height)
    }

    fun setMapList(maps: List<String>) {
        mapListModel.clear()
        mapListModel.addAll(maps)
    }

    fun setVisible() {
        frame.pack()
        frame.isVisible = true
    }

    fun setKeyListener(l : KeyListener) {
        textArea.addKeyListener(l)
        textArea.isFocusable = true
    }

    fun setActionListener(l : ActionListener) {
        restartButton.addActionListener(l)
        newGameButton.addActionListener(l)
        saveMapButton.addActionListener(l)
        loadMapButton.addActionListener(l)
        mainMenuButton.addActionListener(l)
    }

    fun refreshField(newMapView : String) {
        textArea.text = newMapView
        textArea.repaint()
    }

    fun showCard(name : String) {
        mapListLabel.text = "Map list"
        (cardLayout.layout as CardLayout).show(cardLayout, name)
    }

    fun getCurrentMapChoice(): String? {
        val mapIndex = mapList.selectedIndex
        return if (mapIndex != -1) mapListModel.get(mapIndex) else null
    }

    fun setRestartKey(s: String) {
        restartButton.text = s
    }

    fun setWinLabel(s: String) {
        winLabel.text = s
    }

    fun informBadMap() {
        mapListLabel.text = "Failed to load map!"
    }

}