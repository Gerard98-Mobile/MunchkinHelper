package com.example.munchkinhelper

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddingPlayers : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_players)

        val playerAmount = this.intent.getIntExtra(KEY_EXTRA_PLAYER_AMOUNT,0);
        var count = 1;

        val playerName : EditText = findViewById(R.id.name_player_editText)
        val countPlayer : TextView = findViewById(R.id.count_player_textView)
        val addPlayer : Button = findViewById(R.id.add_player_button)

        addPlayer.setOnClickListener {
            val player = Player(playerName.text.toString(),1,1)
            LocalBase.instance.players.add(player)
            val newString = "Gracz nr " + ++count
            playerName.setText("")
            countPlayer.text = newString
            Toast.makeText(this,R.string.player_added,Toast.LENGTH_SHORT).show()

            playerAmount?.let {
                if(count > it){
                    // start new intent
                    val intent = Intent(this, GameActivity::class.java)
                    val withPower = this.intent.getBooleanExtra(KEY_EXTRA_WITH_POWER, false)
                    intent.putExtra(KEY_EXTRA_WITH_POWER, withPower)
                    startActivity(intent)
                }
            }

        }

    }


}