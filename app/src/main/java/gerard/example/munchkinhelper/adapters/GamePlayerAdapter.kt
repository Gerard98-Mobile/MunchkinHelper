package gerard.example.munchkinhelper.adapters

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.MainActivity
import gerard.example.munchkinhelper.model.Player
import gerard.example.munchkinhelper.R

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
        playerHolder.bind(players.get(position))
    }


    private inner class PlayerHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val txtViewPower: TextView
        val txtViewLevel: TextView
        val txtViewPlayerName : TextView
        val playerContainer: LinearLayout

        var player: Player? = null

        init {
            txtViewPlayerName = itemView.findViewById(R.id.txtView_playerName_item)
            txtViewLevel = itemView.findViewById(R.id.txtView_level_item)
            txtViewPower = itemView.findViewById(R.id.txtView_power_item)
            playerContainer = itemView.findViewById(R.id.linearLayout_player_container)
        }

        fun bind(player: Player){
            this.player = player
            txtViewPlayerName.text = player.name
            txtViewPower.text = player.getAbsolutePower()
            txtViewLevel.text = player.lvl.toString()



            playerContainer.setOnClickListener {
                if(selectedView != it){
                    playerContainer.setBackgroundResource(R.drawable.circle_border)
                    val valueInPixels = context.resources.getDimensionPixelOffset(R.dimen.item_padding)
                    playerContainer.setPadding(valueInPixels,valueInPixels,valueInPixels,valueInPixels)
                    selectedView?.setBackgroundResource(0)
                    selectedView = playerContainer
                    selectedPlayer.value = player
                }

            }

        }

        fun end(playerName: String){
            val alertDialog: AlertDialog? = itemView.let {
                val builder = AlertDialog.Builder(it.context)
                val message = itemView.resources.getString(R.string.winner, playerName)
                builder.apply {
                    setTitle(R.string.end)
                    setMessage(message)
                    setNeutralButton(R.string.stay) { _, _ -> }
                    setPositiveButton(R.string.reset) { _, _ ->
                        val newIntent = Intent(it.context, MainActivity::class.java)
                        it.context.startActivity(newIntent)
                    }
                }
                builder.create()
            }
            alertDialog?.show()
        }

    }
}
