package gerard.example.munchkinhelper.model

import java.io.Serializable

class Player(
    val name: String,
    var power: Int,
    var lvl: Int) : Serializable{

    /*
        Function return a value of power + lvl in String
     */
    fun getAbsolutePowerInt(): Int{
        return power + lvl
    }

    fun getAbsolutePower(): String{
        return (power + lvl).toString()
    }

    override fun toString(): String {
        return name
    }
}