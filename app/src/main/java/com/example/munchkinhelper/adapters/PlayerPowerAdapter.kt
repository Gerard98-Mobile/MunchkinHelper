package com.example.munchkinhelper.adapters

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.munchkinhelper.LocalBase
import com.example.munchkinhelper.MainActivity
import com.example.munchkinhelper.Player
import com.example.munchkinhelper.R

class PlayerPowerAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    val players : List<Player> = LocalBase.instance.players


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            0 -> return PlayerHolderPower(LayoutInflater.from(context).inflate(R.layout.one_player_with_power_and_header,parent,false))
            1 -> return PlayerHolderPower(LayoutInflater.from(context).inflate(R.layout.one_player_with_power,parent,false))
            else -> return PlayerHolderPower(LayoutInflater.from(context).inflate(R.layout.one_player_with_power,parent,false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        when(position){
            0 -> return 0
            else -> return 1
        }
    }

    override fun getItemCount(): Int {
        return players.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val playerHolder = holder as PlayerHolderPower
        playerHolder.bind(players.get(position))
    }


    private class PlayerHolderPower(itemView: View) : RecyclerView.ViewHolder(itemView){

        var power: TextView? = null
        var player: Player? = null

        val plusButton: ImageButton
        val minusButton: ImageButton
        val level : TextView

        var plusButtonPower: ImageButton? = null
        var minusButtonPower: ImageButton? = null

        init {
            plusButton = itemView.findViewById(R.id.plusButton)
            minusButton = itemView.findViewById(R.id.minusButton)
            level = itemView.findViewById(R.id.level)


            plusButtonPower = itemView.findViewById(R.id.plusButtonPower)
            minusButtonPower = itemView.findViewById(R.id.minusButtonPower)
            power = itemView.findViewById(R.id.power)

            plusButtonPower?.setOnClickListener{
                player?.let {
                    it.power++
                    power?.text = it.power.toString()
                }
            }

            minusButtonPower?.setOnClickListener{
                player?.let {
                    it.power--
                    power?.text = it.power.toString()
                }
            }


            plusButton.setOnClickListener {
                var actuallyLevel = level.text.toString().toInt()

                player?.let {
                    it.lvl++
                    level.text = it.lvl.toString();

                    if(it.lvl < 10){
                        it.power++
                        power?.text = it.power.toString()

                    }
                    else{
                        end(it.name)
                    }
                }
                ++actuallyLevel
            }

            minusButton.setOnClickListener {
                player?.let {
                    if(it.lvl > 1){
                        it.lvl--
                        level.text = it.lvl.toString()
                        it.power--
                        power?.text = it.power.toString()

                    }
                }
            }

        }

        fun bind(player: Player){
            this.player = player

            val playerNameTextView: TextView = itemView.findViewById(R.id.player_name)
            playerNameTextView.text = player.name

            level.text = player.lvl.toString()
            power?.text = player.power.toString()
        }

        fun end(playerName: String){
            val alertDialog: AlertDialog? = itemView?.let {
                val builder = AlertDialog.Builder(it.context)
                val message = itemView.resources.getString(R.string.winner, playerName)
                builder.apply {
                    setTitle(R.string.end)
                    setMessage(message)
                    setNeutralButton(R.string.stay, DialogInterface.OnClickListener { dialog, id -> })
                    setPositiveButton(R.string.reset, DialogInterface.OnClickListener { dialog, id ->
                        val newIntent = Intent(it.context, MainActivity::class.java)
                        it.context.startActivity(newIntent)
                    })
                }
                builder.create()
            }
            alertDialog?.show()
        }

    }
}
