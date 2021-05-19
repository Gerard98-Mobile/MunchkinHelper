package gerard.example.munchkinhelper.ui.fragments.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import gerard.example.munchkinhelper.CfgTheme
import gerard.example.munchkinhelper.model.Player
import gerard.example.munchkinhelper.colorInt
import gerard.example.munchkinhelper.colorStateList
import gerard.example.munchkinhelper.core.BaseFragment
import gerard.example.munchkinhelper.ui.activity.GAME_KEY
import gerard.example.munchkinhelper.core.views.CounterView
import gerard.example.munchkinhelper.databinding.GameFragmentBinding
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.ui.dialogs.DeathDialog
import gerard.example.munchkinhelper.util.Sound
import gerard.example.munchkinhelper.util.SoundHelper

class GameFragment : BaseFragment<GameFragmentBinding>(){

    var selectedPlayer: Player? = null
    var viewmodel: GameFragmentVM? = null
    var game : Game? = null

    var soundHelper: SoundHelper? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> GameFragmentBinding
            = GameFragmentBinding::inflate

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
            selectLeaderFromStart(it.players)
            playerAdapter = GamePlayerAdapter(view.context, it.players)
        }

        val observer = Observer<Player>{ player ->
            binding.run {
                selectedPlayer = player
                txtViewPlayerName.text = player.name
                powerCounter.attachValue(player.getAbsolutePowerInt())
                powerCounter.onChangeListener = CounterView.OnChangeListener{ newValue, _ ->
                    player.setPowerFromAbsoluteValue(newValue)
                    game?.let { selectLeader(it.players) }
                    viewmodel?.autosave(game)
                    playerAdapter?.notifyDataSetChanged()
                }
                levelCounter.attachValue(player.lvl)
                levelCounter.onChangeListener = CounterView.OnChangeListener { newValue, lvlUp ->
                    player.lvl = newValue
                    powerCounter.attachValue(player.getAbsolutePowerInt())
                    game?.let { selectLeader(it.players) }
                    viewmodel?.autosave(game)
                    soundHelper?.playLvlChangeSound(lvlUp)
                    playerAdapter?.notifyDataSetChanged()
                }
            }
        }

        playerAdapter?.selectedPlayer?.observe(viewLifecycleOwner, observer)
        binding.recyclerView.adapter = playerAdapter

        binding.skullIcon.setOnClickListener { v1 ->
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

    override fun applyThemeColors() : Unit = binding.run {
        context?.let {
            skullIcon.imageTintList = CfgTheme.current.primaryColor.colorStateList(it)
            with(CfgTheme.current.primaryColor.colorInt(it)){
                power.setTextColor(this)
                level.setTextColor(this)
                separator.setBackgroundColor(this)
                txtViewPlayerName.setTextColor(this)
            }
            powerCounter.applyTheme()
            levelCounter.applyTheme()
        }
    }

    var leader: Player? = null

    fun selectLeaderFromStart(players: List<Player>){
        players.forEach {
            if(it.isLeader) leader = it
        }
    }

    fun selectLeader(players: List<Player>){
        val newLeader = players.maxByOrNull { it.lvl }

        players.forEach { a -> if(newLeader?.lvl == a.lvl && newLeader != a){
            leader?.isLeader = false
            leader = null
            return
        }}

        if(newLeader == leader) return

        newLeader?.isLeader = true
        leader?.isLeader = false
        leader = newLeader
    }

    companion object{
        fun newInstance(game: Game?) : GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply{
                    putSerializable(GAME_KEY, game)
                }
            }
        }
    }

}