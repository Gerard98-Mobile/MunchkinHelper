package com.example.munchkinhelper

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddingPlayersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_players)

        val playerAmount = this.intent.getIntExtra(KEY_EXTRA_PLAYER_AMOUNT,0);
        var count = 1;

        val playerNameEditText : EditText = findViewById(R.id.name_player_editText)
        val countPlayer : TextView = findViewById(R.id.count_player_textView)
        val addPlayer : Button = findViewById(R.id.add_player_button)
        val withPower = this.intent.getBooleanExtra(KEY_EXTRA_WITH_POWER, false)

        addPlayer.setOnClickListener {
            val playerName = playerNameEditText.text.toString()
            if(playerName.length > 3) {
                val player: Player
                if (withPower) {
                    player = Player(playerNameEditText.text.toString(), 1, 1)
                } else {
                    player = Player(playerNameEditText.text.toString(), 0, 1)
                }

                LocalBase.instance.players.add(player)
                Toast.makeText(this, R.string.player_added, Toast.LENGTH_SHORT).show()

                playerAmount?.let {
                    if (count >= it) {
                        // start new intent
                        val intent = Intent(this, GameActivity::class.java)
                        intent.putExtra(KEY_EXTRA_WITH_POWER, withPower)
                        startActivity(intent)
                    } else {
                        val newString = "Gracz nr " + ++count
                        playerNameEditText.setText("")
                        countPlayer.text = newString
                    }
                }
            }else{
                Toast.makeText(this, R.string.name_lenght_error, Toast.LENGTH_SHORT).show()
            }

        }

    }


}