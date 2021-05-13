package gerard.example.munchkinhelper

import java.lang.Exception

class NotInitializedException(text: String) : Exception(text)

fun now() = System.currentTimeMillis()