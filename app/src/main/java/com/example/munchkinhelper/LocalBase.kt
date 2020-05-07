package com.example.munchkinhelper

class LocalBase private constructor() {

    private object HOLDER{
        val INSTANCE = LocalBase()
    }

    val players : MutableList<Player> = mutableListOf()

    companion object{
        val instance: LocalBase by lazy{HOLDER.INSTANCE}
    }

    public fun addPlayer(player: Player){
        players + player
    }
}