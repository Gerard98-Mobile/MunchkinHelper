package gerard.example.munchkinhelper.ui.activity.create

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.Cfg
import gerard.example.munchkinhelper.model.Player
import gerard.example.munchkinhelper.R
import kotlinx.android.synthetic.main.item_added_player.view.*

class AddedPlayersAdapter(val context: Context, val players: MutableList<Player>) : RecyclerView.Adapter<AddedPlayersAdapter.PlayerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerHolder {
        return PlayerHolder(
            LayoutInflater.from(context).inflate(if (Cfg.darkMode.value.get() == false) R.layout.item_added_player else R.layout.item_added_player_dark, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: PlayerHolder, position: Int) {
        holder.bind(players[position])
    }

    inner class PlayerHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(player: Player){
            itemView.name.text = player.name

            itemView.remove.setOnClickListener {
                players.remove(player)
                notifyItemRemoved(layoutPosition)
            }
        }
    }
}