package gerard.example.munchkinhelper.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import gerard.example.munchkinhelper.model.Player
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.core.views.EditTextWithTitle
import gerard.example.munchkinhelper.ui.activity.GAME_KEY
import gerard.example.munchkinhelper.model.Game
import kotlinx.android.synthetic.main.fight_fragment2.*

class FightFragment2 : Fragment() {

    private var playerPower = 1
    private var monsterPower = 1
    private var player : Player? = null
    private var assistant : Player? = null

    companion object{
        fun newInstance(game: Game?) : FightFragment2{
            return FightFragment2().apply {
                arguments = Bundle().apply{
                    putSerializable(GAME_KEY, game)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fight_fragment2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        players_count.setOnChangeListener { setColorsForEditTexts() }
        monsters_count.setOnChangeListener { setColorsForEditTexts() }
        players_counter.editText = players_count
        monsters_counter.editText = monsters_count
    }

    private fun setColorsForEditTexts() {
        val players = players_count.getCount() ?: 0
        val monsters = monsters_count.getCount() ?: 0
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
}