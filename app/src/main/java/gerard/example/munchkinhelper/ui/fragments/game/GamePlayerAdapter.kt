package gerard.example.munchkinhelper.ui.fragments.game

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import gerard.example.munchkinhelper.*
import gerard.example.munchkinhelper.databinding.ItemPlayerBinding
import gerard.example.munchkinhelper.model.Player

class GamePlayerAdapter(val context: Context, val players: List<Player>) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    val selectedPlayer : MutableLiveData<Player> by lazy{
        MutableLiveData<Player>();
    }
    var selectedView: View? = null;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = ItemPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return players.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val playerHolder = holder as PlayerHolder
        playerHolder.bind(players[position])
    }


    private inner class PlayerHolder(val binding: ItemPlayerBinding) : RecyclerView.ViewHolder(binding.root){

        var player: Player? = null

        fun bind(player: Player) = binding.run{
            this@PlayerHolder.player = player
            txtViewPlayerNameItem.text = player.name
            txtViewPowerItem.text = player.getAbsolutePower()
            txtViewLevelItem.text = player.lvl.toString()
            imgViewLeader.visibility = if(player.isLeader) View.VISIBLE else View.INVISIBLE
            deathCount.isVisible = player.deaths > 0 && Cfg.showDeathCount.value.get() == true
            deathCount.text = context.getString(R.string.death_count, player.deaths)

            with(CfgTheme.current.primaryColor.colorInt(context)){
                txtViewPlayerNameItem.setTextColor(this)
                txtViewPowerItem.setTextColor(this)
                txtViewLevelItem.setTextColor(this)
            }

            if(selectedView != itemView){
                linearLayoutPlayerContainer.setCardBackgroundColor(CfgTheme.current.backgroundColor.colorInt(context))
                linearLayoutPlayerContainer.strokeColor = CfgTheme.current.backgroundColor.colorInt(context)
            }


            linearLayoutPlayerContainer.setOnClickListener {
                if(selectedView != it){
                    linearLayoutPlayerContainer.strokeColor = CfgTheme.current.primaryColor.colorInt(context)
                    (selectedView as? MaterialCardView)?.strokeColor = CfgTheme.current.backgroundColor.colorInt(context)
                    selectedView = linearLayoutPlayerContainer
                    selectedPlayer.value = player
                }

            }

        }

    }
}
