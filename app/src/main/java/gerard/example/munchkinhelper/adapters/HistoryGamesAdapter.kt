package gerard.example.munchkinhelper.adapters

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.activity.GAME_KEY
import gerard.example.munchkinhelper.activity.GameActivity
import gerard.example.munchkinhelper.model.Game
import java.text.SimpleDateFormat
import java.util.*

class HistoryGamesAdapter(val context: Context, val games: MutableList<Game>, val gameToDelete: MutableLiveData<Game>) : RecyclerView.Adapter<HistoryGamesAdapter.HistoryGameHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryGameHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_card_history, parent, false)
        return HistoryGameHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryGameHolder, position: Int) {
        holder.bind(games.get(position))
    }

    override fun getItemCount(): Int {
        return games.size
    }

    inner class HistoryGameHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val txtViewGamePlayers : TextView
        val txtViewGameDate : TextView
        val imgBtnDeleteGame : ImageButton
        val linearLayoutHistory : LinearLayout

        init{
            txtViewGamePlayers = itemView.findViewById(R.id.txtView_game_players)
            txtViewGameDate = itemView.findViewById(R.id.txtView_game_date)
            imgBtnDeleteGame = itemView.findViewById(R.id.imgButton_history_delete_game)
            linearLayoutHistory = itemView.findViewById(R.id.linearLayout_card_history)
        }

        @SuppressLint("SimpleDateFormat")
        fun bind(game : Game){
            var playersString : String = ""
            var first = true
            game.players.forEach {
                if (first) {
                    first = !first
                } else {
                    playersString += "\n"
                }
                playersString += it.name + " " + it.getAbsolutePower() + " p " + it.lvl + " lvl"
            }
            txtViewGamePlayers.setText(playersString)

            val dateFormat = SimpleDateFormat("dd-MM-yyyy")
            val date = dateFormat.format(Date(game.saveDate))
            txtViewGameDate.setText(date.toString())

            // listeners for open game
            linearLayoutHistory.setOnClickListener {
                val intent = Intent(context, GameActivity::class.java)
                intent.putExtra(GAME_KEY, game)
                context.startActivity(intent)
            }

            imgBtnDeleteGame.setOnClickListener{
                showDialog(game)
            }

        }

    }

    fun showDialog(game : Game){

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_custom)

        val title = dialog.findViewById(R.id.txtView_dialog_title) as TextView
        title.setText(context.getString(R.string.deleting_history_title))
        val body = dialog.findViewById(R.id.txtView_dialog_body) as TextView
        body.setText(context.getString(R.string.deleting_history_body))

        val yesBtn = dialog.findViewById(R.id.txtView_dialog_yes) as TextView
        val noBtn = dialog.findViewById(R.id.txtView_dialog_no) as TextView
        yesBtn.setOnClickListener {
            gameToDelete.value = game
            games.remove(game)
            notifyDataSetChanged()
            dialog.dismiss()
        }
        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

}