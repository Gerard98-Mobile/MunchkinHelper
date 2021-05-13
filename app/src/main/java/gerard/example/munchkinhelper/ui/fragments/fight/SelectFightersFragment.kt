package gerard.example.munchkinhelper.ui.fragments.fight

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.model.Player
import gerard.example.munchkinhelper.ui.activity.GAME_KEY
import kotlinx.android.synthetic.main.fragment_select_fighters.*
import java.io.Serializable

class SelectFightersFragment : Fragment() {

    class FighterModel(val player: Player, var selected: Boolean)

    private val model: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_select_fighters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.fighters?.let {
            fighters_recycler_view.adapter = SelectFightersAdapter(view.context, it) { changedPower ->
                model.playersPower += changedPower
            }
        }

        swipe_to_right.setOnClickListener {
            val parent = parentFragment as? FightContainerFragment
            parent?.changeFragment(1)
        }

        model.reset.observe(viewLifecycleOwner, {
            reset()
        })
    }

    fun reset() {
        model.fighters?.forEach { it.selected = false }
        fighters_recycler_view.adapter?.notifyDataSetChanged()
    }

    companion object{
        fun newInstance() : SelectFightersFragment {
            return SelectFightersFragment()
        }
    }
}