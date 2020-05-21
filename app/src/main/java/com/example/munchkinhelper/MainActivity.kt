package com.example.munchkinhelper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast

val KEY_EXTRA_PLAYER_AMOUNT = "key_extra_player_amount"
val KEY_EXTRA_WITH_POWER = "key_extra_with_power"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startGame: Button = findViewById(R.id.start_game)
        val playerAmount: TextView = findViewById(R.id.players_amount)
        val switch: Switch = findViewById(R.id.with_power_switch)

        val backupPlayers = LocalBase.instance.players
        if(!backupPlayers.isEmpty()){
            val backup: Button = findViewById(R.id.backup_button)

            backup.setOnClickListener {
                val intent = Intent(this, GameActivity::class.java)
                val withPower = switch.isChecked
                intent.putExtra(KEY_EXTRA_WITH_POWER, withPower)
                startActivity(intent)
            }

            backup.visibility = View.VISIBLE
        }

        startGame.setOnClickListener {
            try{
            val playerAmountInt = playerAmount.text.toString().toInt()
            if(playerAmountInt > 1 && playerAmountInt < 10){
                /*
                val intent = Intent(this, GameActivity::class.java)
                intent.putExtra(KEY_EXTRA_PLAYER_AMOUNT, playerAmountInt)
                val withPower = switch.isChecked
                intent.putExtra(KEY_EXTRA_WITH_POWER, withPower)
                startActivity(intent)*/

                LocalBase.instance.players.clear()
                val intent = Intent(this, AddingPlayersActivity::class.java)
                intent.putExtra(KEY_EXTRA_PLAYER_AMOUNT, playerAmountInt)
                val withPower = switch.isChecked
                intent.putExtra(KEY_EXTRA_WITH_POWER, withPower)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, R.string.invalid_amount_players, Toast.LENGTH_LONG).show()
            }
            }catch (ex: NumberFormatException){
                Toast.makeText(this, R.string.invalid_amount_players, Toast.LENGTH_LONG).show()
            }

        }

    }
}