package gerard.example.munchkinhelper.ui.fragments.fight

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.core.views.EditTextWithTitle
import gerard.example.munchkinhelper.core.views.FightCounter
import kotlinx.android.synthetic.main.fight_fragment2.*

class FightFragment : Fragment() {

    companion object{
        fun newInstance() : FightFragment {
            return FightFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fight_fragment2, container, false)
    }

    var playersPower = 0
    var playerAddedPower = 0
    var monsterAddedPower = 0

    val fullPlayersPower = { playersPower + playerAddedPower}



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        players_count.setOnChangeListener {
            setColorsForEditTexts()
        }
        monsters_count.setOnChangeListener {
            setColorsForEditTexts()
        }

        players_counter.valueChangeListener = FightCounter.ValueChangeListener {
            playerAddedPower = it
            players_count?.setCount(fullPlayersPower.invoke())
        }

        monsters_counter.valueChangeListener = FightCounter.ValueChangeListener {
            monsterAddedPower = it
            monsters_count?.setCount(monsterAddedPower)
        }

        players_counter.addedPower = playerAddedPower
        monsters_counter.addedPower = monsterAddedPower
        players_count.setCount(playersPower)
    }

    private fun setColorsForEditTexts() {
        val players = players_count.getCount()
        val monsters = monsters_count.getCount()
        if(players == monsters){
            players_count.changeState(EditTextWithTitle.STATE.DRAW)
            monsters_count.changeState(EditTextWithTitle.STATE.DRAW)
        }else if(players > monsters){
            players_count.changeState(EditTextWithTitle.STATE.WINNER)
            monsters_count.changeState(EditTextWithTitle.STATE.LOSSER)
        }
        else{
            players_count.changeState(EditTextWithTitle.STATE.LOSSER)
            monsters_count.changeState(EditTextWithTitle.STATE.WINNER)
        }
    }

    fun reset() {
        playersPower = 0
        playerAddedPower = 0
        monsterAddedPower = 0
        players_counter.addedPower = playerAddedPower
        monsters_counter.addedPower = monsterAddedPower
        players_count.setCount(fullPlayersPower.invoke())
        monsters_count.setCount(0)
    }

    fun fightersPowerChanged(newPlayerPowers: Int) {
        playersPower = newPlayerPowers
        players_count?.setCount(fullPlayersPower.invoke())
        Log.e("FightFramgnet","After Change players: " + playersPower + " addedPower: " + playerAddedPower)
    }
}