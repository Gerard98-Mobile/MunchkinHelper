package gerard.example.munchkinhelper.ui.activity.create

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.CfgTheme
import gerard.example.munchkinhelper.colorInt
import gerard.example.munchkinhelper.colorStateList
import gerard.example.munchkinhelper.model.Player
import gerard.example.munchkinhelper.databinding.ItemAddedPlayerBinding

class AddedPlayersAdapter(val context: Context, val players: MutableList<Player>) : RecyclerView.Adapter<AddedPlayersAdapter.PlayerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerHolder {
        val itemBinding = ItemAddedPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: PlayerHolder, position: Int) {
        holder.bind(players[position])
        holder.itemBinding.remove.imageTintList = CfgTheme.current.primaryColor.colorStateList(context)
        holder.itemBinding.name.setTextColor(CfgTheme.current.primaryColor.colorInt(context))
    }

    inner class PlayerHolder(val itemBinding: ItemAddedPlayerBinding) : RecyclerView.ViewHolder(itemBinding.root){
        fun bind(player: Player){
            itemBinding.name.text = player.name

            itemBinding.remove.setOnClickListener {
                players.remove(player)
                notifyItemRemoved(layoutPosition)
            }
        }
    }
}