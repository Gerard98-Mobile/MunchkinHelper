package gerard.example.munchkinhelper.ui.activity.load

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.ui.activity.GAME_KEY
import gerard.example.munchkinhelper.ui.activity.GameActivity
import gerard.example.munchkinhelper.util.DateUtil
import kotlinx.android.synthetic.main.item_card_history.view.*
import java.util.*

class HistoryGameAdapter(val context: Context, val games: MutableList<Game>, val callback: Callback) : RecyclerView.Adapter<HistoryGameAdapter.HistoryGameHolder>()  {

    fun interface Callback{
        fun delete(game: Game)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryGameHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_card_history, parent, false)
        return HistoryGameHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryGameHolder, position: Int) {
        holder.bind(games[position])

        // listeners for open game
        holder.itemView.root.setOnClickListener {
            val intent = Intent(context, GameActivity::class.java)
            intent.putExtra(GAME_KEY, games[position])
            context.startActivity(intent)
        }

        holder.itemView.more_icon_click_area.setOnClickListener{
            MoreOptionsPopUp(listOf(
                Option.DELETE_GAME
            )) { option ->
                when(option){
                    Option.DELETE_GAME -> {
                        callback.delete(games[holder.layoutPosition])
                        games.removeAt(holder.layoutPosition)
                        notifyItemRemoved(holder.layoutPosition)
                    }
                }
            }.show(it)
        }
    }

    override fun getItemCount(): Int {
        return games.size
    }

    class HistoryGameHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(game : Game){
            itemView.players.text = game.players.map { "${it.name} ${it.lvl} lvl" }.joinToString(",")
            val date = DateUtil.dateDDMMYYYYdash().format(Date(game.saveDate))
            itemView.game_date.text = date.toString()
        }

    }
}