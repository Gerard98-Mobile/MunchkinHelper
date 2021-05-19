package gerard.example.munchkinhelper.ui.fragments.fight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import gerard.example.munchkinhelper.CfgTheme
import gerard.example.munchkinhelper.colorInt
import gerard.example.munchkinhelper.core.BaseFragment
import gerard.example.munchkinhelper.core.views.EditTextWithTitle
import gerard.example.munchkinhelper.core.views.FightCounter
import gerard.example.munchkinhelper.databinding.FightFragmentBinding

class FightFragment : BaseFragment<FightFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FightFragmentBinding
            = FightFragmentBinding::inflate

    private val model: SharedViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.playersCounter.valueChangeListener = FightCounter.ValueChangeListener {
            model.playersPower += it
        }

        binding.monstersCounter.valueChangeListener = FightCounter.ValueChangeListener {
            model.monstersPower += it
        }

        model.reset.observe(viewLifecycleOwner, {
            binding.playersCount.setCount(0)
            binding.monstersCount.setCount(0)
        })

        model.playersPowerChanged.observe(viewLifecycleOwner, {
            binding.playersCount.setCount(it)
            setColorsForEditTexts()
        })

        model.monstersPowerChanged.observe(viewLifecycleOwner, {
            binding.monstersCount.setCount(it)
            setColorsForEditTexts()
        })
    }

    override fun applyThemeColors() {
        binding.playersCounter.applyTheme()
        binding.monstersCounter.applyTheme()
        context?.let {
            binding.vsTxtView.setTextColor(CfgTheme.current.primaryColor.colorInt(it))
        }
        binding.playersCount.applyTheme()
        binding.monstersCount.applyTheme()

    }

    private fun setColorsForEditTexts() = binding.run {
        val players = playersCount.getCount()
        val monsters = monstersCount.getCount()
        if(players == monsters){
            playersCount.changeState(EditTextWithTitle.STATE.DRAW)
            monstersCount.changeState(EditTextWithTitle.STATE.DRAW)
        }else if(players > monsters){
            playersCount.changeState(EditTextWithTitle.STATE.WINNER)
            monstersCount.changeState(EditTextWithTitle.STATE.LOSSER)
        }
        else{
            playersCount.changeState(EditTextWithTitle.STATE.LOSSER)
            monstersCount.changeState(EditTextWithTitle.STATE.WINNER)
        }
    }

    companion object{
        fun newInstance() : FightFragment {
            return FightFragment()
        }
    }
}