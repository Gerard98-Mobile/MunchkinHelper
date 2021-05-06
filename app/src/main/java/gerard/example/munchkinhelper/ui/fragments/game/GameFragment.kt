package gerard.example.munchkinhelper.ui.fragments.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.Cfg
import gerard.example.munchkinhelper.model.Player
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.ui.activity.GAME_KEY
import gerard.example.munchkinhelper.core.views.CounterView
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.ui.dialogs.DeathDialog
import gerard.example.munchkinhelper.util.Sound
import gerard.example.munchkinhelper.util.SoundHelper
import kotlinx.android.synthetic.main.game_fragment.*

class GameFragment : Fragment(){

    var selectedPlayer: Player? = null
    var viewmodel: GameFragmentVM? = null
    var game : Game? = null

    var soundHelper: SoundHelper? = null

    companion object{
        fun newInstance(game: Game?) : GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply{
                    putSerializable(GAME_KEY, game)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.game_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { soundHelper = SoundHelper(it.applicationContext) }

        game = arguments?.getSerializable(GAME_KEY) as Game?

        activity?.let {
            viewmodel = ViewModelProvider.AndroidViewModelFactory.getInstance(it.application).create(
                GameFragmentVM::class.java)
        }

        var playerAdapter : GamePlayerAdapter? = null


        game?.let {
            playerAdapter = GamePlayerAdapter(view.context, it.players)
        }

        val observer = Observer<Player>{ player ->
            selectedPlayer = player
            txtView_playerName.text = player.name
            power_counter?.attachValue(player.getAbsolutePowerInt())
            power_counter?.onChangeListener = CounterView.OnChangeListener{ newValue, _ ->
                player.setPowerFromAbsoluteValue(newValue)
                viewmodel?.autosave(game)
                playerAdapter?.notifyDataSetChanged()
            }
            level_counter?.attachValue(player.lvl)
            level_counter?.onChangeListener = CounterView.OnChangeListener { newValue, lvlUp ->
                player.lvl = newValue
                power_counter?.attachValue(player.getAbsolutePowerInt())
                viewmodel?.autosave(game)
                soundHelper?.playLvlChangeSound(lvlUp)
                playerAdapter?.notifyDataSetChanged()
            }
        }

        playerAdapter?.selectedPlayer?.observe(viewLifecycleOwner, observer)
        recycler_view.adapter = playerAdapter

        skull_icon.setOnClickListener { v1 ->
            selectedPlayer?.let {
                DeathDialog(
                    v1.context,
                    it.name
                ) {
                    it.death()
                    soundHelper?.playSound(Sound.DEATH)
                    observer.onChanged(it)
                    playerAdapter?.notifyDataSetChanged()
                    viewmodel?.autosave(game)
                }.show()
            }

        }
    }
}