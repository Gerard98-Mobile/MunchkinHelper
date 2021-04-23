package gerard.example.munchkinhelper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import gerard.example.munchkinhelper.ui.activity.create.AddingPlayersActivity
import gerard.example.munchkinhelper.ui.activity.load.LoadGameActivity


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