package gerard.example.munchkinhelper.ui.fragments.fight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import gerard.example.munchkinhelper.CfgTheme
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.colorStateList
import gerard.example.munchkinhelper.core.BaseFragment
import gerard.example.munchkinhelper.databinding.FragmentSelectFightersBinding
import gerard.example.munchkinhelper.model.Player

class SelectFightersFragment : BaseFragment<FragmentSelectFightersBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSelectFightersBinding
            = FragmentSelectFightersBinding::inflate

    class FighterModel(val player: Player, var selected: Boolean)

    private val model: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.fighters?.let {
            binding.fightersRecyclerView.adapter = SelectFightersAdapter(view.context, it) { changedPower ->
                model.playersPower += changedPower
            }
        }

        binding.swipeToRight.setOnClickListener {
            val parent = parentFragment as? FightContainerFragment
            parent?.changeFragment(1)
        }

        model.reset.observe(viewLifecycleOwner, {
            reset()
        })
    }

    override fun applyThemeColors(): Unit = binding.run {
        context?.let {
            swipeToRight.backgroundTintList = CfgTheme.current.primaryColor.colorStateList(it)
            fightersRecyclerView.adapter?.notifyDataSetChanged()
            swipeToRight.imageTintList = CfgTheme.current.backgroundColor.colorStateList(it)
        }
    }

    fun reset() {
        model.fighters?.forEach { it.selected = false }
        binding.fightersRecyclerView.adapter?.notifyDataSetChanged()
    }

    companion object{
        fun newInstance() : SelectFightersFragment {
            return SelectFightersFragment()
        }
    }
}