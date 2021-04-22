package gerard.example.munchkinhelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.Cfg
import gerard.example.munchkinhelper.model.Player
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.activity.GAME_KEY
import gerard.example.munchkinhelper.adapters.GamePlayerAdapter
import gerard.example.munchkinhelper.core.views.CounterView
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.viewmodels.GameFragmentVM
import kotlinx.android.synthetic.main.game_fragment.*

class GameFragment() : Fragment(){

    var actuallyPlayer: Player? = null
    var viewmodel: GameFragmentVM? = null
    var game : Game? = null

    companion object{
        fun newInstance(game: Game?) : GameFragment{
            return GameFragment().apply {
                arguments = Bundle().apply{
                    putSerializable(GAME_KEY, game)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View = inflater.inflate(R.layout.game_fragment, container, false)
        game = arguments?.getSerializable(GAME_KEY) as Game?

        activity?.let {
            viewmodel = ViewModelProvider.AndroidViewModelFactory.getInstance(it.application).create(GameFragmentVM::class.java)
        }

        val txtViewPlayerName : TextView = v.findViewById(R.id.txtView_playerName)

        val recyclerView : RecyclerView = v.findViewById(R.id.recycler_view)
        var playerAdapter : GamePlayerAdapter? = null

        game?.let {
            // we know that context is not null
            playerAdapter = GamePlayerAdapter(context!!, it.players)
        }

        val observer = Observer<Player>{
            actuallyPlayer = it
            txtViewPlayerName.text = it.name
            power_counter?.attachValue(it.power)
            power_counter?.onChangeListener = CounterView.OnChangeListener{
                actuallyPlayer?.power = it
                if(Cfg.autoSave.value.get() == true) viewmodel?.updateGame(game)
                playerAdapter?.notifyDataSetChanged()
            }
            level_counter?.attachValue(it.lvl)
            level_counter?.onChangeListener = CounterView.OnChangeListener {
                actuallyPlayer?.lvl = it
                if(Cfg.autoSave.value.get() == true) viewmodel?.updateGame(game)
                playerAdapter?.notifyDataSetChanged()
            }
        }

        playerAdapter?.selectedPlayer?.observe(viewLifecycleOwner, observer)
        recyclerView.adapter = playerAdapter

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        skull_icon.setOnClickListener {
            // TODO DIALOG
        }
    }
}