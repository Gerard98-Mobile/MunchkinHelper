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

class PlayerAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val players : List<Player> = LocalBase.instance.players

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            1 -> return PlayerHolder(LayoutInflater.from(context).inflate(R.layout.one_player,parent,false))
            0 -> return PlayerHolder(LayoutInflater.from(context).inflate(R.layout.one_player_with_header,parent,false))
            else -> return PlayerHolder(LayoutInflater.from(context).inflate(R.layout.one_player,parent,false))
        }
    }

    override fun getItemCount(): Int {
        return players.count()
    }

    override fun getItemViewType(position: Int): Int {
        when(position){
            0 -> return 0
            else -> return 1
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val playerHolder = holder as PlayerHolder
        playerHolder.bind(players.get(position))
    }




    private class PlayerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var power: TextView? = null
        var player: Player? = null

        val plusButton: ImageButton
        val minusButton: ImageButton
        val level : TextView

        init {
            plusButton = itemView.findViewById(R.id.plusButton)
            minusButton = itemView.findViewById(R.id.minusButton)
            level = itemView.findViewById(R.id.level)

            plusButton.setOnClickListener {
                player?.let {
                    it.lvl++
                    level.text = it.lvl.toString();

                    if(it.lvl >= 10){
                        end(it.name)
                    }
                }
            }

            minusButton.setOnClickListener {
                player?.let {
                    if(it.lvl > 1){
                        it.lvl--
                        level.text = it.lvl.toString()
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

