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
import gerard.example.munchkinhelper.model.Player
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.activity.GAME_KEY
import gerard.example.munchkinhelper.adapters.GamePlayerAdapter
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.viewmodels.GameFragmentVM

class GameFragment() : Fragment(){

    var actuallyPlayer: Player? = null
    var viewmodel: GameFragmentVM? = null
    var game : Game? = null

    companion object{
        fun newInstance(game: Game) : GameFragment{
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
        val txtViewPower : TextView = v.findViewById(R.id.txtView_power)
        val txtViewLevel : TextView = v.findViewById(R.id.txtView_level)

        val recyclerView : RecyclerView = v.findViewById(R.id.recycler_view)
        var playerAdapter : GamePlayerAdapter? = null

        this.context?.let {
            recyclerView.layoutManager = LinearLayoutManager(it)

            game?.let {
                // we know that context is not null
                playerAdapter = GamePlayerAdapter(context!!, it.players)
            }

            val observer = Observer<Player>{
                actuallyPlayer = it
                txtViewPlayerName.text = it.name
                txtViewLevel.text = it.lvl.toString()
                txtViewPower.text = it.getAbsolutePower()
            }

            playerAdapter?.selectedPlayer?.observe(viewLifecycleOwner, observer)
            recyclerView.adapter = playerAdapter
        }

        val addPower : ImageButton = v.findViewById(R.id.imgButton_add_power)
        addPower.setOnClickListener {
            actuallyPlayer?.let{
                it.power += 1
                txtViewPower.text = it.getAbsolutePower().toString()
                playerAdapter?.notifyDataSetChanged()
            }
        }
        val reducePower : ImageButton = v.findViewById(R.id.imgButton_reduce_power)
        reducePower.setOnClickListener {
            actuallyPlayer?.let{
                it.power -= 1
                txtViewPower.text = it.getAbsolutePower()
                playerAdapter?.notifyDataSetChanged()
            }
        }
        val addLevel : ImageButton = v.findViewById(R.id.imgButton_add_level)
        addLevel.setOnClickListener {
            actuallyPlayer?.let{
                it.lvl += 1
                txtViewLevel.text = it.lvl.toString()
                txtViewPower.text = it.getAbsolutePower().toString()
                playerAdapter?.notifyDataSetChanged()
            }
        }
        val reduceLevel : ImageButton = v.findViewById(R.id.imgButton_reduce_level)
        reduceLevel.setOnClickListener {
            actuallyPlayer?.let{
                if(it.lvl - 1 > 0){
                    it.lvl -= 1
                    txtViewLevel.text = it.lvl.toString()
                    txtViewPower.text = it.getAbsolutePower()
                    playerAdapter?.notifyDataSetChanged()
                }
            }
        }

        return v
    }
}