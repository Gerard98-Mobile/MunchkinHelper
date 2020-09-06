package gerard.example.munchkinhelper.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.model.Player
import gerard.example.munchkinhelper.R

public class AddedPlayersAdapter(val context: Context, val players: MutableList<Player>) : RecyclerView.Adapter<AddedPlayersAdapter.PlayerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerHolder {
        return PlayerHolder(
            LayoutInflater.from(context).inflate(R.layout.item_added_player, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: PlayerHolder, position: Int) {
        holder.bind(players.get(position))
    }

    inner class PlayerHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val txtViewName : TextView
        val imgBtnDelete : ImageButton

        init{
            txtViewName = itemView.findViewById(R.id.txtView_added_playerName)
            imgBtnDelete = itemView.findViewById(R.id.imgButton_delete_player)
        }

        fun bind(player: Player){
            txtViewName.text = player.name

            imgBtnDelete.setOnClickListener {
                players.remove(player)
                notifyDataSetChanged()
            }
        }
    }


}