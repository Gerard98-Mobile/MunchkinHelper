package gerard.example.munchkinhelper.ui.fragments.fight

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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

    private val model: SharedViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        players_counter.valueChangeListener = FightCounter.ValueChangeListener {
            model.playersPower += it
//            players_count?.setCount(model.playersPower)
        }

        monsters_counter.valueChangeListener = FightCounter.ValueChangeListener {
            model.monstersPower += it
        }

//        players_counter.addedPower = playerAddedPower
//        monsters_counter.addedPower = monsterAddedPower
//        players_count.setCount(playersPower)

        model.reset.observe(viewLifecycleOwner, {
            Log.e("Reset","FightFragment")
            players_count?.setCount(0)
            monsters_count?.setCount(0)
        })

        model.playersPowerChanged.observe(viewLifecycleOwner, {
            players_count?.setCount(it)
            setColorsForEditTexts()
        })

        model.monstersPowerChanged.observe(viewLifecycleOwner, {
            monsters_count?.setCount(it)
            setColorsForEditTexts()
        })
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

//    fun reset() {
//        playersPower = 0
//        playerAddedPower = 0
//        monsterAddedPower = 0
//        players_counter.addedPower = playerAddedPower
//        monsters_counter.addedPower = monsterAddedPower
//        players_count.setCount(fullPlayersPower.invoke())
//        monsters_count.setCount(0)
//    }
//
//    fun fightersPowerChanged(newPlayerPowers: Int) {
//        playersPower = newPlayerPowers
//        players_count?.setCount(fullPlayersPower.invoke())
//        Log.e("FightFramgnet","After Change players: " + playersPower + " addedPower: " + playerAddedPower)
//    }
}