package gerard.example.munchkinhelper.util

enum class Action{
    DELETE, OPEN, NONE
}

fun interface Callback<T> {
    fun execute(item: T, action: Action)
}

