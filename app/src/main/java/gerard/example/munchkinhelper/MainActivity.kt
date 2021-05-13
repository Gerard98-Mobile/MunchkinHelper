package gerard.example.munchkinhelper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import gerard.example.munchkinhelper.core.views.RoundedButton
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.model.Player
import gerard.example.munchkinhelper.ui.activity.GAME_KEY
import gerard.example.munchkinhelper.ui.activity.GameActivity
import gerard.example.munchkinhelper.ui.activity.create.AddingPlayersActivity
import gerard.example.munchkinhelper.ui.activity.load.LoadGameActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(BuildConfig.DEBUG && Cfg.autoOpen){
            val intent = Intent(this, GameActivity::class.java)
            val game = Game(0, listOf(Player("Gerard",2,4), Player("Braciak",5,3)))
            intent.putExtra(GAME_KEY, game)
            startActivity(intent)
        }

        val loadGame: RoundedButton = findViewById(R.id.btn_loadGame)
        loadGame.setOnClickListener {
            val intent = Intent(this, LoadGameActivity::class.java)
            startActivity(intent)
        }

        val startGame: RoundedButton = findViewById(R.id.start_game)
        startGame.setOnClickListener {
            val intent = Intent(this, AddingPlayersActivity::class.java)
            startActivity(intent)
        }
    }
}