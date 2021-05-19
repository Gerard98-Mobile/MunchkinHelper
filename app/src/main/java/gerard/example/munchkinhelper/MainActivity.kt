package gerard.example.munchkinhelper

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import gerard.example.munchkinhelper.core.BaseActivity
import gerard.example.munchkinhelper.core.views.RoundedButton
import gerard.example.munchkinhelper.databinding.ActivityMainBinding
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.model.Player
import gerard.example.munchkinhelper.ui.activity.GAME_KEY
import gerard.example.munchkinhelper.ui.activity.GameActivity
import gerard.example.munchkinhelper.ui.activity.create.AddingPlayersActivity
import gerard.example.munchkinhelper.ui.activity.load.LoadGameActivity
import gerard.example.munchkinhelper.util.NavigationHelper

class MainActivity : BaseActivity(true) {

    private lateinit var binding: ActivityMainBinding
    var restoredGame: Game? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if(BuildConfig.DEBUG && Cfg.autoOpen){
            val intent = Intent(this, GameActivity::class.java)
            val game = Game(0, listOf(Player("Gerard",2,4), Player("Braciak",5,3)))
            intent.putExtra(GAME_KEY, game)
            startActivity(intent)
        }

        Cfg.init(this)
        restoredGame = Cfg.lastGame.get()
        if(restoredGame != null) {
            binding.loadLastGame.visibility = View.VISIBLE
            binding.loadLastGame.setOnClickListener {
                restoredGame?.let {
                    NavigationHelper.startActivity(this, GameActivity::class.java, it)
                }
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

    override fun onResume() {
        super.onResume()
        restoredGame = Cfg.lastGame.get()
    }

    override fun applyThemeColors() {
        binding.root.setBackgroundColor(CfgTheme.current.backgroundColor.colorInt(this))
        binding.logo.isVisible = CfgTheme.current is DefaultTheme
        binding.logoTxt.isVisible = CfgTheme.current is DarkTheme
        binding.startGame.applyTheme()
        binding.btnLoadGame.applyTheme()
        binding.loadLastGame.applyTheme()
    }

}