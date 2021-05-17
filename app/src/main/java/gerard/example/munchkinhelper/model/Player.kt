package gerard.example.munchkinhelper.model

import gerard.example.munchkinhelper.ui.activity.create.START_LVL
import gerard.example.munchkinhelper.ui.activity.create.START_POWER
import java.io.Serializable

class Player(
    val name: String,
    var power: Int,
    var lvl: Int,
    var deaths: Int = 0,
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
        deaths++
        lvl = START_LVL
        power = START_POWER
    }

    fun reset(){
        lvl = START_LVL
        power = START_POWER
        deaths = 0
    }
}