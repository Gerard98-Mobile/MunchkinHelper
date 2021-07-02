package gerard.example.munchkinhelper

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import gerard.example.munchkinhelper.core.BaseActivity
import gerard.example.munchkinhelper.core.dialogs.TestPopup
import gerard.example.munchkinhelper.core.recycler.CustomViewHolder
import gerard.example.munchkinhelper.core.viewpager.LoopedMultiRecyclerAdapter
import gerard.example.munchkinhelper.core.viewpager.LoopedTabLayoutMediator
import gerard.example.munchkinhelper.databinding.ActivityMainBinding
import gerard.example.munchkinhelper.databinding.TestItemBinding
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

        if(BuildConfig.DEBUG){
            binding.btnDebugOpen.isVisible = true
            binding.btnDebugOpen.setOnClickListener {
                TestPopup().show(binding.startGame)
//                val intent = Intent(this, GameActivity::class.java)
//                val game = Game(0, listOf(Player("Gerard",2,4), Player("Braciak",5,3)))
//                intent.putExtra(GAME_KEY, game)
//                startActivity(intent)
            }
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

        binding.btnLoadGame.setOnClickListener {
            NavigationHelper.startActivity(this, LoadGameActivity::class.java)
        }

        binding.startGame.setOnClickListener {
            NavigationHelper.startActivity(this, AddingPlayersActivity::class.java)
        }


    }

//    class Test(
//        val text: String
//    )
//
//    data class Halo(
//        val alo: Boolean
//    )
//
//    val siema = listOf(
//        Test("Siema"),
//        Halo(true),
//        Halo(false),
//        Test("XD2"),
//        Test("XD3")
//    )
//
//    val adapter = object: LoopedMultiRecyclerAdapter<Any>(this, true){
//        init{
//            register({ it is Test}, ::bind, TestItemBinding::inflate)
//            register({ it is Halo}, ::bindBoolean, TestItemBinding::inflate)
//        }
//
//        fun bind(holder: CustomViewHolder<TestItemBinding>, test: Test){
//            holder.binding.text.text = test.text
//        }
//
//        fun bindBoolean(holder: CustomViewHolder<TestItemBinding>, test: Halo){
//            holder.binding.text.text = test.toString() + " " + test.toString()
//        }
//    }
//
//    fun initVP(){
//        adapter.data = siema
//        binding.viewpagerTest.adapter = adapter
//        LoopedTabLayoutMediator(adapter, binding.viewpagerTest, binding.tablayout).attach()
//    }

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
