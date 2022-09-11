package ru.minotaur.crewhee.map.utils

enum class MapSymbols(val c : Char) {
    WALL('█'), FREE('·'), ENTRANCE('-'), EXIT('@'), UNKNOWN(' '), FOG('×'), ENEMY('M'), HERO('T')
}