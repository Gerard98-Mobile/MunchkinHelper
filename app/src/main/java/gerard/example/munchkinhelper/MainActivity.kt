package gerard.example.munchkinhelper

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.graphics.toColor
import androidx.core.view.isVisible
import gerard.example.munchkinhelper.core.BaseActivity
import gerard.example.munchkinhelper.core.views.RoundedButton
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.model.Player
import gerard.example.munchkinhelper.ui.activity.GAME_KEY
import gerard.example.munchkinhelper.ui.activity.GameActivity
import gerard.example.munchkinhelper.ui.activity.create.AddingPlayersActivity
import gerard.example.munchkinhelper.ui.activity.load.LoadGameActivity
import gerard.example.munchkinhelper.util.NavigationHelper
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(true) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(BuildConfig.DEBUG && Cfg.autoOpen){
            val intent = Intent(this, GameActivity::class.java)
            val game = Game(0, listOf(Player("Gerard",2,4), Player("Braciak",5,3)))
            intent.putExtra(GAME_KEY, game)
            startActivity(intent)
        }

        Cfg.init(this)
        val restoredGame = Cfg.lastGame.get()
        if(restoredGame != null) {
            load_last_game.visibility = View.VISIBLE
            load_last_game.setOnClickListener {
                NavigationHelper.startActivity(this, LoadGameActivity::class.java, restoredGame)
            }
        }

        val loadGame: RoundedButton = findViewById(R.id.btn_loadGame)
        loadGame.setOnClickListener {
            NavigationHelper.startActivity(this, LoadGameActivity::class.java)
        }

        val startGame: RoundedButton = findViewById(R.id.start_game)
        startGame.setOnClickListener {
            NavigationHelper.startActivity(this, AddingPlayersActivity::class.java)
        }
    }

    override fun applyThemeColors() {
        root.setBackgroundColor(CfgTheme.current.backgroundColor.colorInt(this))
        logo.isVisible = CfgTheme.current is DefaultTheme
        start_game.applyTheme()
        btn_loadGame.applyTheme()
        load_last_game.applyTheme()
    }

}