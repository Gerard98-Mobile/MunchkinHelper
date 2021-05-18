package gerard.example.munchkinhelper.ui.activity.load

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import gerard.example.munchkinhelper.CfgTheme
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.colorInt
import gerard.example.munchkinhelper.core.BaseActivity
import gerard.example.munchkinhelper.pickLayout
import gerard.example.munchkinhelper.ui.activity.create.AddingPlayersActivity
import gerard.example.munchkinhelper.util.NavigationHelper
import kotlinx.android.synthetic.main.activity_load_game.*

class LoadGameActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_game)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        load_game_viewpager.adapter = LoadGameVPAdapter(this)

        TabLayoutMediator(load_game_tab_layout, load_game_viewpager) { tab, position ->
            tab.text = if(position == 0) getString(R.string.history) else getString(R.string.schemes)
        }.attach()

        btn_startNewGame.setOnClickListener {
            NavigationHelper.startActivity(this, AddingPlayersActivity::class.java)
        }

        load_game_viewpager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                btn_startNewGame.animate().translationY(0f)
            }
        })
    }

    override fun applyThemeColors() {
        with(CfgTheme.current.primaryColor.colorInt(this)){
            load_game_tab_layout.setSelectedTabIndicatorColor(this)
            load_game_tab_layout.setTabTextColors(R.color.darkGrey.colorInt(this@LoadGameActivity), this)
        }
        root.setBackgroundColor(CfgTheme.current.backgroundColor.colorInt(this))
        btn_startNewGame.applyTheme()
    }

    var startPostion : Float? = null
    fun changePositionOfBtn(newPosition: Float){
        if(startPostion == null) startPostion = btn_startNewGame.y

        startPostion?.let {
            if(btn_startNewGame.y + newPosition < it) return
        }

        btn_startNewGame.y = btn_startNewGame.y + newPosition
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> NavigationHelper.finish(this)
        }
        return super.onOptionsItemSelected(item)
    }


}