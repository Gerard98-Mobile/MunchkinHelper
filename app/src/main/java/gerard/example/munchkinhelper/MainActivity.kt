package gerard.example.munchkinhelper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.se.omapi.Session
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import gerard.example.munchkinhelper.activity.AddingPlayersActivity
import gerard.example.munchkinhelper.activity.GameActivity
import gerard.example.munchkinhelper.activity.LoadGameActivity
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loadGame: Button = findViewById(R.id.btn_loadGame)
        loadGame.setOnClickListener {
            val intent = Intent(this, LoadGameActivity::class.java)
            startActivity(intent)
        }

        val startGame: Button = findViewById(R.id.start_game)
        startGame.setOnClickListener {
            val intent = Intent(this, AddingPlayersActivity::class.java)
            startActivity(intent)
        }
    }
}