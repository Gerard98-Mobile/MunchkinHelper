package gerard.example.munchkinhelper.ui.fragments.fight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import gerard.example.munchkinhelper.CfgTheme
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.colorInt
import gerard.example.munchkinhelper.core.BaseFragment
import gerard.example.munchkinhelper.core.views.EditTextWithTitle
import gerard.example.munchkinhelper.core.views.FightCounter
import kotlinx.android.synthetic.main.fight_fragment.*

class FightFragment : BaseFragment() {

    companion object{
        fun newInstance() : FightFragment {
            return FightFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fight_fragment, container, false)
    }

    private val model: SharedViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        players_counter.valueChangeListener = FightCounter.ValueChangeListener {
            model.playersPower += it
        }

        monsters_counter.valueChangeListener = FightCounter.ValueChangeListener {
            model.monstersPower += it
        }

        model.reset.observe(viewLifecycleOwner, {
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

    override fun applyThemeColors() {
        players_counter.applyTheme()
        monsters_counter.applyTheme()
        context?.let {
            vs_txtView.setTextColor(CfgTheme.current.primaryColor.colorInt(it))
        }
        players_count.applyTheme()
        monsters_count.applyTheme()

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
}