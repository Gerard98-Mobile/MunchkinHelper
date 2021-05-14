package gerard.example.munchkinhelper.util

import gerard.example.munchkinhelper.model.Game

enum class Action{
    DELETE, OPEN, NONE
}

fun interface Callback<T> {
    fun execute(item: T, action: Action)
}
