package gerard.example.munchkinhelper.model

import java.io.Serializable

class Player(
    val name: String,
    var power: Int,
    var lvl: Int,
    var isLeader: Boolean = false) : Serializable{

    /*
        Function return a value of power + lvl in String
     */
    fun getAbsolutePowerInt(): Int{
        return power + lvl
    }

    fun setPowerFromAbsoluteValue(absolutePower: Int){
        this.power = absolutePower - lvl
    }

    fun getAbsolutePower(): String{
        return (power + lvl).toString()
    }

    override fun toString(): String {
        return name
    }

    fun death() {
        lvl = 1
        power = 0
    }
}