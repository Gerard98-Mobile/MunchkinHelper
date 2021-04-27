package gerard.example.munchkinhelper.ui.fragments.fight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.model.Player
import gerard.example.munchkinhelper.ui.activity.GAME_KEY
import kotlinx.android.synthetic.main.fragment_select_fighters.*
import java.io.Serializable

class SelectFightersFragment : Fragment() {

    class FighterModelParcelable(val fighters: List<FighterModel>) : Serializable
    class FighterModel(val player: Player, var selected: Boolean)

    private lateinit var fightersParcelable : FighterModelParcelable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_select_fighters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fightersParcelable = arguments?.getSerializable(FIGHTERS_KEY) as FighterModelParcelable
        fighters_recycler_view.adapter = SelectFightersAdapter(view.context, fightersParcelable.fighters) { newPower ->
            val parent = parentFragment as? FightContainerFragment
            parent?.fightersPowerChanged(newPower)
        }
        swipe_to_right.setOnClickListener {
            val parent = parentFragment as? FightContainerFragment
            parent?.changeFragment(1)
        }
    }

    fun reset() {
        fightersParcelable.fighters.forEach { it.selected = false }
        fighters_recycler_view.adapter?.notifyDataSetChanged()
    }

    companion object{
        fun newInstance(fighters: FighterModelParcelable) : SelectFightersFragment {
            return SelectFightersFragment().apply {
                arguments = Bundle().apply{
                    putSerializable(FIGHTERS_KEY, fighters)
                }
            }
        }
    }
}