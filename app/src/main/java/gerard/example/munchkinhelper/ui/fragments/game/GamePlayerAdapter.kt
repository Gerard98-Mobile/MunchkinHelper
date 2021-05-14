package gerard.example.munchkinhelper.ui.fragments.game

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.MainActivity
import gerard.example.munchkinhelper.model.Player
import gerard.example.munchkinhelper.R
import kotlinx.android.synthetic.main.game_fragment.*
import kotlinx.android.synthetic.main.item_player.view.*

class GamePlayerAdapter(val context: Context, val players: List<Player>) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    val selectedPlayer : MutableLiveData<Player> by lazy{
        MutableLiveData<Player>();
    }
    var selectedView: View? = null;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlayerHolder(
            LayoutInflater.from(context).inflate(R.layout.item_player, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return players.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val playerHolder = holder as PlayerHolder
        playerHolder.bind(players[position])
    }


    private inner class PlayerHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var player: Player? = null

        fun bind(player: Player){
            this.player = player
            itemView.txtView_playerName_item.text = player.name
            itemView.txtView_power_item.text = player.getAbsolutePower()
            itemView.txtView_level_item.text = player.lvl.toString()
            itemView.imgView_leader.visibility = if(player.isLeader) View.VISIBLE else View.INVISIBLE

            itemView.linearLayout_player_container.setOnClickListener {
                if(selectedView != it){
                    itemView.linearLayout_player_container.setBackgroundResource(R.drawable.circle_border)
                    selectedView?.setBackgroundResource(0)
                    selectedView = itemView.linearLayout_player_container
                    selectedPlayer.value = player
                }

            }

        }

    }
}
