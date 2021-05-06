package gerard.example.munchkinhelper.ui.fragments.fight

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    var fighters: List<SelectFightersFragment.FighterModel>? = null

    val playersPowerChanged = MutableLiveData<Int>()
    val monstersPowerChanged = MutableLiveData<Int>()

    var playersPower = 0
        set(value){
            field = value
            playersPowerChanged.value = field
        }

    var monstersPower = 0
        set(value){
            field = value
            monstersPowerChanged.value = field
        }

    val reset = MutableLiveData<Boolean>()

    fun reset(){
        playersPower = 0
        monstersPower = 0
        reset.value = true
    }

}